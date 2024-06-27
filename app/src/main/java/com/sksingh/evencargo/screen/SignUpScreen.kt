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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sksingh.evencargo.R
import com.sksingh.evencargo.data.Resource
import com.sksingh.evencargo.model.AuthViewModel


@Composable
fun SignUpScreen(viewModel: AuthViewModel,navController: NavHostController) {
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val signupimage = painterResource(id = R.drawable.signin)
    val signupFlow = viewModel.signupflow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(37, 37, 37)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = signupimage,
            contentDescription = "Login Photo",
            modifier = Modifier.size(200.dp))
        TextComponent(text = nameState.value,
            label = "Name",
            onTextChange = { nameState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            maxLine = 1)
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


        SignUpButton(
            text = "SIGNUP",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(10.dp)),
            onClick = {
                    viewModel.signup(nameState.value, emailState.value,passwordState.value)

            }
        )

        Text(text = "Already Registered Login here",
            fontSize = 17.sp,
            color = Color.White,
            modifier = Modifier.clickable {
                navController.navigate("ROUTE_LOGIN"){
                    popUpTo("ROUTE_LOGIN"){
                        inclusive = true
                    }
                }
            })
        signupFlow.value?.let {
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

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {}
){
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = { onTextChange(it) },
        modifier = modifier
            ,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(51,51,51),
            unfocusedContainerColor = Color(50, 50, 50),
            disabledContainerColor = Color.White,
            focusedTextColor = Color(60, 208, 120),
            unfocusedTextColor = Color.Gray,
            focusedSupportingTextColor = Color(60, 208, 120)
        ),
        maxLines = maxLine,
        label = {
            Text(
                text = label,
                color = Color.White,
                fontSize = 15.sp,
           //     fontWeight = FontWeight.Bold
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
    )
}

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(Color(60, 208, 120))
    ) {
        Text(
            text,
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}