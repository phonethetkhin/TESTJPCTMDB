package com.ptk.testjpctmdb.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navHostController: NavHostController) {
    HomeScreenBody()
}

@Composable
fun HomeScreenBody() {
    Text("Hello WOrld")
}