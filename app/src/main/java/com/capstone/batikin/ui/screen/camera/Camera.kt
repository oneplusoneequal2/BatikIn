package com.capstone.batikin.ui.screen.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.capstone.batikin.R
import com.capstone.batikin.ml.Model
import com.capstone.batikin.ui.components.TopBarGeneral
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
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

    val showPreview = remember { mutableStateOf(false) }

    val context = LocalContext.current

//    val getGallery =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
//            image= it
//            showPreview.value = true
//        }

    /** ini yg back **/
    val getGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                image = uri
                showPreview.value = true
            } else {
                // Handle pas batal milih gmbr balik ke camera screen
                image = Uri.EMPTY //Setel gambar ke kosong untuk menghapus gambar yang dipilih sebelumnya
                showPreview.value = false // Sembuynikan  preview
            }
        }

    val launcherCameraX =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == 200) {
                showPreview.value = true
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

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarGeneral(
            titleResId = R.string.title_batik_scan
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            if (showPreview.value && image != null) {
                Image(
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = null,
                    modifier = Modifier
                        .width(400.dp)
                        .height(400.dp)
                        .padding(vertical = 16.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.batik_capture_example),
                    contentDescription = null,
                )
            }
            //        Image(
            //            painter = rememberAsyncImagePainter(image),
            //            contentDescription = null,
            //            modifier = Modifier
            //                .width(300.dp)
            //                .height(300.dp)
            //        )
            if (image?.equals(Uri.EMPTY) == true) {

                Text(
                    text = stringResource(R.string.capture_batik),
                    style = MaterialTheme.typography.h6,
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
                //            Button(onClick = { }) {
                //                Icon(
                //                    imageVector = Icons.Default.AdfScanner,
                //                    contentDescription = "Scan"
                //                )
                //                Text(
                //                    text = "Scan",
                //                    style = MaterialTheme.typography.h6
                //                )
                //            }

                // TFLite

                val model = Model.newInstance(context)
                val imgBitmap = BitmapFactory.decodeStream(image?.let {
                    context.contentResolver.openInputStream(
                        it
                    )
                })

                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)


                val inputBitmap = Bitmap.createScaledBitmap(imgBitmap, 224, 224, true)

                val tfImage = TensorImage(DataType.FLOAT32)
                tfImage.load(inputBitmap)

                inputFeature0.loadBuffer(tfImage.buffer)

                val outputData = model.process(inputFeature0)

            //            Toast.makeText(context, outputData.toString(), Toast.LENGTH_SHORT).show()
                val outputFeature = outputData.outputFeature0AsTensorBuffer.floatArray

                val items = ArrayList<Float>()
                for (i in outputFeature) {
                    items.add(i)
                }

                val maxValue = items.maxOrNull()

                val maxValueIndex = items.indexOf(maxValue)

                val batikTypes = arrayOf(
                    stringResource(R.string.batik_cendrawasih),
                    stringResource(R.string.batik_dayak),
                    stringResource(R.string.batik_geblek_renteng),
                    stringResource(R.string.batik_ikat_celup),
                    stringResource(R.string.batik_insang),
                    stringResource(R.string.batik_kawung),
                    stringResource(R.string.batik_lasem),
                    stringResource(R.string.batik_megamendung),
                    stringResource(R.string.batik_pala),
                    stringResource(R.string.batik_parang),
                    stringResource(R.string.batik_poleng),
                    stringResource(R.string.batik_sekar_jagad),
                    stringResource(R.string.batik_tambal)
                )


                Toast.makeText(context, items.indexOf(maxValue).toString(), Toast.LENGTH_SHORT)
                    .show()

                if (maxValue != null) {
                    Text(
                        text = batikTypes[maxValueIndex] + " - " + String.format(
                            "%.2f",
                            maxValue * 100f
                        ) + "%"
                    )
                }

                //            LazyColumn(
                //                modifier = Modifier.height(100.dp)
                //            ) {
                //                items(items.sortedByDescending { it}) {
                //                    Text(
                //                        text = batikTypes[maxValueIndex] + " - " + String.format("%.2f", it*100f)
                //                    )
                //                }
                //            }

                model.close()
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