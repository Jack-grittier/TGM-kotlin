package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.Font
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res

@Composable
fun TextGame(
    current: String,
    compl: String
){

    val complInstant = Instant.parse(compl)
    val currentInstant = Instant.parse(current)

    // Вычисление разницы в миллисекундах
    val differenceInMillis = complInstant.toEpochMilliseconds() - currentInstant.toEpochMilliseconds()

    var progress by remember { mutableStateOf(differenceInMillis) }

    val minutes = (progress / (1000 * 60)) % 60
    val hours = (progress / (1000 * 60 * 60)) % 24
    val days = progress / (1000 * 60 * 60 * 24)

    var formattedTime by remember { mutableStateOf("${days.toString().padStart(2, '0')}:" +
            "${hours.toString().padStart(2, '0')}:" +
            "${minutes.toString().padStart(2, '0')}") }

    LaunchedEffect(Unit) {
        while (progress >= 0) {
            progress -= 60000
            val minutes = (progress / (1000 * 60)) % 60
            val hours = (progress / (1000 * 60 * 60)) % 24
            val days = progress / (1000 * 60 * 60 * 24)
            formattedTime = "${days.toString().padStart(2, '0')}:" +
                    "${hours.toString().padStart(2, '0')}:" +
                    "${minutes.toString().padStart(2, '0')}"
            delay(60000)
        }
    }

    Text(
        text = formattedTime,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
            fontWeight = FontWeight(400),
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    )

}