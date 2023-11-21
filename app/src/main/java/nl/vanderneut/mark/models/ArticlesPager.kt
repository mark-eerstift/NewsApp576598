package nl.vanderneut.mark.models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.vanderneut.mark.api.Repository

class ArticlesPager(
    private val repository: Repository
) : PagingSource<Int, TopNewsArticle>() {
    private val articleMapper = MapListPager()
    override fun getRefreshKey(state: PagingState<Int, TopNewsArticle>): Int? {
        return null
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(true)
    val isError: StateFlow<Boolean> = _isError

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopNewsArticle> {
        _isLoading.value = true
        val result = fetch(params.key ?: -1, params.loadSize).getOrElse {


            val isError: StateFlow<Boolean> = _isError
            return LoadResult.Error(it)

        }

        Log.d("pager", result.toString())
        _isLoading.value = false
        return LoadResult.Page(result, null, (params.key ?: 0) + 1)
    }

    private suspend fun fetch(startKey: Int, loadSize: Int): Result<List<TopNewsArticle>> {


        val response = repository.getArticles()
Log.d("debug", response.toString())
        return when {
            response.isSuccessful -> {

                val body = response.body()

                if (body !== null) {

                    var exx = articleMapper.tryOut(body)

                    exx

                } else {

                    Result.failure(IllegalStateException())
                }
            }

            else -> {
                Log.d("pager", "result fails")
                Result.failure(Exception(IllegalStateException()))
            }
        }
    }
}