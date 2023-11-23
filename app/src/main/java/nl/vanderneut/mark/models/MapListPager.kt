//package nl.vanderneut.mark.models
//
//class MapListPager {
// /*   fun mapListPager(entity: TopNewsResponse): Result<TopNewsResponse> = runCatching {
//        with(entity) {
//            TopNewsResponse(
//                results = mapResults(results).getOrThrow(),
//                nextId = nextId!!
//            )
//        }
//    }*/
//
//
//    fun tryOut(entity: NewsResponse): Result<List<NewsItem>> = runCatching {
//        with(entity) {
//
//            mapResults(entity.Results).getOrThrow()
//        }
//    }
//
//
///*    fun mapList(entityList: List<TopNewsArticle>): Result<List<TopNewsArticle>> = runCatching {
//        entityList.map {
//            map(it).getOrThrow()
//        }
//    }*/
//
//    fun mapResults(entities: List<NewsItem>): Result<List<NewsItem>> = runCatching {
//
//        entities.map {
//
//            with(it) {
//
//                NewsItem(
//                    id = id!!,
//                    feed = feed,
//                    title = title,
//                    summary = summary,
//                    publishDate = publishDate,
//                    image = image,
//                    url = url,
//                    related = related,
//                    categories = categories,
//                    isLiked = isLiked
//                )
//            }
//        }
//
//    }
//
//
//    fun map(entity: NewsItem): Result<NewsItem> = runCatching {
//        with(entity) {
//            NewsItem(
//                id = id!!,
//                feed = feed,
//                title = title,
//                summary = summary,
//                publishDate = publishDate,
//                image = image,
//                url = url,
//                related = related,
//                categories = categories,
//                isLiked = isLiked
//            )
//        }
//    }
//
//
//}
