package nl.vanderneut.mark.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.vanderneut.mark.MainApp
import nl.vanderneut.mark.models.NewsItem
import nl.vanderneut.mark.models.NewsPagingSource

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = getApplication<MainApp>().repository

    private val _favoriteArticles = mutableStateListOf<NewsItem>()
    val favoriteArticles: List<NewsItem> = _favoriteArticles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private val _newsResponse: Flow<PagingData<NewsItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            NewsPagingSource(
                service = repository.retrofitService,
                count = 20 // Adjust as needed
            )
        }
    ).flow.cachedIn(viewModelScope)

    val newsResponse: Flow<PagingData<NewsItem>>
        get() = _newsResponse

    fun addFav(art: NewsItem) {
        _favoriteArticles.add(art)
    }

    fun remove(art: NewsItem) {
        _favoriteArticles.remove(art)
    }

    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _isError.value = true
            _isLoading.value = false
            Log.e("MainViewModel", "Error: $error")
        }
    }
}

