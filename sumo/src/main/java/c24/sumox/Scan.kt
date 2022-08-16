package c24.sumox

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment

class Scan(
    var builder: Builder
) {

    private var borderView: @Composable () -> Unit = {}
    var cameraView: @Composable () -> Unit = {}
    private var titleView: @Composable () -> Unit = {}
    private var descriptionView: @Composable () -> Unit = {}
    private var composedView: @Composable () -> Unit = {}


    private var customView: (@Composable () -> Unit)? = null

    private var scanUIFragment: Fragment

    init {
        scanUIFragment = ScanUIFragment(this.builder)

        customView = builder.getCustomView()
        if (customView == null) {
            borderView = {
                builder.getBorderView().Border()

            }

            descriptionView = { builder.getDescriptionView().CreateView() }
            titleView = { builder.getTitleView().CreateView() }
        }

        composedView = { StitchView() }

        cameraView = {
            builder.getCameraView().setCustomView(composedView)
            builder.getCameraView().StartCamera()
        }


    }

    class Builder {
        // -------- Logic ---------
        private var imageAnalyzer = ImageAnalyzer()
        private var scanLogic = ScanLogic(imageAnalyzer)

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
        fun setPattern(pattern: Regex) = apply { this.imageAnalyzer.verifier.pattern = pattern }

        fun setScanFrequencyDelay(delay: Int) =
            apply { this.imageAnalyzer.scanFrequencyDelay = delay }

        fun setSampleCount(count: Int) = apply { this.imageAnalyzer.sampleCount = count }

        fun setCustomView(customView: @Composable () -> Unit = {}) = apply {
            this.customView = customView
        }

        private fun setCameraView(cameraView: CameraView) = apply { this.cameraView = cameraView }

        /* Getters */
        internal fun getScanLogic() = scanLogic

        fun getBorderView() = borderView
        fun getCameraView() = cameraView
        fun getDescriptionView() = descriptionView
        fun getTitleView() = titleView
        fun getCustomView() = customView
        fun getSampleCount() = imageAnalyzer.sampleCount

        fun build() = Scan(this).scanUIFragment
        fun createScanObject() = Scan(this)


    }

    @Composable
    fun StitchView() {
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



