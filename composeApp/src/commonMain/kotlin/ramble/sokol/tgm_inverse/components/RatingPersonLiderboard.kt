package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ModalDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.web.dom.Col
import ramble.sokol.tgm_inverse.theme.background_wallet_item
import ramble.sokol.tgm_inverse.theme.color_text_price_rating
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_arrow_to_game
import tgminverse.composeapp.generated.resources.icon_bb_rating
import tgminverse.composeapp.generated.resources.liderboard
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun RatingPersonLiderboard(
    number: String,
    price: String,
    name: String
){

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = number,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                fontWeight = FontWeight(400),
                color = Color.White,
                textAlign = TextAlign.Start,
            )
        )

        Spacer(modifier = Modifier.padding(horizontal = 11.dp))

        Surface(
            modifier = Modifier.size(55.dp).background(background_wallet_item),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(Res.drawable.test_photo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = name,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(700),
                    color = Color.White,
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
                        lineHeight = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = color_text_price_rating,
                    )
                )

                Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                Image(
                    modifier = Modifier
                        .height(8.5.dp)
                        .width(18.dp),
                    painter = painterResource(Res.drawable.icon_bb_rating),
                    contentDescription = "icon_bb_rating"
                )

            }

        }

    }

}