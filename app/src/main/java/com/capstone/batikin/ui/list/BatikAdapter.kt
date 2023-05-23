package com.capstone.batikin.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.batikin.R
import com.capstone.batikin.databinding.BatikItemBinding
import com.capstone.batikin.model.Batik
import com.capstone.batikin.ui.screen.detail.DetailActivity

class BatikAdapter(private val dataList: ArrayList<Batik>) : RecyclerView.Adapter<BatikAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BatikItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        with(holder) {

            Glide.with(holder.itemView.context)
                .load(data.photoUrl)
                .into(binding.ivBatik)

            binding.tvItemTitle.text = data.title
            binding.tvItemPrice.text = data.price.toString()

            if (data.isFavourite) {
                binding.ivItemHeart.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.ivItemHeart.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                // DataDummy
                // nanti dihapus

                intent.apply {
                    putExtra("id_extra", data.id)
                    putExtra("photo_extra", data.photoUrl)
                    putExtra("title_extra", data.title)
                    putExtra("price_extra", data.price)
                    putExtra("desc_extra", data.desc)
                    putExtra("favourite_extra", data.isFavourite)
                }

                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(val binding: BatikItemBinding): RecyclerView.ViewHolder(binding.root)
}