package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import ramble.sokol.tgm_inverse.theme.color_text_price_rating
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_bb_rating
import tgminverse.composeapp.generated.resources.icon_first
import tgminverse.composeapp.generated.resources.icon_second
import tgminverse.composeapp.generated.resources.icon_third
import tgminverse.composeapp.generated.resources.mont_bold
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun PhotoOtherRatingLider(
    grade: Int,
    name: String,
    price: String
){

    Column(
        modifier = Modifier.width(120.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.height(70.dp).width(70.dp),
            contentAlignment = Alignment.TopEnd
        ) {

            Surface(
                modifier = Modifier.size(70.dp),
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(Res.drawable.test_photo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Image(
                modifier = Modifier.height(23.dp).width(23.dp),
                painter = if (grade == 2) painterResource(Res.drawable.icon_second) else painterResource(Res.drawable.icon_third),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.padding(top = 7.dp))

        Text(
            text = name,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.mont_regular)),
                fontWeight = FontWeight(700),
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.padding(top = 4.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = price,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(700),
                    color = color_text_price_rating,
                )
            )

            Spacer(modifier = Modifier.padding(horizontal = 2.dp))

            Image(
                modifier = Modifier
                    .width(18.dp),
                painter = painterResource(Res.drawable.icon_bb_rating),
                contentDescription = "icon_bb_rating"
            )

        }

    }

}