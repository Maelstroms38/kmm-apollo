package com.example.justdesserts.androidApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.justdesserts.androidApp.ui.desserts.DessertDetailView
import com.example.justdesserts.androidApp.ui.desserts.DessertListView

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    object DessertsScreen : Screens("Desserts", "Desserts", Icons.Default.List)
    object DessertDetailsScreen : Screens("DessertDetails", "DessertDetails")
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

    val bottomNavigationItems = listOf(Screens.DessertsScreen)
    val bottomBar: @Composable () -> Unit = { BottomNavigation(navController, bottomNavigationItems) }

    NavHost(navController, startDestination = Screens.DessertsScreen.route) {
        composable(Screens.DessertsScreen.route) {
            DessertListView(bottomBar) {
                navController.navigate(Screens.DessertDetailsScreen.route+ "/${it.id}")
            }
        }
        composable(Screens.DessertDetailsScreen.route + "/{id}") { backStackEntry ->
            DessertDetailView(backStackEntry.arguments?.get("id") as String, popBack = { navController.popBackStack() })
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
