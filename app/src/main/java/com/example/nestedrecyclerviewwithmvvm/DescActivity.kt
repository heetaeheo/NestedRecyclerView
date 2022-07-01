package com.example.nestedrecyclerviewwithmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.nestedrecyclerviewwithmvvm.databinding.ActivityDescBinding

class DescActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDescBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<LocationData>("loc_data")


        binding.subtextViewName.text = data?.locationName


        var address = ""

        data?.address?.let {
            address += it +", "
        }
        data?.city?.let {
            address += it+", "
        }
        data?.state?.let {
            address += it+", "
        }
        data?.zip?.let {
            address += it+", "
        }
        data?.country?.let {
            address += it
        }
        binding.subtextViewAddress.text = address


        Glide.with(binding.subimageview).load(data?.url).into(binding.subimageview)
    }

}