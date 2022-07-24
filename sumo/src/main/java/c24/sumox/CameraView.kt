package c24.sumox

import android.content.ContentValues
import android.content.Context
import android.util.Log
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

    var cameraExecutor: ExecutorService = initExecutor()
    var view: @Composable () -> Unit = {}

    fun setCustomView(customView: @Composable () -> Unit = {}) {
        this.view = customView
    }

    private fun setupAnalyzerPrerequisites(context: Context, lifecycleOwner: LifecycleOwner,preview: Preview){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, Analyzer()
                )

            } catch (exc: Exception) {
                Log.e(ContentValues.TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))

    }
    @Composable
    fun StartCamera() {
       val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        //TODO test if this works
        val preview = Preview.Builder().build()
        val previewView = remember { PreviewView(context) }
        setupAnalyzerPrerequisites(context, lifecycleOwner,preview)

        preview.setSurfaceProvider(previewView.surfaceProvider)

        //TODO: Here custom view erlauebbn
        Box(modifier = Modifier.fillMaxSize()
            ) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
            //ToDO: customize UI here
            view()
        }

    }

    //TODO: eigenen ImageAnalyzer erstellen lassen
    private fun Analyzer(): ImageAnalysis {
        val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor, imageAnalyzer
                )
            }
        return imageAnalyzer
    }


    private fun initExecutor(): ExecutorService {
        cameraExecutor = Executors.newSingleThreadExecutor()
        return cameraExecutor
    }
}

