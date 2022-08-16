package c24.sumo_example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import c24.sumox.*

class ExampleScans() {

    val codeRegex = Regex("""Code\s\d{10}""")
    val scanWithDefaultSettings = Scan.Builder().setSampleCount(2).setScanFrequencyDelay(500)
        .setPattern(codeRegex)


    val scanWithCustomView = Scan.Builder().setCustomView {
        Canvas(
            modifier = Modifier.padding(10.dp).fillMaxHeight().fillMaxWidth()
        ) {

            drawRoundRect(
                color = Color.Magenta,
                style = Stroke(
                    width = 2.dp.toPx(),
                ),
                cornerRadius = CornerRadius(x = 0f, y = 0f)
            )
        }
        Text(
            text = "Hier könnte Ihre CustomView stehen.",
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
        )

    }.setPattern(codeRegex)

    val scanExampleReceipt = Scan.Builder()
        .setTitleView(
            TitleView(isVisible = false)
        )
        .setBorderView(
            BorderView(
                height = 500.dp,
                width = 300.dp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )
        )
        .setDescriptionView(
            DescriptionView(
                titleTextAlign = TextAlign.Center,
                cornerRadius = CornerRadius(0f, 0f)
            )
        ).setPattern(codeRegex)


    val scanExampleTR1 = Scan.Builder().setPattern(codeRegex).setScanFrequencyDelay(0).setSampleCount(0)
    val scanExampleTR2 = Scan.Builder().setPattern(codeRegex).setScanFrequencyDelay(0).setSampleCount(2)
    val scanExampleTR3 = Scan.Builder().setPattern(codeRegex).setScanFrequencyDelay(500).setSampleCount(2)


}