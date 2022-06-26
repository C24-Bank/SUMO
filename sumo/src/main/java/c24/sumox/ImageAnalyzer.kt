package c24.sumox

import android.graphics.*
import android.media.Image
import android.os.SystemClock
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class ImageAnalyzer : ImageAnalysis.Analyzer {
        //TODO: Check if needed here what
        var lastAnalyzedTimestamp = 0L
        private val ANALYSIS_DELAY_MS = 1000
        private val INVALID_TIME = -1L
        private var lastAnalysisTime = INVALID_TIME

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
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
                //TODO: Process Image here
//                processImageWithMLKit(bitmapProxy)
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
    }
