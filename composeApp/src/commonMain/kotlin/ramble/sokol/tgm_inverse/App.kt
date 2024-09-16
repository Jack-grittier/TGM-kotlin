package ramble.sokol.tgm_inverse

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import tgminverse.composeapp.generated.resources.*
import ramble.sokol.tgm_inverse.theme.AppTheme
import ramble.sokol.tgm_inverse.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun App() = AppTheme {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    Navigator(SplashScreen())
}

fun getAsyncImageLoader(context: PlatformContext)=
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()
