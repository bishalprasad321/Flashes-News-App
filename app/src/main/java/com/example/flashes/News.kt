package com.example.flashes

import org.json.JSONObject

data class News (
    val source : String,
    val title : String,
    val description : String,
    val url : String,
    val urlToImage : String,
    val time : String
    )