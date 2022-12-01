package nl.vanderneut.mark

import android.app.Application
import nl.vanderneut.mark.api.Api
import nl.vanderneut.mark.api.NewsManager
import nl.vanderneut.mark.api.Repository

class MainApp: Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager = manager)
    }


}