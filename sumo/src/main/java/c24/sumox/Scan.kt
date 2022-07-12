package c24.sumox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Scan(
    var builder: Builder
) {


    var borderView: @Composable () -> Unit = {}
    var cameraView: @Composable () -> Unit = {}
    var titleView: @Composable () -> Unit = {}
    var descriptionView: @Composable () -> Unit = {}
    private var composedView: @Composable () -> Unit = {}

    private lateinit var scanUIFragment: Fragment
    private var borderViewGloballyPositionedModifier: LayoutCoordinates? = null

    init {
        scanUIFragment = ScanUIFragment(this.builder)
        //TODO init functions

        borderView = {
            builder.getBorderView().Rahmen()

        }
        GlobalScope.launch {
            builder.getBorderView().coordinatesFlow.collectLatest {
                if (it != null) {

                borderViewGloballyPositionedModifier = it
                    borderViewGloballyPositionedModifier?.let {
                        builder.setImageAnalyzerCropParameters(
                            borderViewGloballyPositionedModifier?.positionInRoot()?.x!!.toInt(),
                            borderViewGloballyPositionedModifier?.positionInRoot()?.y!!.toInt(),
                            borderViewGloballyPositionedModifier?.size?.width!!.toInt(),
                            borderViewGloballyPositionedModifier?.size?.height!!.toInt()
                        )
                    }
                }
            }
        }

        descriptionView = { builder.getDescriptionView().createView() }
        titleView = { builder.getTitleView().createView() }
        composedView = { stitchView() }
//        stitchView()
        cameraView = {
            builder.getCameraView().setCustomView(composedView)
            builder.getCameraView().StartCamera()
        }
    }

    class Builder {

        private var imageAnalyzer = ImageAnalyzer()

        private var borderView = BorderView()
        private var cameraView = CameraView(imageAnalyzer = imageAnalyzer)
        private var descriptionView = DescriptionView()
        private var titleView = TitleView()

        /* Setters */
        fun setBorderView(borderView: BorderView) = apply { this.borderView = borderView }
        fun setDescriptionView(descriptionView: DescriptionView) =
            apply { this.descriptionView = descriptionView }
        fun setTitleView(titleView: TitleView) = apply { this.titleView = titleView }
        fun setImageAnalyzerCropParameters(x: Int, y: Int, width: Int, height: Int) {
            imageAnalyzer.setCropParameters(
                x = x, y = y, width = width, height = height
            )
        }

        private fun setCameraView(cameraView: CameraView) = apply { this.cameraView = cameraView }

        /* Getters */
        fun getBorderView() = borderView
        fun getCameraView() = cameraView
        fun getDescriptionView() = descriptionView
        fun getTitleView() = titleView

        fun build() = Scan(this).scanUIFragment
        fun buildScan() = Scan(this)


    }

    @Composable
    fun stitchView() {
        //Todo: if custom view is given dont do this

//        val scanView =
        Box(modifier = Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                titleView()
                borderView()
////                Spacer(modifier = Modifier.weight(5f))
                descriptionView()
            }
        }

//        scanUIFragment = ScanUIFragment(scanView)
//        scanUIFragment
    }


}



