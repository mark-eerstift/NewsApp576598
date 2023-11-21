package nl.vanderneut.mark.models

data class TopNewsArticle(
    val id: Int? = null,
    val feed: Int? = null,
    val title: String? = null,
    val summary: String? = null,
    val publishDate: String? = null,
    val image: String? = null,
    val url: String? = null,
    val related: List<String>? = null,
    val categories: List<Category>? = null,
    val isLiked: Boolean? = null
)

