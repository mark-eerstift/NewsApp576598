package nl.vanderneut.mark.models

data class TopNewsResponse(
    val results: List<TopNewsArticle>,
    val nextId: Int? = null
)
