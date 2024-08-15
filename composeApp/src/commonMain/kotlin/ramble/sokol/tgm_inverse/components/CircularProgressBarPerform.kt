package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.core.InternalFlexibleApi
import com.skydoves.flexible.core.toPx

@OptIn(InternalFlexibleApi::class)
@Composable
fun CircularProgressBarPerform() {

    CircularProgressIndicator(
        modifier = Modifier.width(18.dp),
        strokeWidth = 3.dp,
        color = Color.White,
    )

}