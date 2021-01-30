package com.example.justdesserts.androidApp.ui.desserts.review

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.justdesserts.shared.cache.Review
import com.example.justdesserts.shared.cache.ReviewAction
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel

@Composable
fun ReviewFormView(reviewId: String, dessertId: String, action: ReviewAction, popBack: () -> Unit) {
    val reviewFormViewModel = getViewModel<ReviewFormViewModel>()
    val (review, setReview) = remember { mutableStateOf(Review("", "", "", "", 0)) }
    val isEditing = action != ReviewAction.CREATE
    val scope = rememberCoroutineScope()
    val label = if (isEditing) "Save" else "Create"

    LaunchedEffect(reviewId) {
        try {
            if (isEditing) {
                val readReview = reviewFormViewModel.getReview(reviewId)
                readReview?.let {
                    setReview(it)
                }
            }
        } catch(err: Exception) {
            print(err.message)
        }
    }

    suspend fun handleReview(action: ReviewAction) {
        when (action) {
            ReviewAction.CREATE -> {
                reviewFormViewModel.createReview(dessertId, review)
                popBack()
            }
            ReviewAction.UPDATE -> {
                val updateDessert = reviewFormViewModel.updateReview(review)
                updateDessert?.let {
                    setReview(it)
                }
                popBack()
            }
            ReviewAction.DELETE -> {
                val deleted = reviewFormViewModel.deleteReview(review.id)
                if (deleted == true) popBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$label Review") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = {
            review?.let {
                ScrollableColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Description",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                        TextField(
                            value = review.text,
                            onValueChange = { setReview(review.copy(text = it)) }

                        )
                        Text(
                            text = "Rating",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )

                        Row {
                            List(5) {
                                val rating = it + 1
                                if (rating <= review.rating.toInt()) {
                                    IconButton(onClick = {
                                        setReview(review.copy(rating = rating.toLong()))
                                    }) {
                                        Icon(Icons.Filled.Star, tint = MaterialTheme.colors.primary)
                                    }
                                } else {
                                    IconButton(onClick = {
                                        setReview(review.copy(rating = rating.toLong()))
                                    }) {
                                        Icon(Icons.Filled.Star, tint = Color(0xFFd3d3d3))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.preferredHeight(16.dp))

                        Button(
                            onClick = {
                                scope.async {
                                    handleReview(action)
                                }
                            }, modifier = Modifier
                                .padding(16.dp)
                                .preferredWidth(320.dp)
                        ) {
                            Text(label)
                        }

                        if (isEditing) {
                            Button(
                                onClick = {
                                    scope.async {
                                        handleReview(ReviewAction.DELETE)
                                    }
                                }, modifier = Modifier
                                    .padding(16.dp)
                                    .preferredWidth(320.dp)
                            ) {
                                Text("Delete")
                            }
                        }

                    }
                }
            }
        })
    }