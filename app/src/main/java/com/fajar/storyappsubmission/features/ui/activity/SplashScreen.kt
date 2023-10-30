package com.fajar.storyappsubmission.features.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fajar.storyappsubmission.databinding.SplashScreenBinding
import com.fajar.storyappsubmission.features.StoryActivity
import com.fajar.storyappsubmission.features.utils.Const.time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var spBinding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(spBinding.root)
        val intentToHome = Intent(this@SplashScreen, StoryActivity::class.java)
        val handler = Handler(mainLooper)

        lifecycleScope.launch(Dispatchers.Default) {
            handler.postDelayed({
                startActivity(intentToHome)
                finish()
            }, time.toLong())
        }
    }

}