package com.example.tunewave

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tunewave.ui.login.LoginActivity
import com.example.tunewave.ui.singIn.SignInActivity
import com.example.tunewave.ui.theme.TuneWaveTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuneWaveTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
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
                        .offset(y = (15).dp)
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
                            .size(200.dp) // Ajusta el tamaño según sea necesario
                            .scale(6f) // Ajusta la escala según sea necesario
                            .padding(bottom = 80.dp)
                            .offset(x = (-10).dp)
                    )

                    // Imagen "portada_eminem" a la derecha
                    Image(
                        painter = painterResource(id = R.drawable.portada_eminem),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp) // Ajusta el tamaño según sea necesario
                            .scale(5f) // Ajusta la escala según sea necesario
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
                        .size(100.dp) // Ajusta el tamaño según sea necesario
                        .scale(7f) // Ajusta la escala según sea necesario
                        .padding(bottom = 40.dp)
                )
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