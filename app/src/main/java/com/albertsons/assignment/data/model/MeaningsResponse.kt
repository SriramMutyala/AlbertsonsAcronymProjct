package com.albertsons.assignment.data.model

import com.google.gson.annotations.SerializedName

data class Dictionary(
    @SerializedName("sf") val shortForm: String,
    @SerializedName("lfs") val list: List<LFS>
)

data class LFS(@SerializedName("lf") val fullForm: String)
