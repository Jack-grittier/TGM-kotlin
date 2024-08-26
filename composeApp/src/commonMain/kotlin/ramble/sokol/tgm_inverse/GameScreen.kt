package ramble.sokol.tgm_inverse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.color_hello_peterburg

class GameScreen : Screen {

    @Composable
    override fun Content() {

        Box(
            modifier = Modifier.fillMaxSize()
                .background(background_screens),
            contentAlignment = Alignment.Center
        ){

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                }

                Box(modifier = Modifier.fillMaxHeight().width(1.dp).background(Color.White))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                }

                Box(modifier = Modifier.fillMaxHeight().width(1.dp).background(Color.White))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                }

                Box(modifier = Modifier.fillMaxHeight().width(1.dp).background(Color.White))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                }

            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ){

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(103.dp)
                        .clip(RoundedCornerShape(topStart = 120.dp, topEnd = 120.dp, bottomEnd = 19.dp, bottomStart = 19.dp))
                        .background(color_hello_peterburg)
                        .padding(vertical = 11.dp, horizontal = 20.dp)
                )

            }

        }

    }

}