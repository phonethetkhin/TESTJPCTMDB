package com.ptk.testjpctmdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ptk.testjpctmdb.ui.ui_resource.navigation.NavGraph
import com.ptk.testjpctmdb.ui.ui_resource.theme.TestJPCTMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestJPCTMDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainComposable()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainComposable() {
    val navController = rememberNavController()

    Scaffold {
        NavGraph(
            navController = navController,
        )
    }
}
