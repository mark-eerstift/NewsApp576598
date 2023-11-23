package nl.vanderneut.mark.models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.vanderneut.mark.api.NewsService

class NewsPagingSource(
    private val service: NewsService,
    private val count: Int = 20,
    private val feed: Int? = null,
    private val feeds: String? = null,
    private val category: Int? = null
) : PagingSource<Int, NewsItem>() {

    private var topArticlesRequestCount = 0
    private var articlesByIdRequestCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = if (nextPageNumber == 1) {
                topArticlesRequestCount++
                // Initial load, use api/Articles without specifying an ID
                service.getTopArticles(count, feed, feeds, category)
            } else {
                articlesByIdRequestCount++
                // Subsequent loads, use api/Articles/{id} with the provided ID
                service.getArticlesById(nextPageNumber, count, feed, feeds, category)
            }

            // Log the loading information
            Log.d("NewsPagingSource", "Loading Page: $nextPageNumber, PrevKey: ${params.key}")

            // Log the NextId
            val nextId = response.body()?.NextId
            Log.d("NewsPagingSource", "NextId: $nextId")

            // Log article bodies
            response.body()?.Results?.forEach { article ->
                Log.d("NewsPagingSource", "Article Id: ${article.Id}, Title: ${article.Title}")
            }

            // Log request counts
            Log.d("NewsPagingSource", "TopArticles Requests: $topArticlesRequestCount")
            Log.d("NewsPagingSource", "ArticlesById Requests: $articlesByIdRequestCount")

            return LoadResult.Page(
                data = response.body()?.Results ?: emptyList(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = nextId
            )
        } catch (e: Exception) {
            Log.e("NewsPagingSource", "Error loading articles", e)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        // Invalidate the refresh key to force refresh after a modification
        return null
    }
}