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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.components.RatingPersonMusicality
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_wallet_item
import ramble.sokol.tgm_inverse.theme.color_background_referal
import ramble.sokol.tgm_inverse.theme.color_hello_peterburg
import ramble.sokol.tgm_inverse.theme.color_price_referal
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_active
import tgminverse.composeapp.generated.resources.icon_arrow_to_game
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar

class MusicalityScreen(
    val modifier: Modifier
) : Screen {

    @Composable
    override fun Content() {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(background_screens)
                .padding(top = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

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
                                text = "Добавь друга и получи",
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

                    }

                    Spacer(modifier = Modifier.padding(vertical = 3.dp))

                }

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .clip(RoundedCornerShape(26.dp))
                        .background(color_background_referal),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(Res.drawable.image_play_game),
                        contentDescription = "image_play_game",
                        contentScale = ContentScale.Crop
                    )

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){


                        Text(
                            text = "Мини-игра",
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

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Рейтинг друзей",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                RatingPersonMusicality(
                    price = "500",
                    name = "LALALLALALA"
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                RatingPersonMusicality(
                    price = "500",
                    name = "LALALLALALA"
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                RatingPersonMusicality(
                    price = "500",
                    name = "LALALLALALA"
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                RatingPersonMusicality(
                    price = "500",
                    name = "LALALLALALA"
                )

            }

        }

    }
}