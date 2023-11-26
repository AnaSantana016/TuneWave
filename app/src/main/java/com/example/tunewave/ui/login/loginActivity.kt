package com.example.tunewave.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.platform.LocalUriHandler
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
import com.example.tunewave.data.database.AuthenticationService
import com.example.tunewave.data.database.FireClient
import com.example.tunewave.ui.init.InitActivity
import com.example.tunewave.ui.singIn.PrefixOffsetMapping
import com.example.tunewave.ui.singIn.PrefixVisualTransformation
import com.example.tunewave.ui.theme.TuneWaveTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuneWaveTheme {
                Surface {
                    LoginForm()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    fun LoginForm() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = FocusRequester()
        var showErrorSnackbar by remember { mutableStateOf(false) }

        val context = LocalContext.current

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
                    label = { Text("Contraseña") },
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
                        val authenticationService = AuthenticationService(FireClient())
                        val loginSuccess = authenticationService.login(email, password)
                        println(loginSuccess)
                        if (loginSuccess) {
                            val intent = Intent(context, InitActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            // Mostrar Snackbar de error
                            showErrorSnackbar = true
                        }
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
                if (showErrorSnackbar) {
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        action = {
                            IconButton(onClick = { showErrorSnackbar = false }) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                            }
                        }
                    ) {
                        Text("Login failed. Please check your credentials.")
                    }
            }
        }
    }}}

