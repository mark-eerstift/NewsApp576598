package nl.vanderneut.mark.models

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.vanderneut.mark.api.Repository

class ArticlesPager(
    private val repository: Repository
) : PagingSource<Int, TopNewsArticle>() {
    private val articleMapper = MapListPager()
    override fun getRefreshKey(state: PagingState<Int, TopNewsArticle>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopNewsArticle>{
        val result = fetch(params.key?:-1, params.loadSize).getOrElse{
            return LoadResult.Error(it)
        }
        return LoadResult.Page(result, null, (params.key ?:0) +1)
    }

    private suspend fun fetch(startKey: Int, loadSize: Int): Result<List<TopNewsArticle>>{
        val response = repository.getArticles(loadSize.coerceIn(1,10))
        return when {
            response.isSuccessful -> {
                val body = response.body()

                if (body !== null) {
                   // var ayy = articleMapper.mapListPager(body)
                    var exx = articleMapper.tryOut(body)
                    exx
                } else {
                    Result.failure(IllegalStateException())
                }
            }
            else -> Result.failure(Exception(IllegalStateException()))
        }
    }
}