package com.udokur.cinetrove.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udokur.cinetrove.databinding.ItemHomeRecyclerViewBinding
import com.udokur.cinetrove.model.MovieItem
import com.udokur.cinetrove.util.loadImage

interface MovieClickListener {
    fun onMovieClicked(movieId: Int?)
}

class MovieAdapter(private val movieList: List<MovieItem?>, private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHomeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size / 2 // İkişerli olarak sıralandığı için liste uzunluğunu yarıya düşürüyoruz.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie1 = movieList[position * 2] // Listenin çift indexli elemanı
        val movie2 = movieList[position * 2 + 1] // Listenin çift indexli elemanının bir sonraki elemanı

        holder.binding.textViewTitle1.text = movie1?.title
        holder.binding.textViewVote1.text = movie1?.voteAverage.toString()
        holder.binding.imageViewMovie1.loadImage(movie1?.posterPath)

        // İlk öğe için tıklama işlevi
        holder.binding.root.setOnClickListener {
            movieClickListener.onMovieClicked(movieId = movie1?.id)
        }

        // İkinci öğe için tıklama işlevi
        holder.binding.root2.setOnClickListener {
            movieClickListener.onMovieClicked(movieId = movie2?.id)
        }

        holder.binding.textViewTitle2.text = movie2?.title
        holder.binding.textViewVote2.text = movie2?.voteAverage.toString()
        holder.binding.imageViewMovie2.loadImage(movie2?.posterPath)
    }
}
