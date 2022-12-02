package nl.vanderneut.mark.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.vanderneut.mark.api.Api.API_KEY
import nl.vanderneut.mark.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager(private val service: NewsService) {

    suspend fun getArticles(country:String, from: Int):Response<TopNewsResponse> = withContext(Dispatchers.IO){
        service.getTopArticles(country,API_KEY, from)

    }


}