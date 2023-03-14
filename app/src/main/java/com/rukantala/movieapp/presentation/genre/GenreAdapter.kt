package com.rukantala.movieapp.presentation.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rukantala.movieapp.databinding.GenreitemBinding
import com.rukantala.movieapp.domain.entity.GenreEntity
import com.rukantala.movieapp.presentation.home.MovieAdapter

class GenreAdapter (val onClickListener: OnclickListener) :
    ListAdapter<GenreEntity, GenreAdapter.ViewHolder>(
        DiffCallback
    ) {
    inner class ViewHolder(
        private var binding: GenreitemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenreEntity) {
            binding.apply {
                tvName.text = data.name
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
        ViewHolder(GenreitemBinding.inflate(LayoutInflater.from(parent.context),parent, false))


    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        val pelatihanItem = getItem(position)
        holder.bind(pelatihanItem)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pelatihanItem)
        }
    }

    class OnclickListener(
        val clickListener: (entity: GenreEntity) ->
        Unit
    ) {
        fun onClick(entity: GenreEntity) = clickListener(entity)
    }
}

