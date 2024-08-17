package com.rakarguntara.readgood.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.rakarguntara.readgood.navigation.ReadScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
){
    CenterAlignedTopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()) {
            if(showProfile){
                Image(imageVector = Icons.Default.Star,
                    contentDescription = "icon",
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                        .scale(0.5f)
                        .size(30.dp))
            }
            if(icon != null){
                Icon(icon, contentDescription = "arrow back", tint = Color.Black,
                    modifier = Modifier.clickable { onBackArrowClicked.invoke() }
                        .scale(0.5f)
                        .size(30.dp))
            }

            Text(title, color = Color.Green.copy(0.6f),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)

            Spacer(modifier = Modifier.width(150.dp))


        }
    }, actions = {
        if(showProfile) {
            IconButton(onClick = {
                navController.navigate(ReadScreens.StatsScreen.name)
            }) {
                Icon(
                    Icons.Filled.AccountCircle, "Profile", tint = Color.Black,
                    modifier = Modifier.size(30.dp).scale(0.5f)
                )
            }
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReadScreens.LoginScreen.name) {
                        popUpTo(ReadScreens.LoginScreen.name) { inclusive = true }
                    }
                }
            })
            {
                Icon(
                    Icons.Filled.Clear, "logout", tint = Color.Black,
                    modifier = Modifier.size(30.dp).scale(0.5f)
                )
            }
        }
    }, colors = TopAppBarColors(
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        Color.Transparent),)
}