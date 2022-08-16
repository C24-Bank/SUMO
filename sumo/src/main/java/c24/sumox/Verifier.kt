package c24.sumox

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class Verifier(
    var pattern: Regex ,
    private val sampleCount: Int
) {
    var hasVerificationStarted: Boolean = false

    // first Match which has to be verified
    private var recognizedMatch: String? = null

    // how often verifcation was hit in a row. Fully verified when verifcation count == sampleCount
    private var verificationCount: Int = 0

    // true when sampleCount amount was hit in a row
    private var fullyVerified: Boolean = false

    private val verificationSamplesFlow = MutableStateFlow<Int>(-1)
    val samplesFlow = verificationSamplesFlow.asSharedFlow()

    private val mutableVerifiedTextFlow = MutableStateFlow<String?>(null)
    val verifiedTextFlow = mutableVerifiedTextFlow.asSharedFlow()

    private val mutableIsFullyVerifiedFlow = MutableStateFlow<Boolean>(false)
    val isFullyVerifiedFlow = mutableIsFullyVerifiedFlow.asSharedFlow()

    fun startVerification(recognizedText: String) {
        // check if pattern was found
        var result = pattern.containsMatchIn(recognizedText)
        // if true set recognizedMatch
        if (result) {
            recognizedMatch = setRecognizedMatch(recognizedText)
            hasVerificationStarted = true
            verificationSamplesFlow.value = 1
            // loop until SampleCount matches in a row are hit
        } else {
            if(verificationSamplesFlow.value >= 1){

                verificationSamplesFlow.value = 0
            }
        }
    }

    fun confirmVerification(nextRecognizedText: String) {

        val nextRecognizedMatch = setRecognizedMatch(nextRecognizedText)
        if (nextRecognizedMatch.equals(recognizedMatch)) {
            verificationCount++
            if (verificationCount >= sampleCount) {
                mutableVerifiedTextFlow.value = recognizedMatch
                fullyVerified = true
                mutableIsFullyVerifiedFlow.value = true
            }
        } else {
            resetVerification()
        }
        verificationSamplesFlow.value = verificationCount
    }

    private fun resetVerification() {
        verificationCount = 0
        hasVerificationStarted = false
        mutableIsFullyVerifiedFlow.value = false
        fullyVerified = false
    }


    private fun setRecognizedMatch(recognizedText: String): String? {
        val foundString = pattern.find(recognizedText)
        return foundString?.value
    }
}