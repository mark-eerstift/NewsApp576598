package nl.vanderneut.mark

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    fun getAuthToken(): String? {
        return sharedPreferences.getString("authToken", null)
    }

    fun setAuthToken(authToken: String?) {
        sharedPreferences.edit {
            putString("authToken", authToken)
        }
    }

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    fun clearAuthToken() {
        sharedPreferences.edit {
            remove("authToken")
        }
    }
}