package com.allysonjeronimo.muvis.model.db.entity

data class Configuration(
    val baseUrl:String?,
    val secureBaseUrl:String?,
    val backdropSizes:List<String>?,
    val posterSizes:List<String>?
)