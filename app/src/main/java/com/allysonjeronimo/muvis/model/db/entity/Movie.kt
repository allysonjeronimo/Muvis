package com.allysonjeronimo.muvis.model.db.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id:Int,
    var title:String,
    var posterPath:String?
) : Parcelable