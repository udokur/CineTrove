package com.udokur.cinetrove.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udokur.cinetrove.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeEvents()
        setupScrollListener() // Kaydırma olayını dinlemek için

        return binding.root
    }

    private fun observeEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            binding.textViewHomeError.text = error
            binding.textViewHomeError.isVisible = true
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }
        viewModel.movieList.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.textViewHomeError.text = "Film Bulunamadı :("
                binding.textViewHomeError.isVisible = true
            } else {
                if (!::movieAdapter.isInitialized) {
                    movieAdapter = MovieAdapter(list, object : MovieClickListener {
                        override fun onMovieClicked(movieId: Int?) {
                            movieId?.let {
                                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                                findNavController().navigate(action)
                            }
                        }
                    })
                    binding.homeRecyclerView.adapter = movieAdapter
                } else {
                    // Mevcut adapter varsa, yeni filmleri ekleyin
                    movieAdapter.addMovies(list)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupScrollListener() {
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading.value!! && visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0) {
                    // Sayfa sonuna gelindi, daha fazla veri yükle
                    viewModel.loadMoreMovies()
                }
            }
        })
    }
}