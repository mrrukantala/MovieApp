package com.rukantala.movieapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rukantala.movieapp.databinding.MovieitemBinding
import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.utils.bindImageBerita

class MovieAdapter(val onClickListener: OnclickListener) :
    ListAdapter<MovieEntity, MovieAdapter.ViewHolder>(
        DiffCallback
    ) {
    inner class ViewHolder(
        private var binding: MovieitemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieEntity) {
            binding.item = data

            binding.apply {
                tvTitle.text = data.originalTitle
                tvDateRelase.text = data.releaseDate
                tvRatingValue.text = data.voteAverage
            }
            binding.executePendingBindings()

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(MovieitemBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelatihanItem = getItem(position)
        holder.bind(pelatihanItem)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pelatihanItem)
        }
    }

    class OnclickListener(
        val clickListener: (entity: MovieEntity) ->
        Unit
    ) {
        fun onClick(entity: MovieEntity) = clickListener(entity)
    }
}

