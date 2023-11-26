import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.tunewave.R
import com.example.tunewave.ui.init.DrawerItemRow
import com.example.tunewave.ui.navigation.DrawerItem


@Composable
fun MyNavigationDrawer(
    name: String,
    email: String,
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blackTunewave)) // Set background color to black
            .padding(16.dp)
    ) {
        Text(text = name, color = Color.White)
        Text(text = email, color = Color.White)
        Divider(color = Color.Gray, thickness = 0.5.dp)
        items.forEach {
            DrawerItemRow(it, onItemClick)
        }
    }
}
