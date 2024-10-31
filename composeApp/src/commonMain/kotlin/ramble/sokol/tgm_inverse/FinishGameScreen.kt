package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.web.dom.Col
import ramble.sokol.tgm_inverse.components.ButtonClose
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.data.UserEntityCreateResponse
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_splash
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.back_finish_game
import tgminverse.composeapp.generated.resources.icon_star_finish
import tgminverse.composeapp.generated.resources.icon_star_finish_active
import tgminverse.composeapp.generated.resources.image_line_finish_game
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.test_photo

class FinishGameScreen(
    val userEntityCreate: UserEntityCreate,
    val bodyUserCreate: UserEntityCreateResponse,
    val nameSong: String,
    val reward: Long,
    val countStar: Int
) : Screen {

    private lateinit var navigator: Navigator

    @Composable
    override fun Content() {

        navigator = LocalNavigator.current!!

        Box(
            modifier = Modifier.fillMaxSize()
                .background(background_splash),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.back_finish_game),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column (
                modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ){

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = nameSong,
                            style = TextStyle(
                                fontSize = 28.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                fontWeight = FontWeight(600),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )

                        Spacer(modifier = Modifier.padding(vertical = 12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (countStar > 0){
                                Image(
                                    painter = painterResource(Res.drawable.icon_star_finish_active),
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp).width(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }else{
                                Image(
                                    painter = painterResource(Res.drawable.icon_star_finish),
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp).width(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                            if (countStar > 1){
                                Image(
                                    painter = painterResource(Res.drawable.icon_star_finish_active),
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp).width(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }else{
                                Image(
                                    painter = painterResource(Res.drawable.icon_star_finish),
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp).width(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                            if (countStar > 2){
                                Image(
                                    painter = painterResource(Res.drawable.icon_star_finish_active),
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp).width(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }else{
                                Image(
                                    painter = painterResource(Res.drawable.icon_star_finish),
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp).width(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                        }

                        Spacer(modifier = Modifier.padding(vertical = 24.dp))

                        Image(
                            painter = painterResource(Res.drawable.image_line_finish_game),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(1.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.padding(vertical = 24.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "+${reward}BB",
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                fontWeight = FontWeight(400),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )


                    }

                }

                Box(modifier = Modifier.padding(horizontal = 16.dp)) {

                    ButtonClose("Close") {
                        navigator.push(MainMenuScreen(userEntityCreate, bodyUserCreate, 0, null))
                    }

                }

            }

        }

    }
}