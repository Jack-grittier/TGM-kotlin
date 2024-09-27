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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.text_navbar
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.get_bonuses
import tgminverse.composeapp.generated.resources.icon_home_onboarding
import tgminverse.composeapp.generated.resources.icon_next_onboarding
import tgminverse.composeapp.generated.resources.icon_on_boarding_top
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.mont_regular

class OnBoardingScreen(
    val userEntityCreate: UserEntityCreate
) : Screen {

    @Composable
    override fun Content() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background_screens)
                .padding(top = 40.dp, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){

                Text(
                    text = "Your first entry into TGM was on 07/22/2024, during which time you were able to:",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = text_navbar,
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Image(
                    modifier = Modifier
                        .height(107.dp),
                    painter = painterResource(Res.drawable.icon_on_boarding_top),
                    contentDescription = "imageLine",
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "Your balance has reached 320457",
                    style = TextStyle(
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    )
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ){

                    Image(
                        modifier = Modifier
                            .height(44.dp)
                            .width(44.dp),
                        painter = painterResource(Res.drawable.icon_home_onboarding),
                        contentDescription = "imageLine",
                        contentScale = ContentScale.Crop
                    )

                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ){

                    Image(
                        modifier = Modifier
                            .height(44.dp)
                            .width(44.dp),
                        painter = painterResource(Res.drawable.icon_next_onboarding),
                        contentDescription = "imageLine",
                        contentScale = ContentScale.Crop
                    )

                }

            }

        }

    }


}