package c24.sumox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width


class BorderView(
    private var width: Dp? = null,
    private var height: Dp = 200.dp,
    private var color: Color = Color.Red,
    private var modifier: Modifier? = null
//TODO: drawRoundRect parameter
) {

    @Composable
    fun Rahmen() {
        //check if there is a custom width
        setModifier()

        modifier?.let {
            Canvas(
                modifier = it
            ) {
                drawRoundRect(
    //            size = Size(width.toPx(), height.toPx()),
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

    private fun setModifier() {
        if (modifier == null) {
            modifier = Modifier
                .padding(bottom= 200.dp,top= 100.dp,start= 20.dp,end= 20.dp)


        }
        modifier = when {
            width != null -> modifier!!.width(width!!)
            else -> modifier!!.fillMaxWidth().height(height)
        }
    }
}

