package com.example.dogs.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogs.databinding.DogItemBinding
import com.example.dogs.model.Dog


class DogAdapter() : ListAdapter<Dog, DogAdapter.ViewHolder>(DogDiffUtil()) {
    private var dogListFull: List<Dog> = emptyList()

    init {
        dogListFull = currentList.toList()
    }

    fun filterList(filteredList: List<Dog>) {
        dogListFull = filteredList
        submitList(dogListFull)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DogItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: DogItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(dog: Dog) {

            with(binding) {
                Glide.with(root.context)
                    .load(dog.image)
                    .into(dogImage)
                dogName.text = "Name: " + dog.name
               dogDescription.text = "Grooming: " + dog.grooming.toString() + '\n' +
                      "Coat length: " + dog.fur.toString() + '\n' + "Good with children: " + dog.behavior.toString() +
                       '\n' + "Max weight of male: " + dog.weightMale + '\n' + "Max weight of female: " + dog.weightFemale
            }
        }

    }
}