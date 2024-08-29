import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.core.InternalFlexibleApi
import com.skydoves.flexible.core.toPx
import kotlinx.coroutines.delay

@OptIn(InternalFlexibleApi::class)
@Composable
fun AnimatedArcProgressBar(progress: Float) {
    val arcThickness = 20.dp.toPx() // Толщина прогресс-бара
    val innerColor = Color.Gray // Внутренний цвет
    val progressColor = Color.White // Цвет заполнения

    Canvas(modifier = Modifier.size(300.dp)) {
        // Рисуем фоновую дугу
        drawArc(
            color = innerColor,
            startAngle = 0f, // Начинаем с 0 градусов
            sweepAngle = 180f, // Заполняем 180 градусов
            useCenter = false,
            style = Stroke(width = arcThickness, cap = StrokeCap.Round) // Круглые углы
        )

        // Рисуем заполненную дугу
        drawArc(
            color = progressColor,
            startAngle = -180f, // Начинаем с 190 градусов
            sweepAngle = -180f * progress, // Заполняем в зависимости от прогресса
            useCenter = false,
            style = Stroke(width = arcThickness, cap = StrokeCap.Round) // Круглые углы
        )
    }
}


@Composable
fun ProgressBarDemo() {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(targetValue = progress)

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(100) // Задержка для анимации
            progress += 0.01f // Увеличиваем прогресс
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter // Центрируем прогресс-бар снизу
    ) {
        AnimatedArcProgressBar(animatedProgress)
    }
}
