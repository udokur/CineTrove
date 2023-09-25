package com.udokur.cinetrove.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udokur.cinetrove.R
import com.udokur.cinetrove.model.Results
import com.udokur.cinetrove.util.Constant

class SearchAdapter : ListAdapter<Results, SearchAdapter.MovieViewHolder>(MovieDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_rv, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewMovie: ImageView = itemView.findViewById(R.id.imageViewMovie)
        private val textViewMovieTitle: TextView = itemView.findViewById(R.id.textViewMovieTitle)

        fun bind(movie: Results) {
            textViewMovieTitle.text = movie.title

            // Resmi yükleme (Glide kullanarak)
            val imageUrl = Constant.IMAGE_BASE_URL + movie.posterPath
            Glide.with(itemView)
                .load(imageUrl)
                .into(imageViewMovie)
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }
}
