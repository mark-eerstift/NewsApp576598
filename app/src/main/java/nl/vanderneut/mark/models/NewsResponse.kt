package nl.vanderneut.mark.models

//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//
//
//@JsonClass(generateAdapter = true)
//data class TopNewsResponse(
//    @field:Json(name = "Results") val results: List<TopNewsArticle>
//)
//
data class NewsResponse(
    val Results: List<NewsItem>,
    val NextId: Int
)
