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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.tgm_inverse.theme.background_color_disconnect
import ramble.sokol.tgm_inverse.theme.text_color_disconnect
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.mont_bold

@Composable
fun ButtonDisconnect(
    text: String,
    onClick: () -> Unit
)
{
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                shape = RoundedCornerShape(size = 12.dp),
                color = background_color_disconnect
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = background_color_disconnect
        ),
        onClick = onClick

    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = text_color_disconnect,
                fontFamily = FontFamily(Font(Res.font.mont_bold)),
                textAlign = TextAlign.Center
            )
        )
    }
}