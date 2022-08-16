package c24.sumox

import android.graphics.*
import android.media.Image
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.ByteArrayOutputStream

internal class ImageAnalyzer : ImageAnalysis.Analyzer {
    private val invalidTime = -1L
    private var previousAnalyzed = invalidTime
    var scanFrequencyDelay = 1000
    private var recognizer: TextRecognizer
    var  verifier: Verifier
    var sampleCount = 2
    var pattern = Regex("""\d{10}""")

    private val mutableRecognizedTextFlow = MutableStateFlow<String?>(null)
    val recognizedTextFlow = mutableRecognizedTextFlow.asSharedFlow()

    init {
        verifier = Verifier(
            pattern = pattern,
            sampleCount = sampleCount
        )

        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }


    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        if (image.image == null) return

        val now = SystemClock.uptimeMillis();
        val bitmapProxy = image.image?.toBitmap()
        if (skipFrame(now)) {
            image.close();
            return
        }
        if (bitmapProxy != null) {
            processImageWithMLKit(bitmapProxy)
        }
        previousAnalyzed = now;
        image.close()

    }

    private fun skipFrame(now: Long) =
        previousAnalyzed != invalidTime && (now - previousAnalyzed < scanFrequencyDelay)


    private fun Image.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer
        val vuBuffer = planes[2].buffer

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun processImageWithMLKit(bitmap: Bitmap) {

        val image = InputImage.fromBitmap(bitmap, 0)
        recognizer.process(image).addOnSuccessListener { visionText ->
            setAllRecognizedText(visionText.text)
            startVerifier(visionText.text)

        }.addOnFailureListener { e ->
            Log.e("Sumo/Analyzer: ", "ML Kit failed to process bitmap")
        }
    }

    private fun setAllRecognizedText(text: String){
        mutableRecognizedTextFlow.value = text
    }

    private fun startVerifier(visionText: String) {
        if (verifier.hasVerificationStarted) {
            verifier.confirmVerification(visionText)
        } else {
            verifier.startVerification(visionText)
        }
    }

}
