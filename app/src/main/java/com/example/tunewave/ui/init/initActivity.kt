package com.example.tunewave.ui.init

import TopBar
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tunewave.R
import com.example.tunewave.ui.navigation.DrawerItem
import com.example.tunewave.ui.theme.TuneWaveTheme
import kotlinx.coroutines.launch

class InitActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TuneWaveTheme {
                Surface {
                    ScaffoldExample()
                }
            }
        }
        supportActionBar?.hide() // Hide the default ActionBar
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
                // Add your desired content here
                val listaDeCanciones: MutableList<InitActivity.Cancion> = mutableListOf()
                listaDeCanciones.add(InitActivity.Cancion("Canción 1", "Artista 1"))
                listaDeCanciones.add(InitActivity.Cancion("Canción 2", "Artista 2"))
                listaDeCanciones.add(InitActivity.Cancion("Canción 3", "Artista 3"))
                listaDeCanciones.add(InitActivity.Cancion("Canción 4", "Artista 4"))
                listaDeCanciones.add(InitActivity.Cancion("Canción 5", "Artista 5"))
                InitScreen(listaDeCanciones)
            }
        }
    }
}

@Composable
fun InitScreen(canciones: List<InitActivity.Cancion>) {
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
        // Capa 2: Contenido
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                // Mostrar la lista de canciones
                CancionesList(canciones)
            }

            item {
                // Mostrar lista de recomendados
                Spacer(modifier = Modifier.height(16.dp))
                RecomendadosList(canciones)
            }

            item {
                // Mostrar lista de top tracks
                Spacer(modifier = Modifier.height(16.dp))
                TopTracksList(canciones)
            }
        }
    }
}

@Composable
fun RecomendadosList(recomendados: List<InitActivity.Cancion>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.blackTunewave))
            .border(2.dp, color = colorResource(id = R.color.blueTunewave))
            .padding(16.dp)
    ) {
        Text(
            text = "Recomendados",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = colorResource(id = R.color.blueTunewave),
            style = MaterialTheme.typography.titleLarge
        )
        // Mostrar cada canción en la lista de recomendados
        for (cancion in recomendados) {
            CancionItem(cancion)
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}

@Composable
fun TopTracksList(topTracks: List<InitActivity.Cancion>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.blackTunewave))
            .border(2.dp, color = colorResource(id = R.color.blueTunewave))
            .padding(16.dp)
    ) {
        Text(
            text = "Top Tracks",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = colorResource(id = R.color.blueTunewave),
            style = MaterialTheme.typography.titleLarge
        )
        // Mostrar cada canción en la lista de top tracks
        for (cancion in topTracks) {
            CancionItem(cancion)
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}



@Composable
fun CancionesList(canciones: List<InitActivity.Cancion>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.blackTunewave))
            .border(2.dp, color = colorResource(id = R.color.blueTunewave))
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Canciones",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = colorResource(id = R.color.blueTunewave),
            style = MaterialTheme.typography.titleLarge
        )
        // Mostrar cada canción en la lista
        for (cancion in canciones) {
            CancionItem(cancion)
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}

@Composable
fun CancionItem(cancion: InitActivity.Cancion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Aquí puedes manejar la selección de la canción
                // Por ejemplo, puedes abrir una nueva pantalla o realizar alguna acción específica
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Aquí puedes personalizar cómo se muestra cada elemento de la lista
        Text(text = cancion.nombre, color = colorResource(id = R.color.blueTunewave))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = cancion.artista, color = Color.White)
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
            background = Color.Black,
            primary = Color.White
        )
    ) {
        ModalDrawerSheet {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.blackTunewave))
                    .border(2.dp, color = colorResource(id = R.color.blueTunewave))
                    .padding(16.dp)
                    .widthIn(max = 0.8.dp),
                verticalArrangement = Arrangement.Center // Alinea los elementos verticalmente en el centro
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally // Alinea los elementos horizontalmente en el centro
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.portada_nicki),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Añade espacio entre la imagen y el texto
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally // Alinea los elementos horizontalmente en el centro
                    ) {
                        Text(
                            text = name,
                            modifier = Modifier.padding(16.dp),
                            color = colorResource(id = R.color.blueTunewave)
                        )
                        Text(
                            text = email,
                            modifier = Modifier.padding(16.dp),
                            color = Color.White
                        )
                    }
                }
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
            .background(colorResource(id = R.color.blackTunewave))
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

