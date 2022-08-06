package c24.sumox

import android.provider.Settings
import android.util.Log
import androidx.camera.core.ImageAnalysis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ScanLogic(
    private val imageAnalyzer: ImageAnalyzer,
    private val verifier: Verifier,
) {

    private val mutableCollectedTextFlow = MutableStateFlow<String?>(null)
    val collectedTextFlow = mutableCollectedTextFlow.asSharedFlow()

    private val mutableConfirmedTextFlow = MutableStateFlow<String?>(null)
    val confirmedTextFlow = mutableConfirmedTextFlow.asSharedFlow()

    private val mutableIsFullyVerifiedFlow = MutableStateFlow<Boolean>(false)
    val isFullyVerifiedFlow = mutableIsFullyVerifiedFlow.asSharedFlow()

    init {
//        GlobalScope.launch {
//            verifier.verifiedTextFlow.collectLatest {
//                mutableConfirmedTextFlow.value = it
//                Log.e("ExampleApp","scanlogic verified: $it")
//
//            }
//        }
    }

    fun collectRecognizedText(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            imageAnalyzer.recognizedTextFlow.collectLatest {
                mutableCollectedTextFlow.value = it
//                Log.e("ExampleApp","scanlogic text: $it")

            }
        }
    }

    fun collectVerifiedText(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            verifier.verifiedTextFlow.collectLatest {
                mutableConfirmedTextFlow.value = it
//                Log.e("ExampleApp", "scanlogic verified: $it")
            }
        }
//        coroutineScope.launch {
////            feedbackHelper.startFeedback()
//        }
    }

    fun collectIsFullyVerifiedStatus(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            verifier.isFullyVerifiedFlow.collectLatest {
                mutableIsFullyVerifiedFlow.value = it
//                Log.e("ExampleApp", "scanlogic verified bool: $it")
            }
        }
    }
}