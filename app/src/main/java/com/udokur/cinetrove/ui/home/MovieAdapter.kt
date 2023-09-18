package com.udokur.cinetrove.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udokur.cinetrove.databinding.ItemHomeRecyclerViewBinding
import com.udokur.cinetrove.model.MovieItem
import com.udokur.cinetrove.util.loadImage


interface MovieClickListener{
    fun onMovieClicked(movieId:Int?)
}

class MovieAdapter(private val movieList: List<MovieItem?>,private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHoldler>() {

    class ViewHoldler(val binding: ItemHomeRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldler {
        return ViewHoldler(
            ItemHomeRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }
    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHoldler, position: Int) {
        val movie = movieList[position]
        holder.binding.textViewTitle.text = movie?.title
        holder.binding.textViewVote.text = movie?.voteAverage.toString()
        holder.binding.imageViewMovie.loadImage(movie?.posterPath)


        holder.binding.root.setOnClickListener{
            movieClickListener.onMovieClicked(movieId = movie?.id)

        }




    }
}