package nl.vanderneut.mark

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val AUTH_TOKEN_KEY = "authToken"
    }

    fun saveAuthToken(authToken: String) {
        sharedPreferences.edit {
            putString(AUTH_TOKEN_KEY, authToken)
        }
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    }
}