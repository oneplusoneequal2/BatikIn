package com.capstone.batikin.ui.screen.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.batikin.R
import com.capstone.batikin.databinding.FragmentHomeBinding
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.list.BatikAdapterHome
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.capstone.batikin.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Recommendation list

//        binding.rvRecommendation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvRecommendation.adapter = BatikAdapterHome(listDummy)

        // Discover more list

//        binding.rvDiscover.layoutManager = GridLayoutManager(context, 2)
//
//        binding.rvDiscover.adapter = BatikAdapterHome(listDummy)

        binding.cvHome.setContent {
            BatikInTheme {
                HomeApp(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
//                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                )
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}