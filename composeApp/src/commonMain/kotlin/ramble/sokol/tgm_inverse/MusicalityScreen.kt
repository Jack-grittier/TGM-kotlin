package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.internal.JSJoda.DateTimeParseException
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.skiko.ClipboardManager
import ramble.sokol.tgm_inverse.components.PhotoFirstRating
import ramble.sokol.tgm_inverse.components.PhotoOtherRating
import ramble.sokol.tgm_inverse.components.RatingPersonLiderboard
import ramble.sokol.tgm_inverse.components.RatingPersonMusicality
import ramble.sokol.tgm_inverse.components.TextAirdrop
import ramble.sokol.tgm_inverse.components.TextGame
import ramble.sokol.tgm_inverse.model.data.LeaderBoardEntity
import ramble.sokol.tgm_inverse.model.data.LeaderboardReferalEntity
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_copy_link
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_text_link
import ramble.sokol.tgm_inverse.theme.background_text_link_2
import ramble.sokol.tgm_inverse.theme.background_wallet_item
import ramble.sokol.tgm_inverse.theme.color_background_referal
import ramble.sokol.tgm_inverse.theme.color_hello_peterburg
import ramble.sokol.tgm_inverse.theme.color_price_referal
import ramble.sokol.tgm_inverse.theme.text_mini_game_block
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_active
import tgminverse.composeapp.generated.resources.icon_arrow_to_game
import tgminverse.composeapp.generated.resources.icon_copy_link
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar
import kotlin.time.Duration.Companion.milliseconds

