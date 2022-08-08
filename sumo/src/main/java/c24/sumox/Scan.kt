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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Scan(
    var builder: Builder
) {

    private var borderView: @Composable () -> Unit = {}
    var cameraView: @Composable () -> Unit = {}
    private var titleView: @Composable () -> Unit = {}
    private var descriptionView: @Composable () -> Unit = {}
    private var composedView: @Composable () -> Unit = {}

    private var feedbackHelper: FeedbackHelper

    private var customView: (@Composable () -> Unit)? = null

    private lateinit var scanUIFragment: Fragment
    private var borderViewGloballyPositionedModifier: LayoutCoordinates? = null

    init {
        scanUIFragment = ScanUIFragment(this.builder)
        //TODO init functions


        customView = builder.getCustomView()
        feedbackHelper = builder.getFeedbackHelper()
        if (customView == null) {
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
        }

        composedView = { stitchView() }
//        stitchView()
        cameraView = {
            builder.getCameraView().setCustomView(composedView)
            builder.getCameraView().StartCamera()
        }


    }

    class Builder {

        private var imageAnalyzer = ImageAnalyzer()

        // -------- Logic ---------
        private var feedbackHelper = FeedbackHelper(imageAnalyzer)
        private var scanLogic = ScanLogic(imageAnalyzer, imageAnalyzer.verifier)

        // --------- User Interface -----------
        private var borderView = BorderView()
        private var cameraView = CameraView(imageAnalyzer = imageAnalyzer)
        private var descriptionView = DescriptionView()
        private var titleView = TitleView()
        private var customView: (@Composable () -> Unit)? = null


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

        fun setAnalysisDelay(delay: Int) = apply { this.imageAnalyzer.analysisDelay = delay }
        fun setSampleCount(count: Int) = apply { this.imageAnalyzer.sampleCount = count }

        fun setCustomView(customView: @Composable () -> Unit = {}) = apply {
            this.customView = customView
        }

        private fun setCameraView(cameraView: CameraView) = apply { this.cameraView = cameraView }

        /* Getters */
        fun getFeedbackHelper() = feedbackHelper
        internal fun getScanLogic() = scanLogic

        fun getBorderView() = borderView
        fun getCameraView() = cameraView
        fun getDescriptionView() = descriptionView
        fun getTitleView() = titleView
        fun getCustomView() = customView


        fun build() = Scan(this).scanUIFragment
        fun createScanObject() = Scan(this)


    }

    @Composable
    fun stitchView() {
        //Todo: if custom view is given dont do this

        Box(modifier = Modifier.fillMaxSize()) {
            if (customView == null) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    titleView()
                    borderView()
                    descriptionView()
                }
            } else {
                customView!!()
            }

        }
    }


}



