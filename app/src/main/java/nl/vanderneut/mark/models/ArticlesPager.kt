package nl.vanderneut.mark.models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.vanderneut.mark.api.Repository

class ArticlesPager(
    private val repository: Repository
) : PagingSource<Int, TopNewsArticle>() {

    override fun getRefreshKey(state: PagingState<Int, TopNewsArticle>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopNewsArticle> =
        try {
            val page = params.key ?: 0
            val size = params.loadSize
            val from = page * size
            Log.d("pager", "imma page:!")
            Log.d("pager", "page" + page.toString() + ", size: "+ size.toString()+" , from "+from.toString())
            val data = repository.getArticles(from = from)


                LoadResult.Page(
                    data = data.articles!!,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (data.articles.isEmpty()) null else page + 1
                )


        } catch (e: Exception) {
            Log.d("pager", "loaderror is: " + e.toString() + e.localizedMessage)
            LoadResult.Error(e)
        }
}