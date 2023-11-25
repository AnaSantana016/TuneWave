package com.example.tunewave.ui.init

import TopBar
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.tunewave.R
import com.example.tunewave.ui.navigation.DrawerItem
import kotlinx.coroutines.launch

class InitActivity : AppCompatActivity() {

    private val listaDeCanciones: MutableList<Cancion> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.drawable.fondo) // Reemplaza con el layout correcto

        // Agregar algunas canciones de ejemplo
        listaDeCanciones.add(Cancion("Canción 1", "Artista 1"))
        listaDeCanciones.add(Cancion("Canción 2", "Artista 2"))
        listaDeCanciones.add(Cancion("Canción 3", "Artista 3"))

        // Puedes realizar operaciones con la lista de canciones según tus necesidades.
    }

    // Clase de ejemplo para representar una canción
    data class Cancion(val nombre: String, val artista: String)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                name = "Your Name",
                email = "your.email@example.com",
                items = listOf(
                    DrawerItem.PROFILE,
                    DrawerItem.HOME,
                    DrawerItem.SETTINGS,
                    DrawerItem.MY_FAVOURITES,
                    DrawerItem.MY_LIKES,
                    DrawerItem.DISCOVER_MUSIC,
                    DrawerItem.SIGN_OUT
                ),
                onItemClick = { selectedItem ->
                    // Handle item click here
                    when (selectedItem) {
                        // Handle each drawer item click
                        else -> {}
                    }
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(onMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                //RegisterForm()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    name: String,
    email: String,
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = Color.Black, // Establecer el color de fondo a negro
            primary = Color.White // Establecer el color primario (puede ser blanco para el texto)
        )
    ) {
        ModalDrawerSheet {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black // Color del texto
                )
                Text(
                    text = email,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black // Color del texto
                )
                Divider(color = Color.Gray, thickness = 0.5.dp)
                items.forEach {
                    DrawerItemRow(it, onItemClick)
                }
            }
        }
    }
}

@Composable
fun DrawerItemRow(item: DrawerItem, onItemClick: (DrawerItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(item)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = item.text, tint = colorResource(id = R.color.blueTunewave))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.text, color = colorResource(id = R.color.blueTunewave))
    }
}



@Composable
fun MyNavigationDrawer(
    name: String,
    email: String,
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = name)
        Text(text = email)
        Divider()
        items.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(it)
                    }
            ) {
                Icon(imageVector = it.icon, contentDescription = it.text)
                Text(text = it.text)
            }
        }
    }
}

