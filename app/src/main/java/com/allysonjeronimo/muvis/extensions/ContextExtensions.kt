package com.allysonjeronimo.muvis.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isConnected() : Boolean{
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return false
}