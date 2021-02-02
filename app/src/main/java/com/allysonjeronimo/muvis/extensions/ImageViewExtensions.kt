package com.allysonjeronimo.muvis.extensions

import android.widget.ImageView
import com.allysonjeronimo.muvis.R
import com.squareup.picasso.Picasso

fun ImageView.load(url:String){
    Picasso
        .get()
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .into(this)
}