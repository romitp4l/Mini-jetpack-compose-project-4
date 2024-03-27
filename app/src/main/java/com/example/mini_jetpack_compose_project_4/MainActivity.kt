package com.example.mini_jetpack_compose_project_4

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mini_jetpack_compose_project_4.ui.theme.Minijetpackcomposeproject4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var bitmap = remember {
                mutableStateOf<Bitmap?>(null)
            }

            val cameraLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {

                    if (it != null) {
                        bitmap.value = it
                    }


                }

            var permissionLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
                    if (it) {

                        cameraLauncher.launch()


                    }
                }
            Minijetpackcomposeproject4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        var context = LocalContext.current
                        TextButton(onClick = {
                            if (ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                cameraLauncher.launch()
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }) {
                            Text(text = "Launch the camera for usage")
                        }

                        bitmap.value.let {
                            if (it != null) {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                }

            }
        }
    }


}



