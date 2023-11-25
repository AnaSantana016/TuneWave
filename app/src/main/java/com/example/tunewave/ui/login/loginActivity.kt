package com.example.tunewave.ui.login

import com.example.tunewave.ui.theme.TuneWaveTheme
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuneWaveTheme {
                Surface {
                    //RegisterForm()
                }
            }
        }
    }

}

/*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
// import com.example.tunewave.PrefixVisualTransformation
import com.example.tunewave.R
/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterForm() {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
                    .clickable { /* Handle back button click */}
            )
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
                .padding(16.dp),
            text = "Back"
        ) {
            Text(
                text = "Back",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
                    .clickable { /* Handle back button click */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.None, // Establece imeAction a None
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle "Done" action if needed
                        keyboardController?.hide()
                    },
                    onNext = {
                        // Move focus to the next input field
                        // You can also handle any additional logic here
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .heightIn(max = 70.dp) // Set a fixed height to prevent growing
                    .padding(8.dp), // Add padding to maintain consistent appearance
                visualTransformation = PrefixVisualTransformation("")
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(8.dp))
            var password by remember { mutableStateOf("") }
            var hidden by remember { mutableStateOf(true) } //1

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { hidden = !hidden }) {
                        val vector = painterResource(
                            if (hidden) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        )
                        val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                        Icon(painter = vector, contentDescription = description)
                    }
                },
                modifier = Modifier
                    .background(Color.White) // Establece el color de fondo a blanco
            )

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Button(
            onClick = { /* Handle registration */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle registration */ },
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
            }
        }
    }
    class PrefixOffsetMapping(private val prefix: String) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = offset + prefix.length

        override fun transformedToOriginal(offset: Int): Int {
            val delta = offset - prefix.length
            return if (delta < 0) 0 else delta
        }
    }
    class PrefixVisualTransformation(private val prefix: String) : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val transformedText = AnnotatedString(prefix, SpanStyle(Color.Gray)) + text

            return TransformedText(transformedText, PrefixOffsetMapping(prefix))
        }
    }
}*/