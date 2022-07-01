package com.example.nestedrecyclerviewwithmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nestedrecyclerviewwithmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.onRecyclerItemClick {

    private lateinit var binding:ActivityMainBinding
    lateinit var recyclerAdapter : RecyclerViewAdapter
    lateinit var viewModel:MainActvityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initRecyclerView()
        loadData()
    }

    private fun initRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerAdapter

        }
    }


    private fun loadData(){
      viewModel =  ViewModelProvider(this).get(MainActvityViewModel::class.java)
        viewModel.getLoclistObservable().observe(this,Observer<LocationList>{
            recyclerAdapter.locListData = it.data.toMutableList()
            recyclerAdapter.notifyDataSetChanged()
        })
        viewModel.loadData(this@MainActivity)
    }

    override fun onItemClickListener(data: LocationData) {
        val intent = Intent(this@MainActivity, DescActivity::class.java)
        intent.putExtra("loc_data",data)

        startActivity(intent)
    }
}