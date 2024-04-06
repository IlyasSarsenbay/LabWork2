package com.example.dogs.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogs.model.DogApi
import com.example.dogs.model.DogListState
import com.example.dogs.network.DogService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DogListViewModel(private val service: DogService) : ViewModel() {
    class Provider(private val service: DogService) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DogListViewModel::class.java)) {
                return DogListViewModel(service) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    private val _dogListState = MutableLiveData<DogListState>()
    val dogListState: LiveData<DogListState> get() = _dogListState

    private var originalDogApiList: List<DogApi> = emptyList()
    private val filteredDogApiList = MutableLiveData<List<DogApi>>()

    init {
        filteredDogApiList.value = originalDogApiList
    }

    fun observeFilteredDogList(): LiveData<List<DogApi>> {
        return filteredDogApiList
    }

    fun filterDogs(query: String?) {
        val filteredList = originalDogApiList.filter { dogApi ->
            dogApi.name.contains(query.orEmpty(), ignoreCase = true)
        }
        filteredDogApiList.value = filteredList
    }

    fun fetchDogList() {
        _dogListState.value = DogListState.Loading(true)

        service.fetchDogList().enqueue(object : Callback<List<DogApi>> {
            override fun onResponse(
                call: Call<List<DogApi>>,
                response: Response<List<DogApi>>
            ) {
                if (response.isSuccessful) {
                    val dogApiResponse = response.body()
                    if (dogApiResponse != null) {
                        originalDogApiList = dogApiResponse
                        _dogListState.value = DogListState.Success(originalDogApiList.map { DogApi.toDog(it) })
                    } else {
                        _dogListState.value = DogListState.Error()
                    }
                } else {
                    _dogListState.value = DogListState.Error()
                }
                _dogListState.value = DogListState.Loading(false)
            }

            override fun onFailure(call: Call<List<DogApi>>, t: Throwable) {
                _dogListState.value = DogListState.Error()
                _dogListState.value = DogListState.Loading(false)
            }
        })
    }
}
