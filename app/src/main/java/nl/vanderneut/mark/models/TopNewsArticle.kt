package nl.vanderneut.mark.models

import com.squareup.moshi.Json


data class TopNewsArticle(
    @field:Json(name = "Id") val id: Int? = null,
    @field:Json(name = "Feed") val feed: Int? = null,
    @field:Json(name = "Title") val title: String? = null,
    @field:Json(name = "Summary") val summary: String? = null,
    @field:Json(name = "PublishDate") val publishDate: String? = null,
    @field:Json(name = "Image") val image: String? = null,
    @field:Json(name = "Url") val url: String? = null,
    @field:Json(name = "Related") val related: List<String>? = null,
    @field:Json(name = "Categories") val categories: List<Category>? = null,
    @field:Json(name = "IsLiked") val isLiked: Boolean? = null
)