class MusicalityScreen(
    val modifier: Modifier,
    val userEntityCreate: UserEntityCreate,
    val dateMiniGame: String
) : Screen {

    private lateinit var apiRepo: ApiRepository
    private lateinit var listLeader: MutableState<List<LeaderboardReferalEntity>>
    private lateinit var currentTime: MutableState<String>
    private lateinit var miniGameTextVisible: MutableState<Boolean?>
    private lateinit var differenceInMillis: MutableState<Long>
    private lateinit var testText: MutableState<String>

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val toaster = rememberToasterState()

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()
        listLeader = remember {
            mutableStateOf(listOf())
        }

        testText = remember {
            mutableStateOf("")
        }

        differenceInMillis = remember {
            mutableStateOf(0L)
        }

        miniGameTextVisible = remember {
            mutableStateOf(false)
        }

        currentTime = remember {
            mutableStateOf(getCurrentUtcDateTime())
        }

        scope.launch {
            getLeader("1", "25")
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(background_screens)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

            item {

                Spacer(modifier = Modifier.padding(top = 16.dp))

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    painter = painterResource(Res.drawable.image_line),
                    contentDescription = "imageLine"
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(26.dp))
                            .background(color_background_referal),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Text(
                                    text = "Add a friend and get",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                        fontWeight = FontWeight(700),
                                        color = Color.White,
                                    )
                                )

                                Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                                Text(
                                    text = "+500",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                        fontWeight = FontWeight(400),
                                        color = color_price_referal,
                                        textAlign = TextAlign.Start,
                                    )
                                )

                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                                Text(
                                    text = "BB",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color.White,
                                        textAlign = TextAlign.Start,
                                    )
                                )

                            }

                            Spacer(modifier = Modifier.padding(vertical = 3.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(55.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(background_text_link),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = 16.dp,
                                                bottom = 16.dp,
                                                start = 17.dp,
                                                end = 8.dp
                                            ),
                                        //text = "t.me/INVOKE_POSTIDEAS_BOT/tgmapp/startapp=",
                                        text = "t.me/INVOKE_POSTIDEAS_BOT/tgmapp/startapp=${userEntityCreate.id}",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                            fontWeight = FontWeight(700),
                                            color = background_text_link_2,
                                            textDecoration = TextDecoration.Underline,
                                            textAlign = TextAlign.Start
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }

                                Spacer(modifier = Modifier.padding(4.5.dp))

                                Box(
                                    modifier = Modifier
                                        .width(55.dp)
                                        .height(55.dp)
                                        .clip(RoundedCornerShape(18.dp))
                                        .background(background_copy_link)
                                        .clickable {
                                            copyToClipboard("t.me/INVOKE_POSTIDEAS_BOT/tgmapp?startapp=${userEntityCreate.id}")
                                            //copyToClipboard("t.me/INVOKE_POSTIDEAS_BOT/tgmapp?startapp=")
                                            toaster.show(
                                                message = "Hello world!",
                                                type = ToastType.Normal,
                                                duration = 3000.milliseconds
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {


                                    Image(
                                        modifier = Modifier.height(25.dp).width(25.dp),
                                        painter = painterResource(Res.drawable.icon_copy_link),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )

                                }

                            }

                        }

                        Spacer(modifier = Modifier.padding(vertical = 3.dp))

                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(color_background_referal)
                            .clickable(
                                onClick = {
                                    if (miniGameTextVisible.value == false) {
                                        navigator?.push(GameScreen())
                                    }
                                },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(Res.drawable.image_play_game),
                            contentDescription = "image_play_game",
                            contentScale = ContentScale.Crop
                        )

                        if (miniGameTextVisible.value == true){

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "Mini-Game:",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                        fontWeight = FontWeight(400),
                                        color = text_mini_game_block,
                                        textAlign = TextAlign.Center,
                                    )
                                )

                                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                                TextGame(
                                    compl = dateMiniGame,
                                    current = currentTime.value
                                )

                            }

                        }else {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {


                                Text(
                                    text = "Mini-Game",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                    )
                                )

                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(32.dp)
                                        .clip(RoundedCornerShape(13.dp))
                                        .background(color_hello_peterburg),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        painter = painterResource(Res.drawable.icon_arrow_to_game),
                                        contentDescription = "image_play_game"
                                    )

                                }

                            }

                        }

                    }

                    if (listLeader.value.size != 0) {

                        Spacer(modifier = Modifier.padding(vertical = 16.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Friends rating",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                fontWeight = FontWeight(400),
                                color = Color.White,
                                textAlign = TextAlign.Start,
                            )
                        )

                        Spacer(modifier = Modifier.padding(vertical = 12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (listLeader.value.size >= 2) {
                                val second = listLeader.value[1]
                                PhotoOtherRating(2, second.username, second.amount.toString())
                            }
                            if (listLeader.value.size >= 1) {
                                val first = listLeader.value[0]
                                PhotoFirstRating(first.username, first.amount.toString())
                            }
                            if (listLeader.value.size >= 3) {
                                val third = listLeader.value[2]
                                PhotoOtherRating(3, third.username, third.amount.toString())
                            }


                        }

                        Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    }
                }
            }

            if (listLeader.value.size > 3) {
                val newListLeader = listLeader.value.subList(3, listLeader.value.size)
                items(newListLeader) { leader: LeaderboardReferalEntity ->
                    RatingPersonMusicality(
                        leader.amount.toString(), leader.username
                    )
                    Spacer(modifier = Modifier.padding(vertical = 6.dp))
                }
            }

            item{
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }

        }

    }

    fun copyToClipboard(text: String) {
        // Используем API для работы с буфером обмена
        window.navigator.clipboard.writeText(text).then(
            { },
            { window.alert("Не удалось скопировать текст.") }
        )
    }

    private suspend fun getLeader(page: String, limit: String){
        val body = apiRepo.getLeaderBoardReferal(page = page, limit = limit, initData = userEntityCreate.initData)
        listLeader.value = body
    }

    fun getCurrentUtcDateTime() : String {
        val currentInstant: Instant = Clock.System.now()
        val utcDateTime = currentInstant.toLocalDateTime(TimeZone.UTC)
        val currentTimeThis = "${utcDateTime.date}T${utcDateTime.hour.toString().padStart(2, '0')}:${utcDateTime.minute.toString().padStart(2, '0')}:${utcDateTime.second.toString().padStart(2, '0')}.000Z"

        val comparisonResult = compareDates(dateMiniGame, currentTimeThis)
        testText.value = comparisonResult.toString()
        when {
            comparisonResult < 0 -> {
                miniGameTextVisible.value = false
            }
            comparisonResult >= 0 -> miniGameTextVisible.value = true
        }
        return currentTimeThis
    }

    fun compareDates(date1: String, date2: String): Int {
        return try {
            val instant1 = Instant.parse(date1)
            val instant2 = Instant.parse(date2)
            instant1.compareTo(instant2)
        } catch (e: DateTimeParseException) {
            0
        }
    }

}