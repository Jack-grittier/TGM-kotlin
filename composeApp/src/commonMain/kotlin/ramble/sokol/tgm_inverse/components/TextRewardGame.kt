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
fun TextRewardGame(
    rewardMusic: Long,
    duration: Long
) : Long{

    val second = ((duration.toFloat() / rewardMusic.toFloat()) * 1000).toLong()


    //val divide = if (result < 1) 1L else result.toLong()

    var currentReward by remember { mutableStateOf(0L) }
    var currentSecond by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        while (currentSecond < duration*1000) {
            delay(second) // Задержка для анимации
            currentReward += 1 // Увеличиваем прогресс
            currentSecond += second
        }
    }

    Text(
        text = currentReward.toString(),
        style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
            fontWeight = FontWeight(400),
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    )

    return currentReward
}