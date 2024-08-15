package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import ramble.sokol.tgm_inverse.theme.background_screens
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.liderboard
import tgminverse.composeapp.generated.resources.tasks_navbar

class LiderboardScreen(
    val modifier: Modifier
) : Screen {

    @Composable
    override fun Content() {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(background_screens)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

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

        }

    }

}