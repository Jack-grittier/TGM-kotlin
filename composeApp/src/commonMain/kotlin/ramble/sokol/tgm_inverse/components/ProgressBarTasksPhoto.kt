package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarTasksPhoto(){
    CircularProgressIndicator(
        modifier = Modifier
            .size(30.dp),
        color = Color.White
    )
}