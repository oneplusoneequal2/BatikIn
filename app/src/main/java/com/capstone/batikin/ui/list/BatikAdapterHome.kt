package com.capstone.batikin.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.batikin.databinding.ItemBatikHomeBinding
import com.capstone.batikin.model.Batik
import com.capstone.batikin.ui.screen.detail.DetailActivity

class BatikAdapterHome(private val dataList: ArrayList<Batik>) : RecyclerView.Adapter<BatikAdapterHome.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBatikHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = dataList[position].id
        val photoUrl = dataList[position].photoUrl
        val title = dataList[position].title
        val rating = dataList[position].rating
        val price = dataList[position].price
        val desc = dataList[position].price
        val isFavourite = dataList[position].isFavourite

        with(holder) {
            Glide.with(itemView.context)
                .load(photoUrl)
                .into(binding.ivItem)

            binding.tvItem.text = title
            binding.tvPriceItem.text = price.toString()
            binding.tvRatingItem.text = rating.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.apply {
                    putExtra("id_extra", id)
                    putExtra("photo_extra", photoUrl)
                    putExtra("title_extra", title)
                    putExtra("price_extra", price)
                    putExtra("desc_extra", desc)
                    putExtra("favourite_extra", isFavourite)
                }
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(val binding: ItemBatikHomeBinding) : RecyclerView.ViewHolder(binding.root)
}