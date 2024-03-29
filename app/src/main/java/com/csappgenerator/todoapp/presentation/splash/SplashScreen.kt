package com.csappgenerator.todoapp.presentation.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.ui.theme.LOGO_HEIGHT
import com.csappgenerator.todoapp.ui.theme.SplashBackground
import com.csappgenerator.todoapp.util.Constants.SPLASH_EXIT_ANIMATION_TWEEN_DURATION
import com.csappgenerator.todoapp.util.Constants.SPLASH_SCREEN_DELAY
import com.csappgenerator.todoapp.util.navigateFromSplashToList
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else LOGO_HEIGHT,
        animationSpec = tween(SPLASH_EXIT_ANIMATION_TWEEN_DURATION)
    )

    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(SPLASH_EXIT_ANIMATION_TWEEN_DURATION)
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_DELAY)
        navController.navigateFromSplashToList()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                SplashBackground
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(LOGO_HEIGHT)
                .offset(y = offsetState)
                .alpha(alpha = alphaState),
            painter = painterResource(id = getLogo()),
            contentDescription = stringResource(R.string.todo_logo)
        )
    }
}

@Composable
fun getLogo(): Int {
    return if (isSystemInDarkTheme()) {
        R.drawable.ic_logo_dark
    } else {
        R.drawable.ic_logo_light
    }
}