package c24.sumo_example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import c24.sumo_example.ui.theme.SUMOTheme
import c24.sumox.BorderView
import c24.sumox.CameraView
import c24.sumox.Scan

class MainActivity : ComponentActivity() {
    var navController : NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SUMOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //TODO check if you navigate with non compose
                    initNavHost()
                }
            }
        }
    }

    @Composable
    fun initNavHost() {
        navController = rememberNavController()
        val scanSumo = ExampleScans().scanWithDefaultSettings.build()
        val scanSumo2 = ExampleScans().scanExampleReceipt.build()

        NavHost(navController = navController!!, startDestination = "exampleStartScreen") {
            composable("exampleStartScreen") { ExampleScreen(navController!!) }
            composable("cameraViewScreen") { scanSumo2.cameraView() }

        }
    }
}



@Composable
fun ExampleScreen(navController: NavHostController) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")
            navController.navigate("cameraViewScreen")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }
    val context = LocalContext.current

    Button(
        onClick = {
            // Check permission
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) -> {
                    launcher.launch(Manifest.permission.CAMERA)

                    // Some works that require permission
                    Log.d("ExampleScreen", "Code requires permission")
                }
                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.CAMERA)
                }
            }
        }
    ) {
        Text(text = "Check and Request Permission")
    }
}

//@Composable
//fun Rahmen(height: Dp, width: Dp) {
//    Canvas(
//        modifier = Modifier
//            .padding(20.dp)
//    ) {
//        drawRoundRect(
//            size = Size(width.toPx(), height.toPx()),
//            color = Color.Red,
//            style = Stroke(
//                width = 2.dp.toPx(),
//                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
//            ),
//            cornerRadius = CornerRadius(x = 20f, y = 20f)
//
//        )
//    }
//}
//
//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SUMOTheme {
//        Rahmen(height = 200.dp, width = 300.dp)
//    }
//}