package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.list_fragment, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        val list: RecyclerView = view.findViewById(R.id.recyclerView)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        var data: List<Results>? = null

        viewModel.getPlaces()

        viewModel.uiState.observe(requireActivity()) {
            when (it) {
                is MyViewModel.UIState.Empty -> Unit
                is MyViewModel.UIState.Result -> {
                    data = it.responseResults
                    if (data!!.isNotEmpty()) {
                        val myAdapter = RecyclerViewAdapter(data!!)
                        list.adapter = myAdapter
                    }
                }
                is MyViewModel.UIState.Processing -> Unit
                is MyViewModel.UIState.ResultRoute -> Unit
                is MyViewModel.UIState.Error -> {
                    Log.e("response error", it.description)
                }
            }
        }
        list.layoutManager = LinearLayoutManager(view.context)

        fab.setOnClickListener {
            val mapFragment = MapFragment()
            mapFragment.setListResults(data!!)
            parentFragmentManager.beginTransaction()
                .add(R.id.container, mapFragment)
                .addToBackStack("mapFragment")
                .commit()
        }
    }

}