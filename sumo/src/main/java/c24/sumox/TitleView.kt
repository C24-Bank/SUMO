package c24.sumox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class TitleView(
    private var width: Dp? = null,
    private var height: Dp? = null,
    private var title: String = "Scannen Sie hier Ihr Dokument",
    private var modifier: Modifier? = null,
) {

    @Composable
    fun createView() {
        setModifier()
        Text(text = title, textAlign = TextAlign.Center, modifier = modifier!!)
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