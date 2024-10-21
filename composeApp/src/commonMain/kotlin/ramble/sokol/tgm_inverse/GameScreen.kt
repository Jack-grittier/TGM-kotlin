package ramble.sokol.tgm_inverse

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import ramble.sokol.tgm_inverse.components.ButtonExitGame
import ramble.sokol.tgm_inverse.components.GameBlockActive
import ramble.sokol.tgm_inverse.components.ProgressGame
import ramble.sokol.tgm_inverse.theme.background_color_collect
import ramble.sokol.tgm_inverse.theme.background_line_game
import ramble.sokol.tgm_inverse.theme.background_progress_game
import ramble.sokol.tgm_inverse.theme.background_progress_game_star
import ramble.sokol.tgm_inverse.theme.background_reward_game
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_text_author
import ramble.sokol.tgm_inverse.theme.color_hello_peterburg
import ramble.sokol.tgm_inverse.theme.color_price_referal
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_bb_game
import tgminverse.composeapp.generated.resources.icon_bb_music
import tgminverse.composeapp.generated.resources.icon_copy_link
import tgminverse.composeapp.generated.resources.icon_music_game
import tgminverse.composeapp.generated.resources.icon_star_1
import tgminverse.composeapp.generated.resources.icon_star_1_true
import tgminverse.composeapp.generated.resources.icon_star_2
import tgminverse.composeapp.generated.resources.icon_star_2_true
import tgminverse.composeapp.generated.resources.icon_star_3
import tgminverse.composeapp.generated.resources.icon_star_3_true
import tgminverse.composeapp.generated.resources.image_back_game
import tgminverse.composeapp.generated.resources.image_center_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.image_verticall_line_game
import tgminverse.composeapp.generated.resources.mont_regular
import kotlin.random.Random

class GameScreen : Screen {

    private val startPosition = mutableStateOf(getRandomNumber())
    private val lastPosition = mutableStateOf(0)
    private val nextPosition = mutableStateOf(0)

