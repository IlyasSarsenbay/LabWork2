package com.example.dogs.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dogs.adapter.DogAdapter
import com.example.dogs.databinding.FragmentDogListBinding
import com.example.dogs.model.DogApi
import com.example.dogs.model.DogListState
import com.example.dogs.network.ApiClient
import com.example.dogs.viewmodel.DogListViewModel
class DogListFragment : Fragment() {
    private var _binding: FragmentDogListBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = DogListFragment()
    }

    private var adapter: DogAdapter? = null

    private val viewModel: DogListViewModel by lazy {
        ViewModelProvider(
            this,
            DogListViewModel.Provider(ApiClient.instance)
        ).get<DogListViewModel>(DogListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DogAdapter()

        binding.dogList.adapter = adapter

        viewModel.fetchDogList()
        viewModel.dogListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DogListState.Success -> {
                    adapter?.submitList(state.items)
                }

                is DogListState.Loading -> {
                    binding.dogList.isVisible = !state.isLoading
                }

                is DogListState.Error -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Some error occurred.")
                        .show()
                }
            }
        }

        binding.searchBar.addTextChangedListener { editable ->
            val searchQuery = editable.toString().trim()
            viewModel.filterDogs(searchQuery)
        }

        viewModel.observeFilteredDogList().observe(viewLifecycleOwner) { filteredDogs ->
            adapter?.submitList(filteredDogs.map { DogApi.toDog(it) })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






