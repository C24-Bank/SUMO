package c24.sumox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ScanLogic(
    private val imageAnalyzer: ImageAnalyzer,

) {

    private val mutableCollectedTextFlow = MutableStateFlow<String?>(null)
    val collectedTextFlow = mutableCollectedTextFlow.asSharedFlow()

    private val mutableConfirmedTextFlow = MutableStateFlow<String?>(null)
    val confirmedTextFlow = mutableConfirmedTextFlow.asSharedFlow()

    private val mutableIsFullyVerifiedFlow = MutableStateFlow<Boolean>(false)
    val isFullyVerifiedFlow = mutableIsFullyVerifiedFlow.asSharedFlow()

    private val verificationSamplesFlow = MutableStateFlow<Int>(-1)
    val samplesFlow = verificationSamplesFlow.asSharedFlow()


    fun collectRecognizedText(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            imageAnalyzer.recognizedTextFlow.collectLatest {
                mutableCollectedTextFlow.value = it

            }
        }
    }

    fun collectVerifiedText(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            imageAnalyzer.verifier.verifiedTextFlow.collectLatest {
                mutableConfirmedTextFlow.value = it
            }
        }
    }

    fun collectIsFullyVerifiedStatus(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            imageAnalyzer.verifier.isFullyVerifiedFlow.collectLatest {
                mutableIsFullyVerifiedFlow.value = it
            }
        }
    }

    fun collectVerificationCount(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            imageAnalyzer.verifier.samplesFlow.collectLatest {
                verificationSamplesFlow.value = it
            }
        }
    }
}