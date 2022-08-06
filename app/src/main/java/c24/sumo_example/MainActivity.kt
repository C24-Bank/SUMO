package c24.sumo_example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import c24.sumo_example.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}

