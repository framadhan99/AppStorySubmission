package com.fajar.storyappsubmission.features.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.databinding.ActivityDetailBinding
import com.fajar.storyappsubmission.features.utils.Const.DETAIL_ITEM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityDetailBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val data = intent.getParcelableExtra<Story>(DETAIL_ITEM) as Story
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_ITEM, Story::class.java)
        } else {
            intent.getParcelableExtra<Story>(DETAIL_ITEM)
        }
        binding.apply {
            Glide.with(ivStoryDetail)
                .load(data?.photoUrl)
                .into(ivStoryDetail)
            textStorydescriptionDetail.text = data?.description
            textDateDetail.text = data?.createdAt
        }
    }
}