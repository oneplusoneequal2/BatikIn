package com.capstone.batikin.ui.screen.main.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.batikin.databinding.FragmentWishlistBinding
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.list.BatikAdapter

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        binding.rvWishlist.layoutManager = GridLayoutManager(context, 2)

        binding.rvWishlist.adapter = BatikAdapter(listDummy)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}