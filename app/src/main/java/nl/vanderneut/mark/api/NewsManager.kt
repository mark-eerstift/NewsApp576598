package nl.vanderneut.mark.api

//import nl.vanderneut.mark.api.Api.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.vanderneut.mark.models.AuthTokenResponse
import nl.vanderneut.mark.models.LoginRequest
import nl.vanderneut.mark.models.NewsResponse
import nl.vanderneut.mark.models.RegisterRequest
import nl.vanderneut.mark.models.RegisterResponse
import retrofit2.Response

class NewsManager(private val service: NewsService) {

    suspend fun getTopArticles(
        count: Int = 20,
        feed: Int? = null,
        feeds: String? = null,
        category: Int? = null
    ): Response<NewsResponse> = withContext(Dispatchers.IO) {
        service.getTopArticles(count, feed, feeds, category)
    }

    suspend fun getArticlesById(
        id: Int,
        count: Int = 1,
        feed: Int? = null,
        feeds: String? = null,
        category: Int? = null
    ): Response<NewsResponse> = withContext(Dispatchers.IO) {
        service.getArticlesById(id, count, feed, feeds, category)
    }

    suspend fun register(username: String, password: String): Response<RegisterResponse> =
        withContext(Dispatchers.IO) {
            service.register(RegisterRequest(username = username, password = password))
        }

    suspend fun login(username: String, password: String): Response<AuthTokenResponse> =
        withContext(Dispatchers.IO) {
            service.login((LoginRequest(userName = username, password = password)))
        }
}