package com.example.finalproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState
    private val repo = MyApplication.getApp().repo
    fun getPlaces() {
        _uiState.value = UIState.Processing
        viewModelScope.launch(Dispatchers.IO) {
            val responseResults = async { repo.getNearbyPlaces() }.await()

            if (responseResults.isSuccessful){
                responseResults.body()?.let {
                    _uiState.postValue(UIState.Result(it.results))
                }
            }
        }
    }
    fun getRoutes(originId: String,destinationId: String,waypoints: String) {
        _uiState.value = UIState.Processing
        viewModelScope.launch(Dispatchers.IO) {
            val responseResults = async { repo.getComplexRoute(originId,destinationId,waypoints) }.await()

            if (responseResults.isSuccessful){
                responseResults.body()?.let {
                    _uiState.postValue(UIState.ResultRoute(it.routes))
                }
            }
        }
    }
    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class Result(val responseResults: List<Results>) : UIState()
        class ResultRoute(val responseResultRoute: List<Routes>) : UIState()
        class Error(val description: String) : UIState()
    }

}
