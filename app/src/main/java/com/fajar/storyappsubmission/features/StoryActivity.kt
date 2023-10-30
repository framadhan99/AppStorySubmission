package com.fajar.storyappsubmission.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fajar.storyappsubmission.databinding.ActivityStoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    fun showAppBar(s: String, toolbarTittlePosition: Boolean) {
        binding.appbar.visibility = View.VISIBLE
        binding.toolbar.apply {
            visibility = View.VISIBLE
            title = s
            isTitleCentered = toolbarTittlePosition
        }
    }

    fun hideAppBar() {
        binding.appbar.visibility = View.GONE
        binding.toolbar.apply {
            visibility = View.VISIBLE
            title = null
        }
    }

    fun showLoading() {
        binding.apply {
            container.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
            pbGeneral.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        binding.apply {
            container.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
            pbGeneral.visibility = View.GONE
        }
    }
}