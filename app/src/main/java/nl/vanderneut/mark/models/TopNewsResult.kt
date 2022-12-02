package nl.vanderneut.mark.models

data class TopNewsResult(
    val status: String,
    val totalResults: Int,
    val articles: List<TopNewsArticle>
)