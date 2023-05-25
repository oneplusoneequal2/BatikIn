package com.capstone.batikin.ui.screen.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.material.icons.filled.PowerSettingsNew
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

    val popupShown = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_batikin),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Yellow, CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.nama),
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.dark_mode),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = R.string.dark_mode),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    popupShown.value = true
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.language),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = R.string.language),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    popupShown.value = true
                }
            )
        }

        // Show pop-up
        if (popupShown.value) {
            AlertDialog(
                onDismissRequest = { popupShown.value = false },
                title = {
                    Text(text = "Fitur ini masih dalam pengembangan")
                },
                confirmButton = {
                    TextButton(onClick = { popupShown.value = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = { /* Handle logout button click */ },
            modifier = Modifier.wrapContentWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.log_out),
                    color = Color.White
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







