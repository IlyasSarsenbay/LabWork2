package com.example.dogs.network
import com.example.dogs.model.DogApi
import retrofit2.Call
import retrofit2.http.GET

interface DogService {

    @GET("dogs?name=g")
    fun fetchDogList(): Call<List<DogApi>>
}