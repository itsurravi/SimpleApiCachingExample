package com.ravisharma.simpleapicachingexample.features.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravisharma.simpleapicachingexample.R
import com.ravisharma.simpleapicachingexample.databinding.ActivityRestaurantBinding
import com.ravisharma.simpleapicachingexample.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RestaurantActivity : AppCompatActivity() {

    private val viewModel: RestaurantViewModel by viewModels()
    private var _binding: ActivityRestaurantBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantAdapter = RestaurantAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = restaurantAdapter
                layoutManager = LinearLayoutManager(this@RestaurantActivity)
            }

            lifecycleScope.launchWhenStarted {
                viewModel.restaurants.collect { result ->
                    restaurantAdapter.submitList(result.data)

                    progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                    textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                    textViewError.text = result.error?.localizedMessage
                }
            }
//            viewModel.restaurants.observe(this@RestaurantActivity) { result ->
//                restaurantAdapter.submitList(result.data)
//
//                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
//                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
//                textViewError.text = result.error?.localizedMessage
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}