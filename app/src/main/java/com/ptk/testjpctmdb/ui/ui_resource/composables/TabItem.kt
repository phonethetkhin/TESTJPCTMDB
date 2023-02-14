package com.ptk.testjpctmdb.ui.ui_resource.composables


sealed class TabItem(val title: String) {

    object Movies : TabItem(title = "Movies")
    object Events :
        TabItem(title = "Events")

    object Plays : TabItem(
        title = "Plays"
    )

    object Sports : TabItem(
        title = "Sports"
    )

    object Activities : TabItem(
        title = "Activities"
    )


}