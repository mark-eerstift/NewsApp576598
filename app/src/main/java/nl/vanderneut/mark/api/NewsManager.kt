package nl.vanderneut.mark.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
//import nl.vanderneut.mark.api.Api.API_KEY
import nl.vanderneut.mark.models.TopNewsResponse
import retrofit2.Response

class NewsManager(private val service: NewsService) {

    suspend fun getArticles(country: String, from: Int): Response<TopNewsResponse> =
        withContext(Dispatchers.IO) {
            //service.getTopArticles(country, API_KEY, from)
            service.getTopArticles()
        }


}