package nl.vanderneut.mark.models

import com.squareup.moshi.Json


data class TopNewsResponse(
    @field:Json(name = "Results") val results: List<TopNewsArticle>,
    @field:Json(name = "NextId") val nextId: Int? = null
)
