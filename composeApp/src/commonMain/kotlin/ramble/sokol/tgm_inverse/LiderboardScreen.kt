package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.inmo.tgbotapi.types.MembersLimit
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.components.MyRatingLeaderBoard
import ramble.sokol.tgm_inverse.components.PhotoFirstRating
import ramble.sokol.tgm_inverse.components.PhotoFirstRatingLider
import ramble.sokol.tgm_inverse.components.PhotoOtherRating
import ramble.sokol.tgm_inverse.components.ProgressBarTasks
import ramble.sokol.tgm_inverse.components.RatingPersonLiderboard
import ramble.sokol.tgm_inverse.components.TasksPerform
import ramble.sokol.tgm_inverse.model.data.LeaderBoardEntity
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_screens
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.liderboard
import tgminverse.composeapp.generated.resources.tasks_navbar

class LiderboardScreen(
    val modifier: Modifier,
    val userEntityCreate: UserEntityCreate
) : Screen {

    private lateinit var apiRepo: ApiRepository
    private lateinit var listLeader: MutableState<List<LeaderBoardEntity>>
    private lateinit var myPosition: MutableState<LeaderBoardEntity?>

    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()
        listLeader = remember {
            mutableStateOf(listOf())
        }

        myPosition = remember {
            mutableStateOf(null)
        }

        scope.launch {
            getLeader("1", "25")
            getMyLeader()
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(background_screens)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    //.verticalScroll(rememberScrollState())
            ) {

                if (listLeader.value.size == 0) {

                    ProgressBarTasks()

                } else {

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        painter = painterResource(Res.drawable.image_line),
                        contentDescription = "imageLine"
                    )

                    Spacer(modifier = Modifier.padding(vertical = 16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.liderboard),
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                            fontWeight = FontWeight(400),
                            color = Color.White,
                            textAlign = TextAlign.Start,
                        )
                    )

                    Spacer(modifier = Modifier.padding(top = 24.dp))

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

                    if (listLeader.value.size > 3) {
                        val newListLeader = listLeader.value.subList(3, listLeader.value.size)
                        LazyColumn() {
                            items(newListLeader) { leader: LeaderBoardEntity ->
                                RatingPersonLiderboard(
                                    leader.position.toString(),
                                    leader.amount.toString(),
                                    leader.username
                                )
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                }
            }

            if (myPosition.value != null) {

                Box(
                    modifier = Modifier.fillMaxSize().padding(bottom = 12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    MyRatingLeaderBoard(
                        //myPosition.value!!.amount.toString(),
                        "hdghdshghshgjkhsoavjhjhgshjfhvvnjshdghjkdjfdnjdsfjkds",
                        myPosition.value!!.username,
                        myPosition.value!!.position.toString())

                }
            }

        }

    }

    suspend fun getLeader(page: String, limit: String){
        val body = apiRepo.getLeaderboard(page = page, limit = limit)
        listLeader.value = body
    }

    suspend fun getMyLeader(){
        val body = apiRepo.getMyPositionLeaderBoard(userEntityCreate.initData)
        myPosition.value = body
    }

}