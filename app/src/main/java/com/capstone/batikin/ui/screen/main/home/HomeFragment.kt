package com.capstone.batikin.ui.screen.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
<<<<<<< HEAD
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.batikin.R
import com.capstone.batikin.databinding.FragmentHomeBinding
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.list.BatikAdapter
import com.capstone.batikin.ui.screen.login.Login
import com.capstone.batikin.ui.ui.theme.BatikInTheme
=======
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.batikin.databinding.FragmentHomeBinding
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.list.BatikAdapterHome
>>>>>>> 59d1d780a9b327e47ea8a7829b8a10806673bac7

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
<<<<<<< HEAD

        (activity as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.cvHome.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.cvHome.setContent {
            BatikInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.wrapContentHeight(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeApp(Modifier.wrapContentHeight())
                }
            }
        }

        binding.rvHome.layoutManager = GridLayoutManager(context, 2)

        binding.rvHome.adapter = BatikAdapter(listDummy)
=======

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Recommendation list

        binding.rvRecommendation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommendation.adapter = BatikAdapterHome(listDummy)

        // Discover more list

        binding.rvDiscover.layoutManager = GridLayoutManager(context, 2)

        binding.rvDiscover.adapter = BatikAdapterHome(listDummy)

>>>>>>> 59d1d780a9b327e47ea8a7829b8a10806673bac7

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun HomeApp(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {HomeTopBar()}
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

@Composable
fun HomeTopBar() {
    var query by remember { mutableStateOf("") }
    Row(modifier = Modifier
        .background(colorResource(id = R.color.orange_light))
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        SearchBar(
            query = query,
            onChange = { query = it}
        )
        Spacer(modifier = Modifier.width(40.dp))
        Cart(modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun Cart(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Icon(
            Icons.Rounded.ShoppingCart,
            contentDescription = "Shopping Cart",
            tint = Color.White,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
        Text(
            text = "1",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier
                .offset(10.dp, 10.dp)
                .background(shape = CircleShape, color = Color.Red)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun SearchBar(query: String, onChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null
            )
        },
        label = {Text(text = "Cari batik disini!")},
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
    )
}
@Preview(showBackground = true)
@Composable
fun HomeAppPreview() {
    BatikInTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeApp()
        }
    }
}
