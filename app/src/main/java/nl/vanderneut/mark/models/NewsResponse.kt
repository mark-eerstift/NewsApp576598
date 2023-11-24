package nl.vanderneut.mark.models


data class NewsResponse(
    val Results: List<NewsItem>,
    val NextId: Int
)
