package nl.vanderneut.mark.api

import nl.vanderneut.mark.models.AuthTokenResponse
import nl.vanderneut.mark.models.LoginRequest
import nl.vanderneut.mark.models.NewsResponse
import nl.vanderneut.mark.models.RegisterRequest
import nl.vanderneut.mark.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {
    @GET("api/Articles")
    suspend fun getTopArticles(
        @Query("count") count: Int = 20,
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: Int? = null
    ): Response<NewsResponse>

    @GET("api/Articles/{id}")
    suspend fun getArticlesById(
        @Path("id") id: Int,
        @Query("count") count: Int = 1,
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: Int? = null
    ): Response<NewsResponse>

    @POST("api/Users/Register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/Users/Login")
    suspend fun login(@Body request: LoginRequest): Response<AuthTokenResponse>

}