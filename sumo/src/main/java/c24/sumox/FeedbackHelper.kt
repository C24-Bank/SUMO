package c24.sumox

import android.util.Log
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class FeedbackHelper(imageAnalyzer: ImageAnalyzer, borderView: BorderView) {


    init {
        GlobalScope.launch {

            imageAnalyzer.verifier?.samplesFlow?.collectLatest {
                Log.e("Feedbackhelper:" ," collected: $it")
                when (it) {
                    0 -> borderView.changeColor(Color.Red)
                    1 -> borderView.changeColor(Color.Yellow)
                    2 -> borderView.changeColor(Color.Green)
                }
            }

        }

    }
}