    @Composable
    override fun Content() {

        var speed = mutableStateOf(0.1f)

        var offsetY by remember { mutableStateOf(0.dp) }

        val animatedOffsetY by animateDpAsState(
            targetValue = offsetY,
            animationSpec = tween(durationMillis = (1000 / speed.value).toInt())
        )

        var isBoxVisible by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition = updateTransition(targetState = isBoxVisible, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        val boxOffsetY by transition.animateDp(
            transitionSpec = { tween(durationMillis = (1000 / speed.value).toInt()) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        var isBoxVisible2 by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition2 = updateTransition(targetState = isBoxVisible2, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        val boxOffsetY2 by transition2.animateDp(
            transitionSpec = { tween(durationMillis = (1000 / speed.value).toInt()) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        var isBoxVisible3 by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition3 = updateTransition(targetState = isBoxVisible3, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        val boxOffsetY3 by transition3.animateDp(
            transitionSpec = { tween(durationMillis = (1000 / speed.value).toInt()) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        var isBoxVisible4 by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition4 = updateTransition(targetState = isBoxVisible4, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        val boxOffsetY4 by transition4.animateDp(
            transitionSpec = { tween(durationMillis = (1000 / speed.value).toInt()) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }


        var progress by remember { mutableStateOf(0f) }

        LaunchedEffect(Unit) {
            // Пример анимации заполнения прогресс-бара
            while (progress < 1f) {
                progress += 0.01f
                delay(100) // Задержка для анимации
            }
        }


        Box(
            modifier = Modifier.fillMaxSize()
                .background(background_screens),
            contentAlignment = Alignment.Center
        ){

            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(Res.drawable.image_center_line),
                contentDescription = null
            )

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                    if (startPosition.value == 1) {

                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {

                            Box(
                                modifier = Modifier.offset(y = animatedOffsetY)
                            ) {
                                GameBlockActive(
                                    startActive = if (lastPosition.value == 1 && nextPosition.value != 1) false else true,
                                    backgroundClick = if (lastPosition.value == 1 && nextPosition.value != 1) true else false
                                ) {
                                    offsetY = 1000.dp
                                    lastPosition.value = startPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value)
                                    when (nextPosition.value){
                                        2 -> isBoxVisible2 = false
                                        3 -> isBoxVisible3 = false
                                        4 -> isBoxVisible4 = false
                                    }
                                }
                            }
                        }

                    }

                    if (nextPosition.value == 1) {

                            Box(
                                modifier = Modifier.offset(y = boxOffsetY)
                            ) {
                                GameBlockActive(
                                    startActive = if (lastPosition.value == 1) false else true,
                                    backgroundClick = if (lastPosition.value == 1) true else false
                                ) {
                                    lastPosition.value = nextPosition.value
                                    startPosition.value = 0
                                    nextPosition.value = getRandomNumber(lastPosition.value)
                                }
                            }

                    }

                }

                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    painter = painterResource(Res.drawable.image_verticall_line_game),
                    contentDescription = null
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                    if (startPosition.value == 2) {

                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {

                            Box(
                                modifier = Modifier.offset(y = animatedOffsetY)
                            ) {
                                GameBlockActive(
                                    startActive = if (lastPosition.value == 2 && nextPosition.value != 2) false else true,
                                    backgroundClick = if (lastPosition.value == 2 && nextPosition.value != 2) true else false
                                ) {
                                    offsetY = 1000.dp
                                    lastPosition.value = startPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value)
                                    when (nextPosition.value){
                                        1 -> isBoxVisible = false
                                        3 -> isBoxVisible3 = false
                                        4 -> isBoxVisible4 = false
                                    }
                                }
                            }
                        }

                    }

                    if (nextPosition.value == 2) {

                        Box(
                            modifier = Modifier.offset(y = boxOffsetY2)
                        ) {
                            GameBlockActive(
                                startActive = if (lastPosition.value == 2) false else true,
                                backgroundClick = if (lastPosition.value == 2) true else false
                            ) {
                                lastPosition.value = nextPosition.value
                                startPosition.value = 0
                                nextPosition.value = getRandomNumber(lastPosition.value)
                            }
                        }

                    }

                }

                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    painter = painterResource(Res.drawable.image_verticall_line_game),
                    contentDescription = null
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                    if (startPosition.value == 3) {

                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {

                            Box(
                                modifier = Modifier.offset(y = animatedOffsetY)
                            ) {
                                GameBlockActive(startActive = if (lastPosition.value == 3 && nextPosition.value != 3) false else true,
                                    backgroundClick = if (lastPosition.value == 3 && nextPosition.value != 3) true else false
                                ) {
                                    offsetY = 1000.dp
                                    lastPosition.value = startPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value)
                                    when (nextPosition.value){
                                        1 -> isBoxVisible = false
                                        2 -> isBoxVisible2 = false
                                        4 -> isBoxVisible4 = false
                                    }
                                }
                            }
                        }

                    }

                    if (nextPosition.value == 3) {

                        Box(
                            modifier = Modifier.offset(y = boxOffsetY3)
                        ) {
                            GameBlockActive(
                                startActive = if (lastPosition.value == 3) false else true,
                                backgroundClick = if (lastPosition.value == 3) true else false
                            ) {
                                lastPosition.value = nextPosition.value
                                startPosition.value = 0
                                nextPosition.value = getRandomNumber(lastPosition.value)
                            }
                        }

                    }

                }

                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    painter = painterResource(Res.drawable.image_verticall_line_game),
                    contentDescription = null
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                    if (startPosition.value == 4) {

                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {

                            Box(
                                modifier = Modifier.offset(y = animatedOffsetY)
                            ) {
                                GameBlockActive(
                                    startActive = if (lastPosition.value == 4 && nextPosition.value != 4) false else true,
                                    backgroundClick = if (lastPosition.value == 4 && nextPosition.value != 4) true else false
                                ) {
                                    offsetY = 1000.dp
                                    lastPosition.value = startPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value)
                                    when (nextPosition.value){
                                        1 -> isBoxVisible = false
                                        2 -> isBoxVisible2 = false
                                        3 -> isBoxVisible3 = false
                                    }
                                }
                            }
                        }

                    }

                    if (nextPosition.value == 4) {

                        Box(
                            modifier = Modifier.offset(y = boxOffsetY4)
                        ) {
                            GameBlockActive(
                                startActive = if (lastPosition.value == 4) false else true,
                                backgroundClick = if (lastPosition.value == 4) true else false
                            ) {
                                lastPosition.value = nextPosition.value
                                startPosition.value = 0 
                                nextPosition.value = getRandomNumber(lastPosition.value)
                            }
                        }

                    }

                }


            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 11.dp, horizontal = 20.dp),
                contentAlignment = Alignment.TopCenter
            ){

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(103.dp)
                        .clip(RoundedCornerShape(topStart = 120.dp, topEnd = 120.dp, bottomEnd = 19.dp, bottomStart = 19.dp))
                        .background(color_hello_peterburg),
                    contentAlignment = Alignment.TopCenter
                ){

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(background_reward_game),
                            contentAlignment = Alignment.Center
                        ){

                            Row (
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Text(
                                    text = "500",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp,
                                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                    )
                                )

                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                                Image(
                                    modifier = Modifier.width(24.dp),
                                    painter = painterResource(Res.drawable.icon_bb_game),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )

                            }

                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ){

                            Box(modifier = Modifier.padding(horizontal = 42.dp)) {

                                ProgressGame(progress)

                            }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 25.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Box(
                                    modifier = Modifier
                                        .width(9.dp)
                                        .height(9.dp)
                                        .clip(RoundedCornerShape(25.dp))
                                        .background(background_progress_game)
                                )

                                Image(
                                    painter = painterResource(
                                        if (progress >= 0.32f) Res.drawable.icon_star_1_true
                                        else Res.drawable.icon_star_1
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )

                                Image(
                                    painter = painterResource(
                                        if (progress >= 0.63f) Res.drawable.icon_star_2_true
                                        else Res.drawable.icon_star_2
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                )

                                Image(
                                    painter = painterResource(
                                        if (progress >= 0.99f) Res.drawable.icon_star_3_true
                                        else Res.drawable.icon_star_3
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )

                            }



                        }

                    }



                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(Res.drawable.image_back_game),
                        contentDescription = "image_game",
                        contentScale = ContentScale.Crop
                    )

                }

            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp, horizontal = 20.dp),
                contentAlignment = Alignment.BottomCenter
            ){

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Row (
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Image(
                            modifier = Modifier.height(24.dp).width(24.dp),
                            painter = painterResource(Res.drawable.icon_music_game),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ){

                            Text(
                                text = "Song:",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                    fontWeight = FontWeight(700),
                                    color = background_color_collect,
                                )
                            )

                            Spacer(modifier = Modifier.padding(vertical = 2.dp))

                            Text(
                                text = "Tuda syuda millionerrrrrrrrrrrfjhkhdsghhfkg",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                    fontWeight = FontWeight(700),
                                    color = background_color_collect,
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )


                        }

                    }

                    Box(modifier = Modifier.weight(0.7f)) {

                        ButtonExitGame("Выйти") {

                        }

                    }

                }

            }

        }

    }

    fun getRandomNumber() : Int{
        val randomNumber = Random.nextInt(1, 5)
        return randomNumber
    }

    fun getRandomNumber(excludedNumber: Int): Int {
        val numbers = (1..4).filter { it != excludedNumber }
        return numbers[Random.nextInt(numbers.size)]
    }


}