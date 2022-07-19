package c24.sumox

import android.util.Log
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class BorderView(
    private var width: Dp? = null,
    private var height: Dp = 200.dp,
    private var color: Color = Color.Red,
    private var modifier: Modifier? = null
//TODO: drawRoundRect parameter
) {

    private val borderColor = MutableStateFlow(Color.Red)

    private val coordFlow = MutableStateFlow<LayoutCoordinates?>(null)
    val coordinatesFlow = coordFlow.asSharedFlow()


    fun changeColor(color: Color) {
            borderColor.value = color

    }

    @Composable
    fun Rahmen() {
        val myColor by borderColor.collectAsState()
        //check if there is a custom width
        setModifier()



        Canvas(
            modifier = modifier!!.onGloballyPositioned { layoutcoords ->
                Log.e(
                    "coords: ",
                    "Layout coords = height: ${layoutcoords.size.height} width: ${layoutcoords.size.width} "
                )
                Log.e(
                    "coords: ",
                    "Layout coords = x: ${layoutcoords.positionInRoot().x} y: ${layoutcoords.positionInRoot().y} "
                )
                coordFlow.value = layoutcoords
            }
        ) {

            drawRoundRect(
                //            size = Size(width.toPx(), height.toPx()),
                color = myColor,
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                ),
                cornerRadius = CornerRadius(x = 20f, y = 20f)
            )
        }


    }

    private fun setModifier() {
        if (modifier == null) {
            modifier = Modifier
                .padding(bottom = 200.dp, top = 100.dp, start = 20.dp, end = 20.dp)

        }
        modifier = when {
            width != null -> modifier!!.width(width!!)
            else -> modifier!!
                .fillMaxWidth()
        }
        modifier = modifier!!.height(height)
    }
}

