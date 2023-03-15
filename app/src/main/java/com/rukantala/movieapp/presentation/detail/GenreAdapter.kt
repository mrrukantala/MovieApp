package com.rukantala.movieapp.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rukantala.movieapp.databinding.GenreitemchipBinding
import com.rukantala.movieapp.domain.entity.GenreEntity

class GenreAdapter :
    ListAdapter<GenreEntity, GenreAdapter.ViewHolder>(
        DiffCallback
    ) {
    inner class ViewHolder(
        private var binding: GenreitemchipBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenreEntity) {
            binding.apply {
                chip.text = data.name
            }
            binding.executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<GenreEntity>() {
        override fun areItemsTheSame(oldItem: GenreEntity, newItem: GenreEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GenreEntity, newItem: GenreEntity) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(GenreitemchipBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        val pelatihanItem = getItem(position)
        holder.bind(pelatihanItem)
    }
}