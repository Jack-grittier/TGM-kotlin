package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.theme.background_line_item
import ramble.sokol.tgm_inverse.theme.background_line_item_white
import ramble.sokol.tgm_inverse.theme.background_wallet_item
import ramble.sokol.tgm_inverse.theme.color_active
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.active
import tgminverse.composeapp.generated.resources.icon_active
import tgminverse.composeapp.generated.resources.icon_go_perform
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.perform
import tgminverse.composeapp.generated.resources.tasks_navbar
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun TasksPerform(
    tasks: TasksMeEntity,
) {

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(background_wallet_item),
        contentAlignment = Alignment.TopCenter
    ){

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){

//                Image(
//                    modifier = Modifier
//                        .width(36.dp)
//                        .height(36.dp),
//                    painter = painterResource(Res.drawable.test_photo),
//                    contentDescription = "iconActive"
//                )

                KamelImage(
                    resource = asyncPainterResource(data = tasks.task.iconURL),
                    contentDescription = "description"
                )

                Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                Text(
                    text = tasks.task.description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    )
                )

            }

            Spacer(modifier = Modifier.padding(top = 18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .clip(RoundedCornerShape(27.dp))
                    .background(background_line_item_white)
            )

            Spacer(modifier = Modifier.padding(top = 18.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = "+${tasks.task.reward} BB",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                )

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){

                    Text(
                        text = stringResource(Res.string.perform),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                            fontWeight = FontWeight(700),
                            color = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        contentAlignment = Alignment.Center
                    ){

                        Image(
                            painter = painterResource(Res.drawable.icon_go_perform),
                            contentDescription = "iconGoPerform"
                        )

                    }

                }

            }

        }

    }

}