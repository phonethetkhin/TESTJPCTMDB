package com.ptk.testjpctmdb.ui.ui_resource.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("/home_screen")

}