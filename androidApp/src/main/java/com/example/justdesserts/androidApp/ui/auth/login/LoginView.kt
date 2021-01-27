package com.example.justdesserts.androidApp.ui.auth.login

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.justdesserts.androidApp.ui.auth.form.LoginFormView
import com.example.justdesserts.androidApp.ui.auth.profile.ProfileView
import com.example.justdesserts.shared.cache.Dessert
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginView(bottomBar: @Composable () -> Unit,
              dessertSelected: (dessert: Dessert) -> Unit,
              newDessertSelected: () -> Unit) {
    val loginViewModel = getViewModel<LoginViewModel>()
    val scope = rememberCoroutineScope()
    val (token, setToken) = remember { mutableStateOf(loginViewModel.getAuthToken()) }
    val loggedIn = token.isNotEmpty()
    val title = if (loggedIn) "Profile" else "Login"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    if (loggedIn) {
                        IconButton(onClick = {
                            loginViewModel.deleteAuthToken()
                            setToken("")
                        }) {
                            Icon(Icons.Default.ExitToApp)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                newDessertSelected()
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(Icons.Outlined.Add)
            }
        },
        bottomBar = bottomBar,
        bodyContent = {
            if (token.isEmpty()) {
                LoginFormView(handler = { login, email, password ->
                    scope.async {
                        if (login) {
                            val token = loginViewModel.signIn(email, password)
                            setToken(token)
                        } else {
                            val token = loginViewModel.signUp(email, password)
                            setToken(token)
                        }
                    }
                })
            } else {
                ProfileView(dessertSelected = dessertSelected)
            }
        }
    )
}