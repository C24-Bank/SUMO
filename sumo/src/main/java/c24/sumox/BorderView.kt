package c24.sumox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding


class BorderView(
    private var width: Dp = 150.dp,
    private var height: Dp = 150.dp,
    private var color: Color = Color.Red
) {

@Composable
fun Rahmen() {
    Canvas(
        modifier = Modifier
            .padding(20.dp)

    ) {
        drawRoundRect(
            size = Size(width.toPx(), height.toPx()),
            color = color,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            ),
            cornerRadius = CornerRadius(x = 20f, y = 20f)
        )
    }
}
}

