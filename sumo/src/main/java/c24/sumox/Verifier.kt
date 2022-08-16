package c24.sumox

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class Verifier(
    var pattern: Regex ,
    private val sampleCount: Int
) {
    var hasVerificationStarted: Boolean = false

    // first Match which has to be verified
    private var recognizedMatch: String? = null

    // how often verifcation was hit in a row. Stop when verifcation count == sampleCount
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
        Log.e("Verifier: ", "Result Regex: $result")
        Log.e("Verifier: ", "recognized Text : $recognizedText")
        // if true set recognizedMatch
        if (result) {
            recognizedMatch = setRecognizedMatch(recognizedText)
            hasVerificationStarted = true
            verificationSamplesFlow.value = 1
            // loop until SampleCount matches in a row are hit
            Log.e("Verifier: ", "Match string: $recognizedMatch")
        } else {
            if(verificationSamplesFlow.value >= 1){

                verificationSamplesFlow.value = 0
            }
        }
    }

    fun confirmVerification(nextRecognizedText: String) {

        var nextRecognizedMatch = setRecognizedMatch(nextRecognizedText)
        if (nextRecognizedMatch.equals(recognizedMatch)) {
            verificationCount++
            Log.e("Verifier: ","verifcation count -> $verificationCount")
            if (verificationCount >= sampleCount) {
                mutableVerifiedTextFlow.value = recognizedMatch
                fullyVerified = true
                mutableIsFullyVerifiedFlow.value = true
            }
        } else {
            resetVerfication()
        }
        verificationSamplesFlow.value = verificationCount
    }

    private fun resetVerfication() {
        verificationCount = 0
        hasVerificationStarted = false
        mutableIsFullyVerifiedFlow.value = false
        fullyVerified = false
    }


    private fun setRecognizedMatch(recognizedText: String): String? {
        var foundString = pattern.find(recognizedText)
        val recognizedMatch = foundString?.value
        Log.e("Verifier: ", "matched String is: ${foundString?.value}")
        return recognizedMatch
    }
}