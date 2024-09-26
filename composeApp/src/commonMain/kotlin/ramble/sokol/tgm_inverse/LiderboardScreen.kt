package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.components.PhotoFirstRating
import ramble.sokol.tgm_inverse.components.PhotoFirstRatingLider
import ramble.sokol.tgm_inverse.components.PhotoOtherRating
import ramble.sokol.tgm_inverse.components.ProgressBarTasks
import ramble.sokol.tgm_inverse.components.RatingPersonLiderboard
import ramble.sokol.tgm_inverse.model.data.LeaderBoardEntity
import ramble.sokol.tgm_inverse.model.data.MusicResponse
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

    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()
        listLeader = remember {
            mutableStateOf(listOf())
        }

        scope.launch {
            getLeader("1", "25")
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .background(background_screens)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

            if (listLeader.value.size == 0){

                ProgressBarTasks()

            }else {

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
                    if (listLeader.value.size>=2) {
                        val second = listLeader.value[1]
                        PhotoOtherRating(2, second.username, second.amount.toString())
                    }
                    if (listLeader.value.size>=1) {
                        val first = listLeader.value[0]
                        PhotoFirstRating(first.username, first.amount.toString())
                    }
                    if (listLeader.value.size>=3) {
                        val third = listLeader.value[2]
                        PhotoOtherRating(3, third.username, third.amount.toString())
                    }

                }


                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                for (i in 1..10) {
                    RatingPersonLiderboard(
                        i.toString(), "1000", "Baby"
                    )
                    if (i != 10) {
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

            }
        }

    }

    suspend fun getLeader(page: String, limit: String){
        val body = apiRepo.getLeaderboard(page = page, limit = limit)
        listLeader.value = body
    }

}