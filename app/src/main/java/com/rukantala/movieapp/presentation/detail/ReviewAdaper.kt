package com.rukantala.movieapp.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rukantala.movieapp.databinding.ReviewitemBinding
import com.rukantala.movieapp.domain.entity.ReviewEntity

class ReviewAdapter :
    ListAdapter<ReviewEntity, ReviewAdapter.ViewHolder>(
        DiffCallback
    ) {
    inner class ViewHolder(
        private var binding: ReviewitemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewEntity) {
            binding.item = data
            binding.apply {
            }
            binding.executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<ReviewEntity>() {
        override fun areItemsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ReviewitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val pelatihanItem = getItem(position)
        holder.bind(pelatihanItem)
    }
}