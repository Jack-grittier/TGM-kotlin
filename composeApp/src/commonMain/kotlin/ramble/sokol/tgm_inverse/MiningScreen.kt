package ramble.sokol.tgm_inverse

import AnimatedArcProgressBar
import ProgressBarDemo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.inmo.tgbotapi.webapps.webApp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.center_circle_playlist
import ramble.sokol.tgm_inverse.theme.center_circle_playlist_shadow
import ramble.sokol.tgm_inverse.theme.color_background_referal
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.get_bonuses
import tgminverse.composeapp.generated.resources.icon_play_music
import tgminverse.composeapp.generated.resources.image_back_circle_playlist
import tgminverse.composeapp.generated.resources.image_back_mining
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar
import tgminverse.composeapp.generated.resources.test_photo

class MiningScreen (
    val modifier: Modifier
) : Screen {

    @Composable
    override fun Content() {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(background_screens)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(Res.drawable.image_back_mining),
                        contentDescription = "image_play_game",
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier.width(300.dp).height(300.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth().padding(25.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Surface(
                                modifier = Modifier.size(250.dp),
                                shape = CircleShape
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.test_photo),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Image(
                                modifier = Modifier.width(109.dp).height(109.dp),
                                painter = painterResource(Res.drawable.image_back_circle_playlist),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )


                            Surface(
                                modifier = Modifier.size(68.dp),
                                shape = CircleShape
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(68.dp)
                                        .background(center_circle_playlist)
                                )
                            }
                        }

                        Image(
                            modifier = Modifier
                                .height(86.dp)
                                .width(86.dp)
                                .padding(bottom = 14.dp, end = 14.dp),
                            painter = painterResource(Res.drawable.icon_play_music),
                            contentDescription = "imageLine"
                        )

                        ProgressBarDemo()

                    }

                }

                Text(
                    modifier = Modifier.fillMaxWidth().padding(),
                    text = "30646",
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                painter = painterResource(Res.drawable.image_line),
                contentDescription = "imageLine"
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(Res.string.get_bonuses),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            val dg = webApp.initData

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(width = 5.dp, color = Color.White, shape = RoundedCornerShape(15.dp)),
                shape = RoundedCornerShape(15.dp),
                value = dg,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                ),
                onValueChange = {

                },
                label = {
                    Text(
                        dg,
                        style = TextStyle(
                            color = Color.Green,
                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(500)
                        )
                    ) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }

    }
}