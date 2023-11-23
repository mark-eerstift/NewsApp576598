package nl.vanderneut.mark.api

import nl.vanderneut.mark.models.AuthTokenResponse
import nl.vanderneut.mark.models.NewsResponse
import nl.vanderneut.mark.models.RegisterResponse
import retrofit2.Response

class Repository(private val manager: NewsManager) {

    val retrofitService: NewsService
        get() = Api.retrofitService

    suspend fun getTopArticles(
        count: Int = 20,
        feed: Int? = null,
        feeds: String? = null,
        category: Int? = null
    ): Response<NewsResponse> = manager.getTopArticles(count, feed, feeds, category)

    suspend fun getArticlesById(
        id: Int,
        count: Int = 1,
        feed: Int? = null,
        feeds: String? = null,
        category: Int? = null
    ): Response<NewsResponse> = manager.getArticlesById(id, count, feed, feeds, category)

    suspend fun register(username: String, password: String): Response<RegisterResponse> =
        manager.register(username, password)

    suspend fun login(username: String, password: String): Response<AuthTokenResponse> =
        manager.login(username, password)
}