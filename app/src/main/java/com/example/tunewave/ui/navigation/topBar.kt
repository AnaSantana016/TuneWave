import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Tunewave", color = Color.Black)
        },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black // Set the color for the menu icon
                )
            }
        },
        //backgroundColor = Color.Black, // Set the background color for the top app bar
        //contentColor = Color.White, // Set the content color for the top app bar (title and icons)
        //elevation = 4.dp
    )
}
