package c24.sumox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Scan(
    var builder: Builder
) {


    var borderView: @Composable () -> Unit = {}
    var cameraView: @Composable () -> Unit = {}
    var titleView: @Composable () -> Unit = {}
    var descriptionView: @Composable () -> Unit = {}
    private var composedView: @Composable () -> Unit = {}

    class Builder {
        private var borderView = BorderView()
        private var cameraView = CameraView()
        private var descriptionView = DescriptionView()
        private var titleView = TitleView()
        /* Setters */
        fun setBorderView(borderView: BorderView) = apply { this.borderView = borderView }
        fun setDescriptionView(descriptionView: DescriptionView) =
            apply { this.descriptionView = descriptionView }

        private fun setCameraView(cameraView: CameraView) = apply { this.cameraView = cameraView }

        /* Getters */
        fun getBorderView() = borderView
        fun getCameraView() = cameraView
        fun getDescriptionView() = descriptionView
        fun getTitleView() = titleView
        fun build() = Scan(this)
    }


    init {

        borderView = { builder.getBorderView().Rahmen() }
        descriptionView = { builder.getDescriptionView().createView() }
        titleView = { builder.getTitleView().createView()}
        composedView = { stitchView() }
//        stitchView()
        cameraView = {
            builder.getCameraView().setCustomView(composedView)
            builder.getCameraView().StartCamera()
        }

    }

    @Composable
    private fun stitchView() {
        //Todo: if custom view is given dont do this
        Box(modifier = Modifier.fillMaxSize()) {
            Column() {
                titleView()
                borderView()
                descriptionView()
            }
        }


    }
}



