package com.fajar.storyappsubmission.features.ui.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponseItems
import com.fajar.storyappsubmission.databinding.ItemsStoryBinding
import com.fajar.storyappsubmission.features.ui.activity.DetailActivity
import com.fajar.storyappsubmission.features.utils.Const.DETAIL_ITEM
import com.fajar.storyappsubmission.features.utils.showToast
import dagger.hilt.android.internal.managers.ViewComponentManager

class HomeAdapter :
    PagingDataAdapter<StoryResponseItems, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemsStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemsStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StoryResponseItems) {

            itemView.setOnClickListener {
                val context = itemView.context
                val mContext = if (context is ViewComponentManager.FragmentContextWrapper)
                    context.baseContext
                else
                    context

                val intent = Intent(mContext, DetailActivity::class.java)
                intent.putExtra(DETAIL_ITEM, data)

                showToast(mContext, data.name.toString())
                ContextCompat.startActivity(
                    mContext,
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(mContext as Activity)
                        .toBundle()
                )
            }
            Glide.with(itemView.context)
                .load(data.photoUrl.toString())
                .circleCrop()
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = data.name.toString()
            binding.tvItemDescription.text = data.description.toString()

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponseItems>() {
            override fun areItemsTheSame(oldItem: StoryResponseItems, newItem: StoryResponseItems): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryResponseItems, newItem: StoryResponseItems): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

