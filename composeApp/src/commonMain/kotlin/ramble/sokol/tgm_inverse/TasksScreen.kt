package ramble.sokol.tgm_inverse

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import ramble.sokol.tgm_inverse.components.ProgressBarTasks
import ramble.sokol.tgm_inverse.components.TasksDone
import ramble.sokol.tgm_inverse.components.TasksGetPayment
import ramble.sokol.tgm_inverse.components.TasksPerform
import ramble.sokol.tgm_inverse.components.TasksPerformProgress
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.model.data.TasksMeEntityNew
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_screens
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar

class TasksScreen(
    val modifier: Modifier,
    val userEntityCreate: UserEntityCreate
) : Screen {

    private lateinit var apiRepo: ApiRepository
    private lateinit var listTasks: MutableState<List<TasksMeEntity>>
    private lateinit var listTasksNotCom: MutableState<List<TasksMeEntity>>
    private lateinit var listTasksPen: MutableState<List<TasksMeEntity>>
    private lateinit var listTasksComWithout: MutableState<List<TasksMeEntity>>
    private lateinit var listTasksCom: MutableState<List<TasksMeEntity>>
    private lateinit var body: MutableState<TasksMeEntityNew?>

    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()
        listTasks = remember {
            mutableStateOf(listOf())
        }
        listTasksCom = remember {
            mutableStateOf(listOf())
        }
        listTasksComWithout = remember {
            mutableStateOf(listOf())
        }
        listTasksNotCom = remember {
            mutableStateOf(listOf())
        }
        listTasksPen = remember {
            mutableStateOf(listOf())
        }

        body = remember {
            mutableStateOf(null )
        }

        scope.launch {
            getTasks()
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(background_screens)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ){

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.tasks_navbar),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                    fontWeight = FontWeight(400),
                    color = Color.White,
                    textAlign = TextAlign.Start,
                )
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            if (listTasks.value.size == 0) {
                ProgressBarTasks()
            } else {

//                Text(
//                    text = "task: ${body.value}",
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        lineHeight = 21.sp,
//                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
//                        fontWeight = FontWeight(600),
//                        color = Color.White,
//                    )
//                )

                LazyColumn() {
                    items(listTasksNotCom.value) { tasks: TasksMeEntity ->
                        TasksPerform(tasks)
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }

                    items(listTasksPen.value) { tasks: TasksMeEntity ->
                        TasksPerformProgress(
                            name = tasks.task.description,
                            photoUrl = tasks.task.iconURL,
                            reward = tasks.task.reward.toString()
                        )
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }

                    items(listTasksComWithout.value) { tasks: TasksMeEntity ->
                        TasksGetPayment(tasks)
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }

                    items(listTasksCom.value) { tasks: TasksMeEntity ->
                        TasksDone(tasks)
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }

                }
           }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

        }

    }

    private suspend fun getTasks() {

        body.value = apiRepo.getTasksMe(userEntityCreate.initData)
        listTasksNotCom.value = body.value!!.NotCompleted!!
        listTasksPen.value = body.value!!.Pending!!
        listTasksComWithout.value = body.value!!.CompletedWithoutReceivingReward!!
        listTasksCom.value = body.value!!.Completed!!
    }

}