package nl.vanderneut.mark.ui

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.vanderneut.mark.MainApp
import nl.vanderneut.mark.models.TopNewsResponse
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import nl.vanderneut.mark.models.ArticlesPager
import nl.vanderneut.mark.models.TopNewsArticle

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse: Flow<PagingData<TopNewsArticle>> = Pager(
        pagingSourceFactory = { ArticlesPager(repository) },
        config = PagingConfig(pageSize = 20)
    ).flow.cachedIn(viewModelScope)
    val newsResponse: Flow<PagingData<TopNewsArticle>>
        get() = _newsResponse

//    private val _newsResponse: Flow<PagingData<TopNewsResponse>>
//    val newsResponse: StateFlow<TopNewsResponse>
//        get() = _newsResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _favoriteArticles = mutableStateListOf<String>()
    val favoriteArticles: List<String> = _favoriteArticles
    fun addFav(url: String) {
        _favoriteArticles.add(url)
    }

    fun remove(url: String) {
        _favoriteArticles.remove(url)
    }

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError

    val errorHandler = CoroutineExceptionHandler{
        _, error ->
        if(error is Exception){
            _isError.value = true
        }
    }

//    fun getTopArticles(){
//        _isLoading.value  = true
//        viewModelScope.launch(Dispatchers.IO + errorHandler) {
//            _newsResponse.value = repository.getArticles(0, 20).articles
//            _isLoading.value = false
//        }
//
//    }

}