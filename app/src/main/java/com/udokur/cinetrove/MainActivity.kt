package com.udokur.cinetrove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.udokur.cinetrove.databinding.ActivityMainBinding
import com.udokur.cinetrove.model.MovieItem
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private fun searchMovies(query: String) {
        // API çağrısı yerine örnek sonuç kullan
        val exampleMovieList: List<MovieItem?> = getExampleMovieList(query)

        // Örnekleme sonuçları kullanarak RecyclerView'i güncelle
        updateRecyclerView(exampleMovieList)
    }

    private fun updateRecyclerView(movieList: List<MovieItem?>) {
        // RecyclerView'i güncellemek için movieList'i kullan
        // MovieAdapter'ın içindeki veri listesini güncelle
       // movieAdapter.updateData(movieList)
    }


    private fun getExampleMovieList(query: String): List<MovieItem?> {
        // Örnek bir sonuç listesi döndürmek için kullanılabilir
        // Burada gerçek bir API çağrısı yerine kullanıcı tarafından girilen sorguyu filtrele
        return emptyList() // Örnek olarak boş bir liste döndür
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length >= 3) { // Minimum 3 karakter kontrolü
                    // API çağrısı yapmak için özel bir fonksiyonu çağır
                    searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Arama çubuğuna her karakter girişinde burada filtreleme işlemi yap
                // ve RecyclerView'i güncelle
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}