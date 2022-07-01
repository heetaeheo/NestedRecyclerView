package com.example.nestedrecyclerviewwithmvvm

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nestedrecyclerviewwithmvvm.databinding.RecyclerListBinding

class RecyclerViewAdapter(val clickListener: onRecyclerItemClick) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var locListData = mutableListOf<LocationData>()


    class MyViewHolder(private val binding: RecyclerListBinding, val clickListener : onRecyclerItemClick) :
        RecyclerView.ViewHolder(binding.root) {

        val imageView = binding.imageView
        val textTitle = binding.textTitle
        val textContent = binding.textContent
        val textChild = binding.textSecond

        fun bind(data: LocationData) {
            textTitle.text = data.locationName
            var address = ""

            data.address?.let {
                address = it
            }
            data.city?.let {
                address = it
            }
            data.state?.let {
                address = it
            }
            data.zip?.let {
                address = it
            }
            data.country?.let {
                address = it
            }

            textContent.text = address
            Glide.with(imageView)
                .load(data.url).into(imageView)

            if (data.childLocations != null && data.childLocations.size > 0) {
                textChild.visibility = VISIBLE
                binding.childRecyclerView.visibility = VISIBLE
                binding.childRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val decoration =
                        DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                    addItemDecoration(decoration)

                    val recyclerViewAdapter = RecyclerViewAdapter(clickListener)
                    recyclerViewAdapter.locListData = data.childLocations.toMutableList()
                    adapter = recyclerViewAdapter


                }
                textChild.text = "Child Location " + data.childLocations.size
            } else {
                textChild.visibility = GONE
                binding.childRecyclerView.visibility = GONE
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(locListData[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemClickListener(locListData[position])
        }
    }

    override fun getItemCount(): Int {
        return locListData.size
    }

    interface onRecyclerItemClick {
        fun onItemClickListener(data: LocationData)
    }

}