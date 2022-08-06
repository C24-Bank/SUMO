package c24.sumo_example

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import c24.sumox.ScanUIFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ScanExampleFragment : Fragment() {

    lateinit var scanModuleFragment: ScanUIFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildScan()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        lifecycleScope.launch {
//            scanModuleFragment.fetchScanData(this)
//
//        }
        lifecycleScope.launch {
            scanModuleFragment.fetchRecognizedText(this)

            scanModuleFragment.recognizedTextFlow.collectLatest {
//                Log.e("ExampleApp:", "collected Text : $it")
            }
        }
        lifecycleScope.launch {
            scanModuleFragment.fetchConfirmedText(this)

            scanModuleFragment.verifiedTextFlow.collectLatest {
//                Log.e("ExampleApp:", "verified Text : $it")
            }
        }

        lifecycleScope.launch {
            scanModuleFragment.fetchVerificationStatus(this)
            scanModuleFragment.isFullyVerifiedFlow.collectLatest {
//                Log.e("ExampleApp:", "isfully verified : $it")
            }

        }
        lifecycleScope.launch {
            scanModuleFragment.fetchVerificationCountStatus(this)
            scanModuleFragment.isFullyVerifiedFlow.collectLatest {
//                Log.e("ExampleApp/feedback:", "verifications tatus : $it")
            }

        }


        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.scanFragmentContainer, scanModuleFragment).commit()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    private fun buildScan() {

        val scanFragment1 = ExampleScans().scanWithDefaultSettings
        val scanFragment2 = ExampleScans().scanExampleReceipt
        val scanFragment3 = ExampleScans().scanWithCustomView

        scanModuleFragment = scanFragment1.build() as ScanUIFragment
    }
}