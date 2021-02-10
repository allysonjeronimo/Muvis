package com.allysonjeronimo.muvis.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isConnected() : Boolean{
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivityManager.activeNetworkInfo
    return info != null && info.isConnected
}