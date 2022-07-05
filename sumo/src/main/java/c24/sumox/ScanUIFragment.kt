package c24.sumox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView


class ScanUIFragment(
    private val scanView : Scan.Builder
) : Fragment(R.layout.fragment_scan_ui) {

//    private lateinit var  scan : Scan.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         scan = scanView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_ui,container,false).apply {
            findViewById<ComposeView>(R.id.composeView).setContent{
                scanView.buildScan().cameraView()
            }
        }
    }

}