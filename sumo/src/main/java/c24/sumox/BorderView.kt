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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import kotlinx.coroutines.GlobalScope
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


    var coordinates: LayoutCoordinates? = null
    private val coordFlow = MutableStateFlow<LayoutCoordinates?>(null)
    val coordinatesFlow = coordFlow.asSharedFlow()



    @Composable
    fun Rahmen() {

            //check if there is a custom width
            setModifier()

            modifier?.let {
                // set the position here to crop the bitmap later via scan class
                it.onGloballyPositioned { coords ->
                    coordinates = coords
                }

                Canvas(
                    modifier = it.onGloballyPositioned { layoutcoords ->
                        Log.e(
                            "coords: ",
                            "Layout coords = height: ${layoutcoords.size.height} width: ${layoutcoords.size.width} "
                        )
                        Log.e(
                            "coords: ",
                            "Layout coords = height: ${layoutcoords.positionInRoot().x} width: ${layoutcoords.positionInRoot().y} "
                        )
                        coordFlow.value = layoutcoords
                        coordinates = layoutcoords
                    }
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
                .padding(bottom = 200.dp, top = 100.dp, start = 20.dp, end = 20.dp)


        }
        modifier = when {
            width != null -> modifier!!.width(width!!)
            else -> modifier!!
                .fillMaxWidth()
                .height(height)
        }
    }
}

