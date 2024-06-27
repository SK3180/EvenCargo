package com.sksingh.evencargo.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sksingh.evencargo.R
import com.sksingh.evencargo.data.Resource
import com.sksingh.evencargo.model.AuthViewModel


// LoginScreen.kt

@Composable
fun LoginScreen(viewModel: AuthViewModel,navController: NavHostController) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val loginimage = painterResource(id = R.drawable.login)
    val loginFlow = viewModel.loginflow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(37, 37, 37)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = loginimage,
            contentDescription = "Login Photo",
            modifier = Modifier.size(200.dp))

        TextComponent(text = emailState.value,
            label = "Email",
            onTextChange = { emailState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            maxLine = 1)

        TextComponent(text = passwordState.value,
            label = "Password",
            onTextChange = { passwordState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            maxLine = 1)
        SignUpButton(text = "Login", onClick = {
            viewModel.login(emailState.value,passwordState.value)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp))
        Text(text = "New here? SignUp",
            fontSize = 17.sp,
            color = Color.White,
            modifier = Modifier.clickable {
            navController.navigate("ROUTE_SIGNUP"){
                popUpTo("ROUTE_SIGNUP"){
                    inclusive = true
                }

            }
        })
        loginFlow.value?.let {
            when(it){
                is Resource.Failure ->
                {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Loading ->
                {
                    CircularProgressIndicator(modifier = Modifier
                        .size(60.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                        color = Color(60, 208, 120))
                }
                is Resource.Success ->
                {
                    LaunchedEffect(Unit) {
                        navController.navigate("ROUTE_HOME"){
                            popUpTo("ROUTE_HOME"){
                                inclusive = true
                            }
                        }
                    }

                }
            }
        }
    }

}