package com.manikandan.pexels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.manikandan.pexels.adapters.PexelsAdapter
import com.manikandan.pexels.util.NetworkResult
import com.manikandan.pexels.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { PexelsAdapter() }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setUpRecyclerView()
        requestApiData("Popular")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pexels_menu, menu)

        val search = menu.findItem(R.id.search_menu)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled =true
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener{
            requestApiData("popular")
            return@setOnCloseListener false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null){
            requestApiData(query)
        }
       return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }



    private fun requestApiData(searchQuery: String){
        mainViewModel.getPexelImages(provideQueries(searchQuery))
        mainViewModel.pexelsResponse.observe(this, {response ->
            Log.d("Response", response.data?.perPage.toString())
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {
                        adapter.setNewData(it)
                    }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun provideQueries(searchQuery: String): HashMap<String, String>{
        val queries:HashMap<String, String> = HashMap()
        queries["query"] = searchQuery
        queries["per_page"] = "60"
        return queries
    }

    private fun setUpRecyclerView(){
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,GridLayoutManager.VERTICAL)
    }


}