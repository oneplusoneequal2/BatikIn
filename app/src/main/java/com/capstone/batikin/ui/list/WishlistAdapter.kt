package com.capstone.batikin.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.painterResource
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.batikin.R
import com.capstone.batikin.databinding.WishlistItemBinding
import com.capstone.batikin.model.Batik

class WishlistAdapter(private val dataList: ArrayList<Batik>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WishlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with (dataList[position]) {
                Glide.with(holder.itemView.context)
                    .load(this.photoUrl)
                    .into(binding.ivBatik)
                binding.tvWishlistTitle.text = this.title
                binding.tvPriceWishlist.text = this.price.toString()
                if (this.isFavourite) {
                    binding.ivHeartWishlist.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.ivHeartWishlist.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }

    inner class ViewHolder(val binding: WishlistItemBinding): RecyclerView.ViewHolder(binding.root)
}