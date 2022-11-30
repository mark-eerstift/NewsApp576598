package nl.vanderneut.mark.API

import nl.vanderneut.mark.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET(value="top-headlines")
    fun getTopArticles(@Query ("country") country:String, @Query("apiKey") apiKey: String): Call<TopNewsResponse>
}