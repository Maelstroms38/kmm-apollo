package com.example.justdesserts.androidApp.ui.desserts.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.justdesserts.shared.cache.Review

@Composable
fun ReviewListView(reviews: List<Review>, userId: String, editReviewSelected: (Review) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        reviews.let { reviewsList ->
            LazyColumn {
                items(reviewsList) { item ->
                    item.let { review ->
                        val editable = userId == review.userId
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = editable,
                                onClick = { editReviewSelected(review) })) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp, bottom = 5.dp)
                            ) {
                                Text(
                                    review.text,
                                    style = MaterialTheme.typography.h6
                                )
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp, bottom = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Row {
                                    List(5) {
                                        val rating = it + 1
                                        if (rating <= review.rating.toInt()) {
                                            Icon(Icons.Filled.Star, tint = MaterialTheme.colors.primary)
                                        } else {
                                            Icon(Icons.Filled.Star, tint = Color(0xFFd3d3d3))
                                        }
                                    }
                                }

                                Row {
                                    if (editable) {
                                        Icon(Icons.Outlined.Create)
                                    }
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }
}