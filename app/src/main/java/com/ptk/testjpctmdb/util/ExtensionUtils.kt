package com.ptk.testjpctmdb.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.convertHoursAndMinutes(): String {
    return SimpleDateFormat("HH:mm").format(SimpleDateFormat("mm").parse(this))
}
fun String.cutHoursAndMinutes():String{
    return "${this[1]}hr ${this[3]}${this[4]}min"
}