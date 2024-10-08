package com.rakarguntara.readgood.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.rakarguntara.readgood.components.TextTitle
import com.rakarguntara.readgood.navigation.ReadScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f, animationSpec = tween(durationMillis = 800,
            easing = {
                OvershootInterpolator(8f).getInterpolation(it)
            }))

        delay(2000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(ReadScreens.LoginScreen.name)
        } else {
            navController.navigate(ReadScreens.HomeScreen.name)
        }

    }

    Surface(modifier = Modifier.size(300.dp)
        .scale(scale.value)
        .background(Color.Transparent)
        .border(2.dp, Color.Green, shape = CircleShape),
        shape = CircleShape,){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextTitle("Read Good")
        }

    }
}