package c24.sumox

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ScanUIFragment(
    private val scanView: Scan.Builder
) : Fragment(R.layout.fragment_scan_ui) {

    private val mutableRecognizedTextFlow = MutableStateFlow<String?>(null)
    var recognizedTextFlow = mutableRecognizedTextFlow.asSharedFlow()

    private val mutableVerifiedTextFlow = MutableStateFlow<String?>(null)
    var verifiedTextFlow = mutableVerifiedTextFlow.asSharedFlow()

    private val mutableIsFullyVerifiedFlow = MutableStateFlow<Boolean>(false)
    val isFullyVerifiedFlow = mutableIsFullyVerifiedFlow.asSharedFlow()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_scan_ui, container, false).apply {
            findViewById<ComposeView>(R.id.composeView).setContent {
                scanView.createScanObject().cameraView()
            }
        }
    }

    fun fetchScanData(lifecycleCoroutineScope: CoroutineScope) {
        val scanLogic = scanView.getScanLogic()
        lifecycleCoroutineScope.launch {
            scanLogic.apply {
                collectRecognizedText(this@launch)
                collectedTextFlow.collectLatest {
                    mutableRecognizedTextFlow.value = it
//                       Log.e("ExampleApp","Scanui fragment text: $it")
                }


            }

        }

        lifecycleCoroutineScope.launch {
            scanLogic.apply {
                collectVerifiedText(this@launch)

                confirmedTextFlow.collectLatest {
                    mutableVerifiedTextFlow.value = it
//                    Log.e("ExampleApp", "Scanui fragment verified code: $it")

                }
            }
        }
//        lifecycleCoroutineScope.launch {
//            scanLogic.apply {
//                collectIsFullyVerifiedStatus(this@launch)
//
//                isFullyVerifiedFlow.collectLatest {
//                    mutableIsFullyVerifiedFlow.value = it
////                    Log.e("ExampleApp", "Scanui fragment verified: $it")
//
//                }
//            }
//        }



    }

}