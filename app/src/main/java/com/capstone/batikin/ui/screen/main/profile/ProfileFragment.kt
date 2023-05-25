package com.capstone.batikin.ui.screen.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.batikin.R
import com.capstone.batikin.databinding.FragmentProfileBinding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.NonDisposableHandle.parent

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val composeView = ComposeView(requireContext()).apply {
            setContent {
                ProfileScreen()
            }
        }

        binding.composeContainer.addView(composeView)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


@Composable
fun ProfileScreen() {
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_batikin),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "John Doe",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))


        // Settings
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White, shape = RoundedCornerShape(4.dp))
                .background(Color.White)
                .clickable { expanded.value = !expanded.value }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Settings",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Icon(
                        imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray,
                    )
                }

                if (expanded.value) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Dark Mode
                    Column(
                        modifier = Modifier
                        .clickable {
                            selectedOption.value = "Dark Mode"
                            showDialog.value = true
                        }
                        .padding(vertical = 8.dp),
                    ) {
                        Row(

                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Dark Mode",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                        }

                        Text(
                            text = "Aktifkan dark mode",
                            fontSize = 8.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }


//                    Divider(color = Color.LightGray, thickness = 1.dp)

                    // Language
                    Column(
                        modifier = Modifier
                            .clickable {
                                selectedOption.value = "Language"
                                showDialog.value = true
                            }
                            .padding(vertical = 8.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Language",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                        }
                        Text(
                            text = "Ubah bahasa aplikasi",
                            fontSize = 8.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }

                }
            }


        if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = {
                        Text(text = "Fitur ini masih dalam pengembangan")
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text(text = "OK")
                        }
                    }
                )
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        //Log out
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* button click */ }
                .background(Color.White, shape = RoundedCornerShape(2.dp))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log Out",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

    }
}



@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}







