package c24.sumox

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraView(
    private var imageAnalyzer: ImageAnalysis.Analyzer
) {

    var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    var view: @Composable () -> Unit = {}

    fun setCustomView(customView: @Composable () -> Unit = {}) {
        this.view = customView
    }

    private fun setupAnalyzerPrerequisites(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        preview: Preview
    ) {
        // get instance of the camera provider to attach listener
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Unbind all Use Cases and bind the custom scan analyzer
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, analyzer()
            )

        }, ContextCompat.getMainExecutor(context))

    }

    @Composable
    fun StartCamera() {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        val preview = Preview.Builder().build()
        val previewView = remember { PreviewView(context) }
        setupAnalyzerPrerequisites(context, lifecycleOwner, preview)

        preview.setSurfaceProvider(previewView.surfaceProvider)

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
            view()
        }

    }

    private fun analyzer(): ImageAnalysis {
        val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor, imageAnalyzer
                )
            }
        return imageAnalyzer
    }
}

