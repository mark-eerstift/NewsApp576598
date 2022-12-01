package nl.vanderneut.mark.api

import nl.vanderneut.mark.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(@Query("country") country:String): TopNewsResponse


}