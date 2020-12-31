package com.example.justdesserts.androidApp.ui.desserts.detail

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AmbientContentColor
import androidx.compose.material.BottomDrawerLayout
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.androidApp.ui.desserts.form.DessertFormView
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun DessertDetailView(dessertId: String, popBack: () -> Unit) {
    val dessertDetailViewModel = getViewModel<DessertDetailViewModel>()
    val (dessert, setDessert) = remember { mutableStateOf<GetDessertQuery.Dessert?>(null) }
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    LaunchedEffect(dessertId) {
         setDessert(dessertDetailViewModel.getDessert(dessertId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(dessert?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (drawerState.isExpanded || drawerState.isOpen) {
                    drawerState.close()
                } else {
                    drawerState.expand()
                }
            }, backgroundColor = MaterialTheme.colors.primary) {
                if (drawerState.isExpanded || drawerState.isOpen) {
                    Icon(Icons.Filled.Check)
                } else {
                    Icon(Icons.Outlined.Create)
                }
            }
        },
        bodyContent = {
            BottomDrawerLayout(drawerState = drawerState,
                drawerContent = {
                    dessert?.let {
                        DessertFormView(
                            dessertId = it.id,
                            name = it.name ?: "",
                            description = it.description ?: "",
                            imageUrl = it.imageUrl ?: ""
                        )
                    }
                }, bodyContent = {
                    Surface(color = Color.White) {
                        ScrollableColumn(modifier = Modifier.padding(top = 16.dp)) {
                            dessert?.let {
                                Surface(color = Color.White) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        val imageUrl = dessert.imageUrl
                                        if (imageUrl != null) {
                                            Card(
                                                modifier = Modifier.preferredSize(150.dp),
                                                shape = RoundedCornerShape(25.dp)
                                            ) {
                                                CoilImage(data = imageUrl)
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.preferredHeight(16.dp))

                                Text(
                                    "Summary", style = typography.h5, color = AmbientContentColor.current,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                                )

                                Text(
                                    dessert.description ?: "", style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                                )

                                Spacer(modifier = Modifier.preferredHeight(16.dp))

                                Text(
                                    "Reviews", style = typography.h5, color = AmbientContentColor.current,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                                )

                                Surface(color = Color.White) {
                                    DessertReviewsList(dessert)
                                }
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
private fun DessertReviewsList(dessert: GetDessertQuery.Dessert) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        dessert.reviews?.let { reviewsList ->
            reviewsList.filterNotNull().forEach { review ->
                Column {
                    Text(review.text,
                        style = typography.h6)
                    Text("${review.rating.toString()} stars",
                        style = typography.subtitle2,
                        color = Color.Gray)
                }
                Divider()
            }
        }
    }
}
