package ramble.sokol.tgm_inverse

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.web.dom.Main
import ramble.sokol.tgm_inverse.theme.background_splash
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_logo_splash_screen
import tgminverse.composeapp.generated.resources.image_background_splash_screen


class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val transition = rememberInfiniteTransition(label = "")
        val alpha by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        LaunchedEffect(
            key1 = true
        ) {
            delay(3000L)
            navigator?.push(MainMenuScreen())
//            if (settings.loadToken() != null){
//                navigator?.push(AreasScreen())
//            }else{
//                navigator?.push(LoginScreen())
//            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background_splash)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){

                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(Res.drawable.image_background_splash_screen),
                    contentDescription = "imageSplashScreen"
                )

                Image(
                    modifier = Modifier
                        .height(51.dp)
                        .fillMaxWidth()
                        .alpha(alpha = alpha),
                    painter = painterResource(Res.drawable.icon_logo_splash_screen),
                    contentDescription = "imageSplashScreen"
                )

            }

        }

    }

}