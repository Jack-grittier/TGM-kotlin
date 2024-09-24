package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.tgm_inverse.theme.background_color_close
import ramble.sokol.tgm_inverse.theme.background_color_disconnect
import ramble.sokol.tgm_inverse.theme.background_color_exit_game
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.mont_black
import tgminverse.composeapp.generated.resources.mont_bold

@Composable
fun ButtonExitGame(
    text: String,
    onClick: () -> Unit
)
{
    Button(
        modifier = Modifier
            .height(48.dp)
            .background(
                shape = RoundedCornerShape(size = 30.dp),
                color = background_color_exit_game
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = background_color_exit_game
        ),
        onClick = onClick,
        shape = RoundedCornerShape(size = 30.dp)
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                textAlign = TextAlign.Center
            )
        )
    }
}