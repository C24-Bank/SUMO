package c24.sumo_example

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import c24.sumox.*

class ExampleScans() {

    val scanWithDefaultSettings = Scan.Builder().setSampleCount(2).setAnalysisDelay(500)


    val scanWithCustomView = Scan.Builder().setCustomView {
        Text(
            text = "Hier könnte Ihre CustomView stehen.",
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
        )
    }

    val scanExampleReceipt = Scan.Builder()
        .setTitleView(
            TitleView(isVisible = false)
        )
        .setBorderView(
            //TODO: X und Y koordinaten als parameter
            //Padding weglassen
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
        )

}