package c24.sumo_example

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import c24.sumox.BorderView
import c24.sumox.CameraView
import c24.sumox.Scan

class ExampleScans() {

    val scanWithDefaultSettings = Scan.Builder()


    val scanExampleReceipt = Scan.Builder().setBorderView(
        BorderView(
            height = 600.dp,
            width = 400.dp,
            color = Color.Blue
        )
    )

}