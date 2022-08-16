package c24.sumox

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TitleView(
    private var width: Dp? = null,
    private var height: Dp? = null,
    private var title: String = "Scannen Sie hier Ihr Dokument",
    private var modifier: Modifier? = null,
    private var isVisible: Boolean = true,
    private var titleTextAlign: TextAlign? = null
) {

    @Composable
    fun CreateView() {
        if (isVisible) {
            setModifier()
            Text(
                text = title,
                color = Color.White,
                textAlign = titleTextAlign ?: TextAlign.Center,
                modifier = modifier!!,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
        }
    }

    private fun setModifier() {
        if (modifier == null) {
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()

            modifier = when {
                width != null -> modifier!!.width(width!!)
                height != null -> modifier!!.height(height!!)

                else -> modifier!!.fillMaxWidth()
            }
        }
    }
}