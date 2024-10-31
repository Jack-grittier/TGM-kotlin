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
fun TextReward(
    current: String,
    compl: String,
    start: String,
    rewardMining: Long
) : Boolean{

    val complInstant = Instant.parse(compl)
    val currentInstant = Instant.parse(current)
    val startInstant = Instant.parse(start)

    val differenceInMillis = complInstant.toEpochMilliseconds() - startInstant.toEpochMilliseconds()
    val differenceInMillis2 = complInstant.toEpochMilliseconds() - currentInstant.toEpochMilliseconds()
    val seconds2 = (differenceInMillis2.toFloat() / 1000) / 60
    val seconds = (differenceInMillis.toFloat() / 1000) / 60
    var currentProgress =  rewardMining - (rewardMining *seconds2/seconds)
    if (currentProgress < 0){
        currentProgress = 0F
    }
    var currentReward by remember { mutableStateOf(currentProgress.toLong()) }
    val countRewardMinute = (differenceInMillis)/rewardMining

    LaunchedEffect(Unit) {
        while (currentReward < rewardMining) {
            delay(countRewardMinute) // Задержка для анимации
            currentReward += 1 // Увеличиваем прогресс
        }
    }

    Text(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        text = currentReward.toString(),
        style = TextStyle(
            fontSize = 32.sp,
            lineHeight = 32.sp,
            fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
            fontWeight = FontWeight(400),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    )

    if (currentProgress < rewardMining){
        return false
    }else{
        return true
    }
}