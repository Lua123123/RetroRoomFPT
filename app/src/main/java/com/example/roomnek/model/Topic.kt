package com.example.roomnek.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Topic {
    @SerializedName("success")
    @Expose
    var success: List<Success>? = null
}