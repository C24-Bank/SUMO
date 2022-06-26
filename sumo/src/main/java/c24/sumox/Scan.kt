package c24.sumox

import androidx.compose.runtime.Composable

class Scan(
    var builder : Builder
) {


    var borderView: @Composable () -> Unit = {}
    var cameraView : @Composable () -> Unit = {}

    class Builder {
        private var borderView = BorderView()
        private var cameraView = CameraView()

        /* Setters */
        fun setBorderView(borderView: BorderView) = apply {this.borderView = borderView}
        fun setCameraView(cameraView: CameraView) = apply { this.cameraView = cameraView }

        /* Getters */
        fun getBorderView() = borderView
        fun getCameraView() = cameraView

        fun build() = Scan(this)
    }

    init {
        borderView = { builder.getBorderView().Rahmen() }
        cameraView = { builder.getCameraView().StartCamera() }
    }


}
