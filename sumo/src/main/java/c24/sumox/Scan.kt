package c24.sumox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment

class Scan(
    var builder: Builder
) {


    var borderView: @Composable () -> Unit = {}
    var cameraView: @Composable () -> Unit = {}
    var titleView: @Composable () -> Unit = {}
    var descriptionView: @Composable () -> Unit = {}
    private var composedView: @Composable () -> Unit = {}
    private lateinit var scanUIFragment: Fragment


    init {
        scanUIFragment = ScanUIFragment(this.builder)
        borderView = { builder.getBorderView().Rahmen() }
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
        private var borderView = BorderView()
        private var cameraView = CameraView()
        private var descriptionView = DescriptionView()
        private var titleView = TitleView()

        /* Setters */
        fun setBorderView(borderView: BorderView) = apply { this.borderView = borderView }
        fun setDescriptionView(descriptionView: DescriptionView) =
            apply { this.descriptionView = descriptionView }
        fun setTitleView(titleView: TitleView) = apply { this.titleView = titleView }

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



