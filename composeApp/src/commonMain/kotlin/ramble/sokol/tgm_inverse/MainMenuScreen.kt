package ramble.sokol.tgm_inverse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.theme.background_navbar
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_splash
import ramble.sokol.tgm_inverse.theme.icon_navbar
import ramble.sokol.tgm_inverse.theme.text_navbar
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_logo_splash_screen
import tgminverse.composeapp.generated.resources.icon_mining
import tgminverse.composeapp.generated.resources.icon_musicality
import tgminverse.composeapp.generated.resources.icon_tasks
import tgminverse.composeapp.generated.resources.mining_navbar
import tgminverse.composeapp.generated.resources.mont_bold
import tgminverse.composeapp.generated.resources.musicality_navbar
import tgminverse.composeapp.generated.resources.tasks_navbar


class MainMenuScreen : Screen {

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {

        var selectedItem by rememberSaveable {
            mutableIntStateOf(1)
        }

        Scaffold(
            modifier = Modifier.background(background_screens),
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .background(background_screens)
                        .fillMaxWidth()
                        .height(87.dp)
                        .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)),
                    containerColor = background_navbar
                ){

                    NavigationBarItem(
                        modifier = Modifier.padding(start = 30.dp),
                        selected = selectedItem == 0,
                        onClick = {
                            selectedItem = 0
                        },
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(Res.drawable.icon_musicality),
                                contentDescription = "Musicality",
                                tint = if (selectedItem == 0) Color.White else icon_navbar
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.musicality_navbar),
                                color = if (selectedItem == 0) Color.White else text_navbar,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(Res.font.mont_bold)),
                                    fontWeight = FontWeight(700)
                                )
                            )
                        },
                        colors = androidx.compose.material3.NavigationBarItemDefaults
                            .colors(
                                indicatorColor = Color.Transparent
                            )
                    )

                    NavigationBarItem(
                        selected = selectedItem == 1,
                        onClick = {
                            selectedItem = 1
                        },
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .width(44.dp),
                                painter = painterResource(Res.drawable.icon_mining),
                                contentDescription = "Mining",
                                tint = if (selectedItem == 1) Color.White else icon_navbar
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.mining_navbar),
                                color = if (selectedItem == 1) Color.White else text_navbar,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(Res.font.mont_bold)),
                                    fontWeight = FontWeight(800)
                                )
                            )
                        },
                        colors = androidx.compose.material3.NavigationBarItemDefaults
                            .colors(
                                indicatorColor = Color.Transparent
                            )
                    )

                    NavigationBarItem(
                        modifier = Modifier.padding(end = 30.dp),
                        selected = selectedItem == 2,
                        onClick = {
                            selectedItem = 2
                        },
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(Res.drawable.icon_tasks),
                                contentDescription = "Tasks",
                                tint = if (selectedItem == 2) Color.White else icon_navbar
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.tasks_navbar),
                                color = if (selectedItem == 2) Color.White else text_navbar,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(Res.font.mont_bold)),
                                    fontWeight = FontWeight(700)
                                )
                            )
                        },
                        colors = androidx.compose.material3.NavigationBarItemDefaults
                            .colors(
                                indicatorColor = Color.Transparent
                            )
                    )

                }
            }
        ) { innerPadding ->
            when (selectedItem){
                0 -> Navigator(MusicalityScreen(modifier = Modifier.padding(innerPadding)))
                1 -> Navigator(MiningScreen(modifier = Modifier.padding(innerPadding)))
                2 -> Navigator(TasksScreen(modifier = Modifier.padding(innerPadding)))
            }

        }

    }

}