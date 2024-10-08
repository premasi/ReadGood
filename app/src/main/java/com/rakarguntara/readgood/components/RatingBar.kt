package com.rakarguntara.readgood.components

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rakarguntara.readgood.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    rating: Int,
    onPressRating: (Int) -> Unit
){
    var ratingState by remember {
        mutableIntStateOf(rating)
    }
    var selected by remember {
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        targetValue = if(selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = Modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for(i in 1..5){
            Icon(
                painter = painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when(it.action){
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                ratingState = i
                                onPressRating(i)
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }

                            else -> {}
                        }
                        true
                    },
                tint = if(i <= ratingState) Color.Yellow else Color.Gray
            )
        }

    }
}