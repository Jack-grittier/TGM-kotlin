package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarTasks(){
    CircularProgressIndicator(
        modifier = Modifier
            .size(60.dp),
        color = Color.White
    )
}