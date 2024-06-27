package com.sksingh.evencargo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sksingh.evencargo.R

import com.sksingh.evencargo.model.AuthViewModel


@Composable
fun HomeScreen(viewModel: AuthViewModel,navController: NavHostController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(37, 37, 37)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val hy_robot = painterResource(id = R.drawable.hello)
        Text(text = "WELCOME BACK",
            fontSize = 40.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp))
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Text(text = viewModel.currentUser?.displayName?:"",
            fontSize = 40.sp, color = Color(60, 208, 120))

        Image(painter = hy_robot, contentDescription = "hello")
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = viewModel.currentUser?.email?:"",
                fontSize = 30.sp, color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp))

           Spacer(modifier = Modifier.padding(vertical = 20.dp))
            SignUpButton(text = "Logout", onClick = { viewModel.logout()
                navController.navigate("ROUTE_LOGIN"){
                    popUpTo("ROUTE_HOME"){
                        inclusive = true
                    }
                }
            })
        }

    }
}
