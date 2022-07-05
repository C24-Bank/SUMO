package c24.sumo_example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager


class ScanFragment : Fragment() {

    lateinit var scanModuleFragment : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildScan()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildScan()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.scanFragmentContainer,scanModuleFragment).commit()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    private fun buildScan() {
        val scanFragment1 = ExampleScans().scanWithDefaultSettings.build()
        val scanFragment2 = ExampleScans().scanExampleReceipt.build()
        scanModuleFragment = scanFragment2
    }
}