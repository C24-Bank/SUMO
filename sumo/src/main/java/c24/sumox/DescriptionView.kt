package c24.sumox

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DescriptionView(
    private var width: Dp? = null,
    private var height: Dp? = null,
    private var title: String = "Beschreibung",
    private var description: String = "Legen Sie das Dokument in den angezeigt Rahmen und fokussieren sie die Kamera um den Scan zu absolvieren",
    private var modifier: Modifier? = null,
    private var color: Color = Color.White,
    private var closeable: Boolean = false,
) {

    @Composable
    fun createView() {
        setModifier()

        val cornerRadius = CornerRadius(10f, 10f)

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(0f, 0f),
                        size = Size(100f,100f),
                        ),
                    topLeft = cornerRadius,
                    topRight = cornerRadius,
                )
            )
        }
        Box(modifier = modifier!!) {
            Canvas(modifier!!) {
                drawRect(color = color, topLeft = Offset(0f,50f))
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(x = 50.0f,y= 50.0f)
                )
//                drawPath(path, color)
            }
            Column(
                modifier!!
//                    .background(color)
                    .padding(horizontal = 10.dp)) {
                Text(
                    text = title,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(text = description, color = Color.Black)
            }
        }
    }

    private fun setModifier() {
        if (modifier == null) {
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .fillMaxHeight()

            modifier = when {
                width != null -> modifier!!.width(width!!)
                height != null -> modifier!!.height(height!!)
                else -> modifier!!.fillMaxWidth()
            }
        }
    }
}