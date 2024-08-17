package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.mont_regular

@Composable
fun PlaylistItem(
    name: String,
    author: String
){

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {



        Text(
            text = name,
            style = TextStyle(
                fontSize = 22.sp,
                lineHeight = 22.sp,
                fontFamily = FontFamily(Font(Res.font.mont_regular)),
                fontWeight = FontWeight(800),
                color = Color.White,
            )
        )

    }

}