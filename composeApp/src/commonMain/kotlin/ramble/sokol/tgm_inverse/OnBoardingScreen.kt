package ramble.sokol.tgm_inverse


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.text_navbar
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.get_bonuses
import tgminverse.composeapp.generated.resources.icon_back_onboarding
import tgminverse.composeapp.generated.resources.icon_button_finish
import tgminverse.composeapp.generated.resources.icon_first_liderboard
import tgminverse.composeapp.generated.resources.icon_home_onboarding
import tgminverse.composeapp.generated.resources.icon_next_onboarding
import tgminverse.composeapp.generated.resources.icon_on_boarding_top
import tgminverse.composeapp.generated.resources.icon_onboarding_first
import tgminverse.composeapp.generated.resources.icon_onboarding_fourth
import tgminverse.composeapp.generated.resources.icon_onboarding_second
import tgminverse.composeapp.generated.resources.icon_onboarding_third
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.mont_bold
import tgminverse.composeapp.generated.resources.mont_regular

class OnBoardingScreen(
    val userEntityCreate: UserEntityCreate
) : Screen {

    private lateinit var currentScreen: MutableState<Int>
    private lateinit var navigator: Navigator

    @Composable
    override fun Content() {

        currentScreen = remember {
            mutableStateOf(0)
        }

        navigator = LocalNavigator.current!!

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
                    text = "Your first entry into TGM was on ${currentScreen.value}, during which time you were able to:",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_bold)),
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

                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                Text(
                    text =
                    if (currentScreen.value == 0){
                        "Your balance has reached ${currentScreen.value}"
                    }else if (currentScreen.value == 1){
                        "You have listened to ${currentScreen.value} tracks by young artists!"
                    }else if (currentScreen.value == 2){
                        "You invited ${currentScreen.value} friends to TGM community!"
                    }else {
                        "You have completed ${currentScreen.value} tasks!"
                    },
                    style = TextStyle(
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_bold)),
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 24.dp))

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 9.dp),
                    painter =
                    if (currentScreen.value == 0){
                        painterResource(Res.drawable.icon_onboarding_first)
                    }else if (currentScreen.value == 1){
                        painterResource(Res.drawable.icon_onboarding_second)
                    }else if (currentScreen.value == 2){
                        painterResource(Res.drawable.icon_onboarding_third)
                    }else {
                        painterResource(Res.drawable.icon_onboarding_fourth)
                    },
                    contentDescription = "imageLine",
                    contentScale = ContentScale.FillHeight
                )

            }

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (currentScreen.value == 0) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        Image(
                            modifier = Modifier
                                .height(44.dp)
                                .width(44.dp)
                                .clickable {
                                    navigator?.push(MainMenuScreen(userEntityCreate))
                                },
                            painter = painterResource(Res.drawable.icon_home_onboarding),
                            contentDescription = "imageLine",
                            contentScale = ContentScale.Crop
                        )

                    }
                }else{
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        Image(
                            modifier = Modifier
                                .height(44.dp)
                                .width(44.dp)
                                .clickable {
                                    currentScreen.value -= 1
                                },
                            painter = painterResource(Res.drawable.icon_back_onboarding),
                            contentDescription = "imageLine",
                            contentScale = ContentScale.Crop
                        )

                    }
                }

                if (currentScreen.value == 3){
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {

                        Image(
                            modifier = Modifier
                                .height(44.dp)
                                .clickable {
                                    navigator?.push(MainMenuScreen(userEntityCreate))
                                },
                            painter = painterResource(Res.drawable.icon_button_finish),
                            contentDescription = "imageLine",
                            contentScale = ContentScale.Crop
                        )

                    }
                }else {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {

                        Image(
                            modifier = Modifier
                                .height(44.dp)
                                .width(44.dp)
                                .clickable {
                                    currentScreen.value += 1
                                },
                            painter = painterResource(Res.drawable.icon_next_onboarding),
                            contentDescription = "imageLine",
                            contentScale = ContentScale.Crop
                        )

                    }
                }

            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

        }

    }


}