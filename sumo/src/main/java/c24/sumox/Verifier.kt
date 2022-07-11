package c24.sumox

import android.util.Log

class Verifier(
    private val pattern: Regex,
    private val sampleCount: Int
) {
    var hasVerificationStarted: Boolean = false

    // first Match which has to be verified
    private var recognizedMatch: String? = null

    // how often verifcation was hit in a row. Stop when verifcation count == sampleCount
    private var verificationCount: Int = 0

    // true when sampleCount amount was hit in a row
    private var fullyVerified: Boolean = false

    fun startVerification(recognizedText: String) {
        // check if pattern was found
        var result = pattern.containsMatchIn(recognizedText)
        Log.e("Verifier: ", "Result Regex: $result")
        Log.e("Verifier: ", "recognized Text : $recognizedText")
        // if true set recognizedMatch
        if (result) {
            recognizedMatch = setRecognizedMatch(recognizedText)
            hasVerificationStarted = true
            // loop until SampleCount matches in a row are hit
            Log.e("Verifier: ", "Match string: $recognizedMatch")
        }
    }

    fun confirmVerification(nextRecognizedText: String) {
        //verify first match with current
        //todo: get livedata from analyzer
        var nextRecognizedMatch = setRecognizedMatch(nextRecognizedText)
        if (nextRecognizedMatch.equals(recognizedMatch)) {
            verificationCount++
            Log.e("Verifier: ","verifcation count -> $verificationCount")
            // verification is done when verifcation equals given sample count
            if (verificationCount >= sampleCount) fullyVerified = true
        } else {
            resetVerfication()
        }
    }

    private fun resetVerfication() {
        verificationCount = 0
        hasVerificationStarted = false
    }


    private fun setRecognizedMatch(recognizedText: String): String? {
        var foundString = pattern.find(recognizedText)
        val recognizedMatch = foundString?.value
        Log.e("Verifier: ", "matched String is: ${foundString?.value}")
        return recognizedMatch
    }
}