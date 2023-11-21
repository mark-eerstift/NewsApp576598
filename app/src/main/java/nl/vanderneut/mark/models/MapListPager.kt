package nl.vanderneut.mark.models

class MapListPager {
 /*   fun mapListPager(entity: TopNewsResponse): Result<TopNewsResponse> = runCatching {
        with(entity) {
            TopNewsResponse(
                results = mapResults(results).getOrThrow(),
                nextId = nextId!!
            )
        }
    }*/


    fun tryOut(entity: TopNewsResponse): Result<List<TopNewsArticle>> = runCatching {
        with(entity) {

            mapResults(entity.results).getOrThrow()
        }
    }


/*    fun mapList(entityList: List<TopNewsArticle>): Result<List<TopNewsArticle>> = runCatching {
        entityList.map {
            map(it).getOrThrow()
        }
    }*/

    fun mapResults(entities: List<TopNewsArticle>): Result<List<TopNewsArticle>> = runCatching {

        entities.map {

            with(it) {

                TopNewsArticle(
                    id = id!!,
                    feed = feed,

                    title = title,
                    summary = summary,
                    publishDate = publishDate,
                    image = image,
                    url = url,
                    related = related,
                    categories = categories,
                    isLiked = isLiked
                )
            }
        }

    }


    fun map(entity: TopNewsArticle): Result<TopNewsArticle> = runCatching {
        with(entity) {
            TopNewsArticle(
                id = id!!,
                feed = feed,

                title = title,
                summary = summary,
                publishDate = publishDate,
                image = image,
                url = url,
                related = related,
                categories = categories,
                isLiked = isLiked
            )
        }
    }


}
