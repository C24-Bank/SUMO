package c24.sumox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

        Box() {
            Canvas(modifier!!.align(Alignment.BottomStart)) {
                drawRoundRect(
                    color = color
                )
            }
            Column(modifier!!.background(color)) {
                Text(text = title,color = Color.Black)
                Text(text = description,color = Color.Black)
            }
        }
    }

    private fun setModifier() {
        if (modifier == null) {
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()

            modifier = when {
                width != null -> modifier!!.width(width!!)
                height != null -> modifier!!.height(height!!)
                else -> modifier!!.fillMaxWidth()
            }
        }
    }
}