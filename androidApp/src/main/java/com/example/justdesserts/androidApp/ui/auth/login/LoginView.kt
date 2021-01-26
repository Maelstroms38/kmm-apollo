package com.example.justdesserts.androidApp.ui.auth.login

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.justdesserts.androidApp.ui.auth.form.LoginFormView
import com.example.justdesserts.androidApp.ui.auth.profile.ProfileView
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.Review
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginView(bottomBar: @Composable () -> Unit, dessertSelected: (dessert: Dessert) -> Unit) {
    val loginViewModel = getViewModel<LoginViewModel>()
    val scope = rememberCoroutineScope()
    val (token, setToken) = remember { mutableStateOf(loginViewModel.getAuthToken()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                actions = {
                    IconButton(onClick = {
                        loginViewModel.deleteAuthToken()
                        setToken("")
                }) {
                    Icon(Icons.Default.ExitToApp)
                }
                }
            )
        },
        bottomBar = bottomBar,
        bodyContent = {
            if (token.isEmpty()) {
                LoginFormView(handler = { email, password ->
                    scope.async {
                        val token = loginViewModel.signIn(email, password)
                        setToken(token)
                    }
                })
            } else {
                ProfileView(dessertSelected = dessertSelected)
            }
        }
    )
}