package com.example.tunewave.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tunewave.R
import com.example.tunewave.ui.screens.login.LoginActivity
import com.example.tunewave.ui.screens.signIn.SignInActivity
import com.example.tunewave.ui.theme.MusifyTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            MusifyTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @Composable
    fun MainScreen() {

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
                    painter = painterResource(id = R.drawable.imagen),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(5f)
                        .background(Color(0f, 0f, 0f, 0.6f))
                        .offset(x = (-20.dp), y = (40).dp)
                )
            }

            // Capa 2: Imágenes "portada_nicki" y "portada_eminem"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Imagen "portada_nicki" a la izquierda
                    Image(
                        painter = painterResource(id = R.drawable.portada_nicki),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .scale(6f)
                            .padding(bottom = 80.dp)
                            .offset(x = (-10).dp)
                    )

                    // Imagen "portada_eminem" a la derecha
                    Image(
                        painter = painterResource(id = R.drawable.portada_eminem),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .scale(5f)
                            .padding(bottom = 16.dp)
                            .offset(x = (5).dp)
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Imagen "portada_eminem" a la derecha
                Image(
                    painter = painterResource(id = R.drawable.portada_milo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .scale(7f)
                        .padding(bottom = 40.dp)
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Imagen musica
                Image(
                    painter = painterResource(id = R.drawable.musica),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .scale(5f)
                        .padding(bottom = 60.dp)
                        .offset(y = (-5).dp)
                )
            }

            // Agregar textos "Tune" y "Wave" en la parte superior
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (60).dp)
                        .height((110).dp)
                        .padding(horizontal = 40.dp, vertical = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        "TUNE",
                        color = colorResource(id = R.color.redTunewave),
                        fontSize = 55.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .offset(y = (55).dp)
                    )

                    Text(
                        "WAVE",
                        color = colorResource(id = R.color.blueTunewave),
                        fontSize = 55.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .offset(y = (65).dp)
                    )
                }
            }

            // Capa 3: Botones en la parte inferior
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(context, SignInActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(60.dp)
                            .border(
                                1.dp,
                                colorResource(id = R.color.redTunewave),
                                RoundedCornerShape(50)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blackTunewave))
                    ) {
                        Text("Sign In", color = Color.White, fontSize = 20.sp)
                    }

                    Button(
                        onClick = {
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(60.dp)
                            .border(
                                width = 2.dp,
                                color = colorResource(id = R.color.blueTunewave),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blackTunewave))
                    ) {
                        Text(
                            "Login",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}