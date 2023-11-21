package nl.vanderneut.mark.api

class Repository(private val manager: NewsManager) {

    suspend fun getArticles() = manager.getArticles()


}