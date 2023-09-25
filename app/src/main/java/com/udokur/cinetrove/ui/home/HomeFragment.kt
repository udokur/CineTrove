package com.udokur.cinetrove.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udokur.cinetrove.R
import com.udokur.cinetrove.databinding.FragmentHomeBinding




class HomeFragment : Fragment(), MovieClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        observeEvents()
        setupSearchView()

        setupScrollListener()

        return binding.root
    }

    private var lastVisibleItemPosition = 0

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
                binding.textViewHomeError.text = ""
                binding.textViewHomeError.isVisible = true
            } else {
                movieAdapter = MovieAdapter(list, this)
                binding.homeRecyclerView.adapter = movieAdapter
                binding.homeRecyclerView.scrollToPosition(lastVisibleItemPosition)
            }
        }
    }

    private fun setupSearchView() {
        activity?.findViewById<EditText>(R.id.editTextSearch)
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val query = s.toString()
                    if (query.length >= 3) {
                        viewModel.searchMovies(query)
                    } else {
                        viewModel.clearMovieList()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
    }

    private fun setupScrollListener() {
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                lastVisibleItemPosition = firstVisibleItem

                if (!viewModel.isLoading.value!! && visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0) {
                    viewModel.loadMoreMovies()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClicked(movieId: Int?) {

        movieId?.let {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
    }}