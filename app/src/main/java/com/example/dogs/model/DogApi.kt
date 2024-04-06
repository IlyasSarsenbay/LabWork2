package com.example.dogs.model

import com.google.gson.annotations.SerializedName

data class DogApi(
    @SerializedName("image_link") val imageLink: String,
    val name: String,
    val grooming: String,
    @SerializedName("coat_length") val coatLength: String,
    @SerializedName("good_with_children") val goodWithChildren: String,
    @SerializedName("max_weight_male") val maxWeightMale: String,
    @SerializedName("max_weight_female") val maxWeightFemale: String
){
    companion object {
        fun toDog(dogApi: DogApi) = Dog(
            image = dogApi.imageLink,
            name = dogApi.name,
            grooming = dogApi.grooming.toInt(),
            fur = dogApi.coatLength.toInt(),
            behavior = dogApi.goodWithChildren.toInt(),
            weightMale = dogApi.maxWeightMale,
            weightFemale = dogApi.maxWeightFemale,
        )
    }
}