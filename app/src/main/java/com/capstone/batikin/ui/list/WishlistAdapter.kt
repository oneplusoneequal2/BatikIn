package com.capstone.batikin.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.painterResource
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.batikin.R
import com.capstone.batikin.databinding.WishlistItemBinding
import com.capstone.batikin.model.Batik
import com.capstone.batikin.ui.screen.detail.DetailActivity

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
                    binding.ivHeartWishlist.setOnClickListener {
                        binding.ivHeartWishlist.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                } else {
                    binding.ivHeartWishlist.setImageResource(R.drawable.baseline_favorite_border_24)
                    binding.ivHeartWishlist.setOnClickListener {
                        binding.ivHeartWishlist.setImageResource(R.drawable.baseline_favorite_24)
                    }
                }
                holder.itemView.setOnClickListener {
                    val context = holder.itemView.context
                    val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                    // DataDummy
                    // nanti dihapus
                    intent.putExtra("id_extra", this.id)
                    intent.putExtra("photo_extra", this.photoUrl)
                    intent.putExtra("title_extra", this.title)
                    intent.putExtra("price_extra", this.price)
                    intent.putExtra("desc_extra", this.desc)
                    intent.putExtra("favourite_extra", this.isFavourite)

                    context.startActivity(intent)
                }
            }
        }
    }

    inner class ViewHolder(val binding: WishlistItemBinding): RecyclerView.ViewHolder(binding.root)
}