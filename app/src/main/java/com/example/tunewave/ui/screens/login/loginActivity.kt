@file:OptIn(ExperimentalFoundationApi::class)

package com.example.tunewave.ui.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tunewave.R
import com.example.tunewave.ui.screens.init.InitActivity
import com.example.tunewave.ui.theme.MusifyTheme
import com.example.tunewave.viewmodels.AuthViewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterialApi::class)
class LoginActivity : ComponentActivity() {

    val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusifyTheme {
                Surface {
                    LoginForm()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class, ExperimentalFoundationApi::class
    )
    @Composable
    fun LoginForm() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = FocusRequester()


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Capa 1: Fondo
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(5f)
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.transparente),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(5f)
                        .background(Color(0f, 0f, 0f, 0.6f))
                        .offset(y = (15).dp)
                )
            }

            // Capa 3: Imágenes "fondo_duki"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                // Imagen "fondo_duki"
                Image(
                    painter = painterResource(id = R.drawable.fondo_duki),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp) // Ajusta el tamaño según sea necesario
                        .scale(6f) // Ajusta la escala según sea necesario
                        .padding(bottom = 40.dp)
                        .offset(x = (-24).dp)
                )
            }

            // Capa 4: Imágenes "nota_musicales"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                // Imagen "nota_musicales"
                Image(
                    painter = painterResource(id = R.drawable.nota_musicales),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .scale(6f)
                        .padding(bottom = 110.dp)
                        .offset(x = 8.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .background(
                        color = colorResource(id = R.color.blackTunewave),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.blueTunewave),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Back",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp)
                        .clickable { onBackPressedDispatcher?.onBackPressed() },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusRequester.requestFocus()
                        }
                    ),
                    modifier = Modifier
                        .heightIn(max = 70.dp)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .heightIn(max = 70.dp)
                        .padding(8.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                        viewModel.login(
                            email = email,
                            password = password
                        )

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(
                            width = 2.dp,
                            color = colorResource(id = R.color.blueTunewave),
                            shape = RoundedCornerShape(50)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        "Login",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                // Snackbar para mostrar mensaje de error
                val showErrorSnackbar by remember { viewModel.getErrorSnackbar() }

                if (showErrorSnackbar) {
                    CustomDialog(onDismissRequest = { finish() })
                }else{

                    val loginSuccess by remember { viewModel.loginSuccess }

                    if (loginSuccess) {
                        val intent = Intent(LocalContext.current, InitActivity::class.java)
                        LocalContext.current.startActivity(intent)
                    }
                }
            }
        }
    }

    @Composable
    fun CustomDialog(
        onDismissRequest: () -> Unit
    ) {

        AlertDialog(
            onDismissRequest = { onDismissRequest(

            ) },
            text = {
                Text(
                    text="¡¡ERROR!!",
                    fontSize = 26.sp,
                    color = Color.Red,
                    modifier = Modifier.offset(x = 60.dp)
                )
                Text(
                    text = "This is a minimal dialog with a close button.",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    color = Color.White
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {  onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Close", color = Color.White)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}