package com.example.dogs.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dogs.R
import com.example.dogs.databinding.ActivityMainBinding
import com.example.dogs.fragment.DogListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, DogListFragment.newInstance())
            .commit()

    }
}