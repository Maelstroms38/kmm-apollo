package com.example.justdesserts.androidApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.justdesserts.androidApp.ui.auth.login.LoginView
import com.example.justdesserts.androidApp.ui.auth.profile.ProfileView
import com.example.justdesserts.shared.cache.DessertAction
import com.example.justdesserts.androidApp.ui.desserts.detail.DessertDetailView
import com.example.justdesserts.androidApp.ui.desserts.favorites.FavoriteListView
import com.example.justdesserts.androidApp.ui.desserts.form.DessertFormView
import com.example.justdesserts.androidApp.ui.desserts.list.DessertListView
import com.example.justdesserts.androidApp.ui.desserts.review.ReviewFormView
import com.example.justdesserts.shared.cache.ReviewAction

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    object DessertsScreen : Screens("Desserts", "Desserts", Icons.Default.List)
    object DessertDetailsScreen : Screens("DessertDetails", "DessertDetails")
    object DessertFormScreen : Screens("DessertForm", "DessertForm")
    object ReviewFormScreen : Screens("ReviewForm", "ReviewForm")
    object FavoritesScreen : Screens("Favorites", "Favorites", Icons.Default.Favorite)
    object LoginScreen : Screens("Profile", "Profile", Icons.Default.AccountCircle)
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainLayout()
            }
        }
    }
}

@Composable
fun MainLayout() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(Screens.DessertsScreen, Screens.FavoritesScreen, Screens.LoginScreen)
    val bottomBar: @Composable () -> Unit = { BottomNavigation(navController, bottomNavigationItems) }

    NavHost(navController, startDestination = Screens.DessertsScreen.route) {
        composable(Screens.DessertsScreen.route) {
            DessertListView(bottomBar, dessertSelected = {
                navController.navigate(Screens.DessertDetailsScreen.route +
                        "/${it.id}")
            })
        }
        composable(Screens.DessertDetailsScreen.route + "/{id}") { backStackEntry ->
            DessertDetailView(backStackEntry.arguments?.get("id") as String,
                editDessertSelected = {
                    navController.navigate(Screens.DessertFormScreen.route +
                            "/${it}")
                }, popBack = { navController.popBackStack() },
                editReviewSelected = {
                     navController.navigate(Screens.ReviewFormScreen.route +
                             "?reviewId=${it}")
                },
                createReviewSelected = {
                    navController.navigate(Screens.ReviewFormScreen.route + "?dessertId=${it}")
                }
            )
        }
        
        composable(Screens.DessertFormScreen.route + "/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.get("id") as? String ?: "new"
            val action = if (id != "new") DessertAction.UPDATE else DessertAction.CREATE
            DessertFormView(id, action,
                popBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screens.ReviewFormScreen.route + "?reviewId={reviewId}&dessertId={dessertId}") { backStackEntry ->
            val reviewId = backStackEntry.arguments?.get("reviewId") as? String ?: "new"
            val dessertId = backStackEntry.arguments?.get("dessertId") as? String ?: "new"
            val action = if (reviewId != "new") ReviewAction.UPDATE else ReviewAction.CREATE
            ReviewFormView(reviewId, dessertId, action,
                popBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screens.FavoritesScreen.route) {
            FavoriteListView(bottomBar, dessertSelected = {
                navController.navigate(Screens.DessertDetailsScreen.route +
                        "/${it.id}")
            })
        }

        composable(Screens.LoginScreen.route) {
            LoginView(bottomBar, dessertSelected = {
                navController.navigate(Screens.DessertDetailsScreen.route +
                        "/${it.id}")
            }, newDessertSelected = {
                navController.navigate(Screens.DessertFormScreen.route + "/new")
            })
        }
    }
}


@Composable
private fun BottomNavigation(
    navController: NavHostController,
    items: List<Screens>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { screen.icon?.let { Icon(screen.icon) } },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }

}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}
