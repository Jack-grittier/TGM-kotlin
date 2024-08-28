package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import ramble.sokol.tgm_inverse.theme.background_price_music
import ramble.sokol.tgm_inverse.theme.background_text_author
import ramble.sokol.tgm_inverse.theme.color_text_price_rating
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_bb_music
import tgminverse.composeapp.generated.resources.icon_bb_rating
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun PlaylistItem(
    name: String,
    author: String,
    price: String,
){

    Column(
        modifier = Modifier.width(136.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .height(140.dp)
                .width(136.dp)
                .clip(RoundedCornerShape(22.dp))
        ){

            Image(
                painter = painterResource(Res.drawable.test_photo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 16.dp, topStart = 22.dp))
                    .background(background_price_music)
            ){

                Row(modifier = Modifier
                        .padding(start = 14.dp, end = 18.dp, top = 6.dp, bottom = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){

                    Image(
                        modifier = Modifier
                            .width(21.dp),
                        painter = painterResource(Res.drawable.icon_bb_music),
                        contentDescription = "icon_bb_rating"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = price,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                            fontWeight = FontWeight(700),
                            color = Color.White,
                        )
                    )

                }

            }

        }

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        Box(
            modifier = Modifier.height(35.dp)
                .padding(end = 3.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = name,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(700),
                    color = Color.White,
                ),
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        Text(
            text = author,
            style = TextStyle(
                fontSize = 10.sp,
                lineHeight = 13.sp,
                fontFamily = FontFamily(Font(Res.font.mont_regular)),
                fontWeight = FontWeight(700),
                color = background_text_author,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

    }

}