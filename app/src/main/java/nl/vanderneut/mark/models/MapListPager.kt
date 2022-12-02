package nl.vanderneut.mark.models

class MapListPager {
    fun mapListPager(entity: TopNewsResponse): Result<TopNewsResponse> = runCatching {
        with(entity) {
            TopNewsResponse(
                articles = mapResults(articles).getOrThrow(),
                totalResults = totalResults!!,
                status = status!!
            )
        }
    }


    fun tryOut(entity: TopNewsResponse): Result<List<TopNewsArticle>> = runCatching {
        with(entity) {
            mapResults(entity.articles).getOrThrow()
        }
    }


    fun mapList(entityList: List<TopNewsArticle>): Result<List<TopNewsArticle>> = runCatching {
        entityList.map {
            map(it).getOrThrow()
        }
    }

    fun mapResults(entities: List<TopNewsArticle>): Result<List<TopNewsArticle>> = runCatching {
        entities.map {
            with(it) {
                TopNewsArticle(
                    id = id!!,
                    source = source!!,
                    author = author!!,
                    title = title!!,
                    description = description!!,
                    url = url!!,
                    urlToImage = urlToImage!!,
                    publishedAt = publishedAt!!,
                    content = content!!
                )
            }
        }
    }


    fun map(entity: TopNewsArticle): Result<TopNewsArticle> = runCatching {
        with(entity) {
            TopNewsArticle(
                id = id!!,
                source = source!!,
                author = author!!,
                title = title!!,
                description = description!!,
                url = url!!,
                urlToImage = urlToImage!!,
                publishedAt = publishedAt!!,
                content = content!!
            )
        }
    }




}
