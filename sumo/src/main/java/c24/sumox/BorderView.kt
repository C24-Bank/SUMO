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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow



class BorderView(
    private var width: Dp? = null,
    private var height: Dp = 200.dp,
    private var color: Color = Color.White,
    private var style: Stroke?= null,
    private var modifier: Modifier? = null,
) {

    private val borderColor = MutableStateFlow(color)

    private fun changeColor(color: Color) {
            borderColor.value = color
    }

    @Composable
    fun Border() {
        val myColor by borderColor.collectAsState()
        setModifier()



        Canvas(
            modifier = modifier!!
        ) {

            drawRoundRect(
                color = myColor,
                style = style ?: Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                ),
                cornerRadius = CornerRadius(x = 20f, y = 20f)
            )
        }


    }

     fun listenToVerificationStatus(verificationStatus: Int,sampleCount: Int){
        when(verificationStatus) {
            -1 -> changeColor(color)
            0 -> changeColor(Color.Red)
            1 -> changeColor(Color.Yellow)
            sampleCount -> changeColor(Color.Green)
        }
    }


    private fun setModifier() {
        if (modifier == null) {
            modifier = Modifier
                .padding(bottom = 200.dp, top = 100.dp, start = 20.dp, end = 20.dp)

        }
        // check if custom width or height is set
        modifier = when {
            width != null -> modifier!!.width(width!!)
            else -> modifier!!
                .fillMaxWidth()
        }
        modifier = modifier!!.height(height)
    }
}

