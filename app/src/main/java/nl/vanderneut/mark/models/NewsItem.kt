package nl.vanderneut.mark.models

data class NewsItem(
    val Id: Int,
    val Feed: Int,
    val Title: String,
    val Summary: String,
    val PublishDate: String,
    val Image: String,
    val Url: String,
    val Related: List<String>,
    val Categories: List<Category>,
    val IsLiked: Boolean
)

