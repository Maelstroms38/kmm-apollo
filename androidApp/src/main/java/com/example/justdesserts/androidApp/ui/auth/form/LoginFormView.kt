package com.example.justdesserts.androidApp.ui.auth.form

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginFormView(handler: (email: String, password: String) -> Unit) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    ScrollableColumn(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Email",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = email,
                onValueChange = { setEmail(it) }

            )
            Text(
                text = "Password",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = password,
                onValueChange = { setPassword(it) }
            )

            Spacer(modifier = Modifier.preferredHeight(16.dp))

            Button(
                onClick = {
                    handler(email, password)
                }, modifier = Modifier
                    .padding(16.dp)
                    .preferredWidth(320.dp)
            ) {
                Text("Login")
            }
        }
    }
}