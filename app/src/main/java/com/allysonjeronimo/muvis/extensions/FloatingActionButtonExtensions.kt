package com.allysonjeronimo.muvis.extensions

import android.os.Build
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun FloatingActionButton.setIcon(iconResource:Int){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
        setImageDrawable(resources.getDrawable(iconResource, context.theme))
    }
    else{
        setImageDrawable(resources.getDrawable(iconResource))
    }
}