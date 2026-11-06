package com.akas62083.study_app

import android.R.attr.name
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akas62083.study_app.ui.detail.DetailScreen
import com.akas62083.study_app.ui.detail.DetailViewModel
import com.akas62083.study_app.ui.home.HomeScreen
import com.akas62083.study_app.ui.home.HomeViewModel
import com.akas62083.study_app.ui.move.MoveScreen
import com.akas62083.study_app.ui.move.MoveViewModel
import com.akas62083.study_app.ui.settings.SettingsScreen
import com.akas62083.study_app.ui.settings.SettingsViewModel
import com.akas62083.study_app.ui.theme.Study_appTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Study_appTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = Route.HOME,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Route.HOME>() {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<Route.DETAIL>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { it }
                )
            }
        ) {
            val viewModel: DetailViewModel = hiltViewModel()
            DetailScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<Route.SETTINGS>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { it }
                )
            }
        ){
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel
            )
        }
        composable<Route.MOVE>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { it }
                )
            }
        ) {
            val viewModel: MoveViewModel = hiltViewModel()
            MoveScreen(
                viewModel
            )
        }
    }
}

object Route {
    @Serializable
    data object HOME
    @Serializable
    data class DETAIL(val id: Long)
    @Serializable
    data object SETTINGS
    @Serializable
    data object MOVE
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Study_appTheme {
        Greeting()
    }
}
