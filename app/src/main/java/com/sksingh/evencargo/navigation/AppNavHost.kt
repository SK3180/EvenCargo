package com.sksingh.evencargo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sksingh.evencargo.model.AuthViewModel
import com.sksingh.evencargo.screen.HomeScreen
import com.sksingh.evencargo.screen.LoginScreen
import com.sksingh.evencargo.screen.SignUpScreen

@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController:NavHostController = rememberNavController(),
    startDestination:String = "ROUTE_LOGIN"

){
    NavHost(modifier = modifier, navController = navController, startDestination = startDestination ) {
        composable("ROUTE_LOGIN"){
            LoginScreen(viewModel,navController)
        }
        composable("ROUTE_SIGNUP"){
            SignUpScreen(viewModel,navController)
        }
        composable("ROUTE_HOME"){
            HomeScreen(viewModel,navController)
        }
    }
}