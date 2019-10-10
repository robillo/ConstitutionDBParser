package com.assistiveapps.constitutiondbparser.data.model

import com.google.gson.annotations.SerializedName

data class ReadElement(@SerializedName("read_element_id") val id: Int,
                       @SerializedName("title") val title: String?,
                       @SerializedName("subtitle") val subtitle: String?,
                       @SerializedName("category") val categoryName: String?,
                       @SerializedName("short_description") val shortDescription: String?,
                       @SerializedName("content") val content: String?,
                       @SerializedName("tags") val tags: List<String>?)