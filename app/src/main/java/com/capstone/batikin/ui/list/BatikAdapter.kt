package com.capstone.batikin.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.capstone.batikin.R
import com.capstone.batikin.databinding.BatikItemBinding
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.listDummy
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

//            Glide.with(holder.itemView.context)
//                .load(data.photoUrl)
//                .into(binding.ivBatik)
//
//            binding.tvItemTitle.text = data.title
//            binding.tvItemPrice.text = data.price.toString()
//
//            if (data.isFavourite) {
//                binding.ivItemHeart.setImageResource(R.drawable.baseline_favorite_24)
//            } else {
//                binding.ivItemHeart.setImageResource(R.drawable.baseline_favorite_border_24)
//            }

            holder.binding.cvItemRow.setContent {
                MaterialTheme {
                    BatikItem(
                        title = data.title,
                        photoUrl = data.photoUrl,
                        price = data.price.toString(),
                        desc = data.desc,
                        isFavourite = data.isFavourite
                    )
                }
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

@Composable
fun BatikItem(
    title: String,
    photoUrl: String,
    price: String,
    desc: String,
    isFavourite: Boolean
) {

    ConstraintLayout {
        val (titleText, photo, priceText, descText, heartIcon, starIcon, ratingText) = createRefs()

        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(120.dp)
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                }
        )

        Spacer(modifier = Modifier.padding(top = 5.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(photo.bottom, margin = 5.dp)
                start.linkTo(parent.start, margin = 10.dp)
            }
        )

        Text(
            text = "Rp. $price",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(priceText) {
                top.linkTo(titleText.bottom, margin = 5.dp)
                start.linkTo(parent.start, margin = 10.dp)
            }
        )

        Icon(
            painter = painterResource(
                if (isFavourite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24),
            contentDescription = null,
            modifier = Modifier.constrainAs(heartIcon) {
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(titleText.bottom)
            },
            tint = Color.Red
        )

        Text(
            text = desc,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.constrainAs(descText) {
                start.linkTo(parent.start, 10.dp)
                top.linkTo(priceText.bottom, 10.dp)
            }
        )

        Icon(
            painter = painterResource(id = R.drawable.baseline_star_24),
            contentDescription = null,
            modifier = Modifier.constrainAs(starIcon) {
                top.linkTo(descText.bottom, 10.dp)
                start.linkTo(parent.start, margin = 10.dp)
                bottom.linkTo(parent.bottom)
            }
                .clickable {

                },
            tint = Color.Yellow
        )

        Text(
            text = "4.5",
            fontSize = 12.sp,
            modifier = Modifier.constrainAs(ratingText) {
                start.linkTo(starIcon.end, 5.dp)
                top.linkTo(descText.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun BatikItemPreview() {
    MaterialTheme {
        BatikItem(
            title = listDummy[0].title,
            photoUrl = listDummy[0].photoUrl,
            price = listDummy[0].price.toString(),
            desc = listDummy[0].desc,
            isFavourite = listDummy[0].isFavourite,
        )
    }
}