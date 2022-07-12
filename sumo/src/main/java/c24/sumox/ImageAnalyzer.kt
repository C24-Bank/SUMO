package c24.sumox

import android.graphics.*
import android.media.Image
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class ImageAnalyzer : ImageAnalysis.Analyzer {
    //TODO: Check if needed here what
    var lastAnalyzedTimestamp = 0L
    private val ANALYSIS_DELAY_MS = 1000
    private val INVALID_TIME = -1L
    private var lastAnalysisTime = INVALID_TIME
    private var recognizer: TextRecognizer
    private var verifier: Verifier? = null
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }
    // set these to crop the bitmap to only the border view captured image
    var cropX : Int? = null
    var cropY : Int? = null
    var cropHeight : Int? = null
    var cropWidth : Int? = null

    init {
        //TODO: regex and sample count set manually. Make verifier nullable
        verifier = Verifier(
            pattern = Regex("""Code\s\d{10}"""),
            sampleCount = 3
        )

        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    fun setCropParameters(x: Int, y: Int, width: Int, height: Int) {
        cropX = x
        cropY = y
        cropHeight = height
        cropWidth = width
    }

    fun cropBitmap(bitmap: Bitmap): Bitmap? {
        var croppedBitmap : Bitmap? = null
        cropX?.let {
            croppedBitmap =
                Bitmap.createBitmap(
                    bitmap,
                    cropX!!,
                    cropY!!,
                    cropWidth!!,
                    cropHeight!!
                )
        }

        return croppedBitmap
    }

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        if (image.image == null) return

        val now = SystemClock.uptimeMillis();
        //TODO: Check format here before
        val bitmapProxy = image.image?.toBitmap()
        // process Image with tesseract
        if (lastAnalysisTime != INVALID_TIME && (now - lastAnalysisTime < ANALYSIS_DELAY_MS)) {
            image.close();
            return
        }
        if (bitmapProxy != null) {
            //crop image before if needed:
            // Todo: make sure not null
//            val croppedBitmap = cropBitmap(bitmapProxy)!!
//            processImageWithMLKit(croppedBitmap)
            processImageWithMLKit(bitmapProxy)
        }
        lastAnalysisTime = now;
        image.close()

    }

    /*
     *IMPORTANT conversion:
     * IF  Format is 256:
     */
    fun ImageProxy.convertImageProxyToBitmap(): Bitmap {
        val buffer = planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /*
     if Format is 35
     */
    fun Image.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer // Y
        val vuBuffer = planes[2].buffer // VU

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

    fun processImageWithMLKit(bitmap: Bitmap) {
        // Use a bitmap as InputImage

        val image = InputImage.fromBitmap(bitmap, 0)
        recognizer.process(image).addOnSuccessListener { visionText ->
            Log.e("OCR: ", "Success process")
            print(visionText.text)
            Log.e("OCR: text ", " ${visionText.text}")
            if (verifier != null) {
                startVerifier(visionText.text)
            }

        }.addOnFailureListener { e ->
            Log.e("OCR: ", "FAILED process")
        }
    }

    private fun startVerifier(visionText: String) {
        if (verifier?.hasVerificationStarted == true) {
            Log.e("Verifier: (analyzer)", "confirm verification")
            verifier?.confirmVerification(visionText)
        } else {
            Log.e("Verifier: (analyzer)", "start verification")
            verifier?.startVerification(visionText)
        }
    }

}
