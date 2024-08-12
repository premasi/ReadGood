package com.rakarguntara.readgood.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rakarguntara.readgood.components.TextTitle
import com.rakarguntara.readgood.components.UserForm

@Composable
fun LoginScreen(navController: NavHostController) {
    val showLoginForm = rememberSaveable{
        mutableStateOf(true)
    }
    Scaffold(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.padding(it).fillMaxWidth().padding(bottom = 32.dp, top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitle()
            if(showLoginForm.value) {
                UserForm(loading = false, isRegister = false) { email, password ->

                }
            } else {
                UserForm(loading = false, isRegister = true) {email,password ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)) {
                val text = if(showLoginForm.value) "Sign Up" else "Sign In"
                Text("New user?")
                Text(text, modifier = Modifier.clickable {
                    showLoginForm.value = !showLoginForm.value
                }.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue)
            }
        }
    }
}