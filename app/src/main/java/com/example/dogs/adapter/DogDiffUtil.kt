package com.example.dogs.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.dogs.model.Dog

class DogDiffUtil : DiffUtil.ItemCallback<Dog>() {
    override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem == newItem
    }

}