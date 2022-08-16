package c24.sumox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ScanUIFragment(
    private val scanView: Scan.Builder,
) : Fragment(R.layout.fragment_scan_ui) {

    private val scanLogic = scanView.getScanLogic()


    private val mutableRecognizedTextFlow = MutableStateFlow<String?>(null)
    var recognizedTextFlow = mutableRecognizedTextFlow.asSharedFlow()

    private val mutableVerifiedTextFlow = MutableStateFlow<String?>(null)
    var verifiedTextFlow = mutableVerifiedTextFlow.asSharedFlow()

    private val mutableIsFullyVerifiedFlow = MutableStateFlow(false)
    val isFullyVerifiedFlow = mutableIsFullyVerifiedFlow.asSharedFlow()

    private val mutableVerificationCountFlow = MutableStateFlow(0)
    val verificationCount = mutableVerificationCountFlow.asSharedFlow()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_scan_ui, container, false).apply {
            findViewById<ComposeView>(R.id.composeView).setContent {
                scanView.createScanObject().cameraView()
            }
        }
    }

    fun fetchVerificationCountStatus(lifecycleCoroutineScope: CoroutineScope) {
        lifecycleCoroutineScope.launch {
            var isVerified = false
            val sampleCount = scanView.getSampleCount()
            scanLogic.apply {
                collectVerificationCount(this@launch)
                samplesFlow.collectLatest {

                    mutableVerificationCountFlow.value = it

                    if (!isVerified) {
                        scanView.getBorderView()
                            .listenToVerificationStatus(it, sampleCount)
                    }
                    if (it == sampleCount) {
                        isVerified = true
                    }
                }
            }
        }
    }

    fun fetchRecognizedText(lifecycleCoroutineScope: CoroutineScope) {
        lifecycleCoroutineScope.launch {
            scanLogic.apply {
                collectRecognizedText(this@launch)
                collectedTextFlow.collectLatest {
                    mutableRecognizedTextFlow.value = it
                }
            }
        }
    }

    fun fetchConfirmedText(lifecycleCoroutineScope: CoroutineScope) {
        lifecycleCoroutineScope.launch {
            scanLogic.apply {
                collectVerifiedText(this@launch)
                confirmedTextFlow.collectLatest {
                    mutableVerifiedTextFlow.value = it
                }
            }
        }
    }

    fun fetchVerificationStatus(lifecycleCoroutineScope: CoroutineScope) {
        lifecycleCoroutineScope.launch {
            scanLogic.apply {
                collectIsFullyVerifiedStatus(this@launch)
                isFullyVerifiedFlow.collectLatest {
                    mutableIsFullyVerifiedFlow.value = it
                }
            }
        }
    }
}