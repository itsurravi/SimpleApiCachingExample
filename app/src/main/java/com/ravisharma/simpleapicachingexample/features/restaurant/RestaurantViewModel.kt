package com.ravisharma.simpleapicachingexample.features.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ravisharma.simpleapicachingexample.data.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    repository: RestaurantRepository
) : ViewModel() {
    val restaurants = repository.getRestaurants()

}