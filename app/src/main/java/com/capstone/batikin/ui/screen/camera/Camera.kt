package com.capstone.batikin.ui.screen.camera

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.capstone.batikin.R
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import java.io.File
import java.io.FileOutputStream


private fun rotateFile(file: File, isBackCamera: Boolean = false) {
    val matrix = Matrix()
    val bitmap = BitmapFactory.decodeFile(file.path)
    val rotation = if (isBackCamera) 90f else -90f
    matrix.postRotate(rotation)
    if (!isBackCamera) {
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
    }
    val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
}

@Composable
fun CameraApp() {
    var image by remember { mutableStateOf<Uri?>(Uri.EMPTY)}

    val context = LocalContext.current

    val getGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            image= it
        }

    val launcherCameraX =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == 200) {
                val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("picture", File::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.data?.getSerializableExtra("picture")
                } as? File
                val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
                myFile?.let { file ->
                    rotateFile(file, isBackCamera)
                    image = Uri.fromFile(file)
                }
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(colorResource(id = R.color.pinkish_white))
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        )
        if (image?.equals(Uri.EMPTY) == true) {
            Text(
                text = "Choose The Batik You Want to Find The Pattern",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Button(
                    onClick = {
                        getGallery.launch("image/*")
                    }) {
                    Icon(
                        imageVector = Icons.Default.BrowseGallery,
                        contentDescription = stringResource(R.string.gallery)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.gallery),
                        style = MaterialTheme.typography.h6,
                    )
                }
                Button(
                    onClick = {
                        val intent = Intent(context, CameraActivity::class.java)
                        launcherCameraX.launch(intent)
                    }) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = stringResource(R.string.Camera)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.Camera),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.AdfScanner,
                    contentDescription = "Scan"
                )
                Text(
                    text = "Scan",
                    style = MaterialTheme.typography.h6
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CameraAppPreview() {
    BatikInTheme {
        CameraApp()
    }
}