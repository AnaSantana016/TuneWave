package com.example.tunewave.ui.screens.signIn

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.tunewave.R
import com.example.tunewave.data.database.User
import com.example.tunewave.data.database.getRandomId
import com.example.tunewave.data.utils.UiState
import com.example.tunewave.data.utils.userDatabaseObject
import com.example.tunewave.ui.screens.init.InitActivity
import com.example.tunewave.ui.screens.signIn.user.UserModel
import com.example.tunewave.ui.theme.MusifyTheme
import com.example.tunewave.viewmodels.AuthViewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalFoundationApi::class)
class SignInActivity : ComponentActivity() {

    val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusifyTheme {
                Surface {
                    RegisterForm()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class, ExperimentalMaterialApi::class
    )
    @Composable
    fun RegisterForm() {

        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = FocusRequester()

        val context = LocalContext.current

        // Use rememberLauncherForActivityResult to launch image picker
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    // Handle the selected image URI
                    selectedImageUri = uri
                }
            }
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

            // Capa 3: Imágenes "fondo_dualipa"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                // Imagen "fondo_dualipa"
                Image(
                    painter = painterResource(id = R.drawable.fondo_dualipa),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .scale(6f)
                        .padding(bottom = 40.dp)
                        .offset(x = (-24).dp)
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
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.blueTunewave),
                            RoundedCornerShape(50)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text("Select Image", color = Color.White, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
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
                    value = email,
                    onValueChange = { email = it},
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                        onNext = {
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

                        val uriToUpload = selectedImageUri ?: Uri.EMPTY

                        viewModel.onUploadSingleFile(uriToUpload) { uiState ->
                            when (uiState) {
                                is UiState.Success -> {
                                    val (imageUrl, imageId) = uiState.data
                                    viewModel.setUploadedImageUrl(imageUrl)

                                    val userSingIn = UserModel(
                                        id = getRandomId(),
                                        imageUrl,
                                        username,
                                        email,
                                        password,
                                        password
                                    )

                                    viewModel.register(
                                        email = email,
                                        password = password,
                                        user = userSingIn
                                    )

                                    val userRoom = User(userSingIn.id, userSingIn.email)

                                    userDatabaseObject.setUser(userRoom)

                                    val intent = Intent(context, InitActivity::class.java)
                                    context.startActivity(intent)
                                }
                                is UiState.Failure -> {
                                    // Manejar el error en la carga de la imagen
                                }
                            }
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
                        "Sign In",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

}