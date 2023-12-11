@file:OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)

package com.example.tunewave.ui.screens.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.tunewave.R
import com.example.tunewave.data.utils.userDatabaseObject
import com.example.tunewave.data.utils.userProfileDatabase
import com.example.tunewave.ui.activities.MainActivity
import com.example.tunewave.ui.components.MusifyBottomNavigationConstants
import com.example.tunewave.ui.components.MusifyMiniPlayerConstants
import com.example.tunewave.ui.screens.signIn.user.UserModel
import com.example.tunewave.viewmodels.AuthViewModel.AuthViewModel


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(viewModel: AuthViewModel, context: Context) {

    var isEditing by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var usuario by remember { mutableStateOf<UserModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    viewModel.getUser()?.email?.let { viewModel.getUserByEmail(it) }

    usuario = viewModel.getUserModel()


    LaunchedEffect(key1 = Unit) {
        val userEmail = userDatabaseObject.getUser().email
        if (userEmail != null) {
            viewModel.getUserByEmail(userEmail)

            userProfileDatabase.getUser().let { user ->
                usuario = user
                isLoading = false
            }
        }
    }

    var problema : String = ""

    if(usuario != null){
        for (i in usuario!!.name.iterator()){
            problema += i
        }
    }

    var nombre by remember {mutableStateOf("")}

    if (isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
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

            // Capa 2: Fondo oscurecido
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.transparente),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (500).dp)
                )

                // Capa 3: Oscurecer fondo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0f, 0f, 0f, 0.6f))
                        .offset(y = (15).dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(
                    top = 8.dp,
                    bottom = MusifyMiniPlayerConstants.miniPlayerHeight + MusifyBottomNavigationConstants.navigationHeight
                )
            ) {
                item {
                    Spacer(modifier = Modifier.statusBarsPadding())
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pro",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h3,
                            color = colorResource(id = R.color.blueTunewave)
                        )
                        Text(
                            text = "file",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h3,
                            color = Color(0xFFFFFFFF)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    // Imagen de perfil
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.1f))
                    ) {
                        val imageUrl = usuario?.urlImage ?: ""

                        val imagePainter = rememberImagePainter(
                            data = imageUrl,
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.icono_perfil)
                            }
                        )

                        if (imageUrl?.isNotEmpty() == true) {
                            Image(
                                painter = imagePainter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.icono_perfil),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(80.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }


                item {

                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .height(1.dp)
                            .padding(
                                start = 20.dp,
                                end = 20.dp
                            )
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    // Nombre

                    Column(
                        modifier = Modifier
                            .heightIn(max = 120.dp)
                            .padding(8.dp)
                    ) {
                        Text("Name",
                            modifier = Modifier.padding(start = 10.dp))

                        TextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text(problema)},
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Email
                            ),
                            placeholder = { Text("Enter your name") },
                            enabled = isEditing,
                            modifier = Modifier
                                .heightIn(max = 120.dp)
                                .padding(8.dp)
                        )
                    }
                }

                item {
                    // Email
                    var email = usuario?.email ?: ""

                    Column(
                        modifier = Modifier
                            .heightIn(max = 120.dp)
                            .padding(8.dp)
                    ) {
                        Text("Email",
                            modifier = Modifier.padding(start = 10.dp))

                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Email
                            ),
                            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "emailIcon") },
                            enabled = false,
                            modifier = Modifier
                                .heightIn(max = 120.dp)
                                .padding(8.dp)
                        )
                    }
                }

                item {

                    var password = usuario?.password ?: ""

                    Column(
                        modifier = Modifier
                            .heightIn(max = 120.dp)
                            .padding(8.dp)
                    ) {
                        Text("Password",
                            modifier = Modifier.padding(start = 10.dp))

                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Email
                            ),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "emailIcon") },
                            enabled = false,
                            modifier = Modifier
                                .heightIn(max = 120.dp)
                                .padding(8.dp)
                        )
                    }
                }

                item{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {

                                    if (!isEditing){
                                        isEditing = true
                                    }else{
                                        problema = nombre
                                        isEditing = false
                                        usuario?.name = nombre
                                        usuario?.let { viewModel.updateUser(it) }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .height(60.dp),
                                border = BorderStroke(1.dp, colorResource(id = R.color.blueTunewave)),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.blackTunewave)
                                )
                            ) {
                                if (!isEditing) {
                                    Text(
                                        "Update",
                                        color = Color.White,
                                        fontSize = 20.sp
                                    )
                                }else{
                                    Text(
                                        "Save",
                                        color = Color.White,
                                        fontSize = 20.sp
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    viewModel.logout {
                                    }
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)

                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(60.dp),
                                border = BorderStroke(1.dp, colorResource(id = R.color.redTunewave)),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.blackTunewave)
                                )
                            ) {
                                Text(
                                    "Log Out",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }


                item {
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    }
}