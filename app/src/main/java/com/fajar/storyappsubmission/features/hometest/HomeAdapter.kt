package com.fajar.storyappsubmission.features.hometest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajar.storyappsubmission.R

class HomeAdapter: PagingDataAdapter<StoryResponseItems, HomeAdapter.ViewModel>(DIFF_CALLBACK){
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewModel(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_item_photo)
        val userName: TextView = view.findViewById(R.id.tv_item_name)
        val detail: TextView = view.findViewById(R.id.tv_item_description)
        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onItemClick(getItem(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_story, parent, false)
        return ViewModel(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewModel, position: Int) {
        val storyData = getItem(position)
        Glide.with(holder.itemView.context)
            .load(storyData?.photoUrl)
            .into(holder.imageView)
        holder.userName.text = storyData?.name
        holder.detail.text = storyData?.description
    }
    interface OnItemClickListener {
        fun onItemClick(story: StoryResponseItems?)
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