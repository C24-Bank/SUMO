//package c24.sumox
//
//import android.util.Log
//import androidx.compose.ui.graphics.Color
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//class FeedbackHelper internal constructor(
//    private val imageAnalyzer: ImageAnalyzer,
//) {
//
//
////    fun startFeedback(coroutineScope: CoroutineScope) {
////        coroutineScope.launch {
////            imageAnalyzer.verifier.samplesFlow.collectLatest {
////                Log.e("Feedbackhelper:", " collected: $it")
////                verificationSamplesFlow.value = it
////            }
////        }
////    }

//}