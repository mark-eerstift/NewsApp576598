package nl.vanderneut.mark.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.vanderneut.mark.SharedPreferencesHelper
import nl.vanderneut.mark.api.Repository
import nl.vanderneut.mark.models.AuthTokenResponse
import nl.vanderneut.mark.models.RegisterResponse

class LoginScreenViewModel(private val repository: Repository, private val sharedPreferencesHelper: SharedPreferencesHelper) : ViewModel() {

    private val _loading = MutableStateFlow(false)

    val loading: StateFlow<Boolean> get() = _loading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> get() = _isError

    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _isError.value = true
        }
    }



    fun signInWithEmailAndPassword(username: String, password: String, home: () -> Unit) =
        viewModelScope.launch(errorHandler) {
            _loading.value = true
            try {
                // Make the API call to login
                Log.d("LoginScreenViewModel", "Attempting login for username: $username")

                val response = repository.login(username, password)

                if (response.isSuccessful) {
                    // Successful login, handle the response
                    Log.d("LoginScreenViewModel", "Login successful")
                    val authTokenResponse = response.body() as AuthTokenResponse
                    // Save the token
                    sharedPreferencesHelper.saveAuthToken(authTokenResponse.authToken)
                    // Do something with authTokenResponse, like saving the token
                    home()
                } else {
                    // Handle login failure
                    Log.e("LoginScreenViewModel", "Login failed with code: ${response.code()}")
                    _isError.value = true
                }
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("LoginScreenViewModel", "Exception during login: $e")
                _isError.value = true
            } finally {
                _loading.value = false
            }
        }

    // Method to clear errors
    fun clearError() {
        _isError.value = false
    }

    fun createUserWithEmailAndPassword(username: String, password: String, home: () -> Unit) =
        viewModelScope.launch(errorHandler) {
            _loading.value = true

            // Make the API call to register
            try {
                Log.d("LoginScreenViewModel", "Attempting registration for username: $username")
                val response = repository.register(username, password)

                if (response.isSuccessful) {
                    // Check the actual type of the response body
                    val responseBody = response.body()
                    if (responseBody is RegisterResponse) {
                        // Successful registration, handle the response
                        Log.d("LoginScreenViewModel", "Registration successful: ${responseBody.success}")
                        if (responseBody.success) {
                            // Registration successful
                            home()
                        } else {
                            // Registration failed, handle the message
                            Log.e("LoginScreenViewModel", "Registration failed: ${responseBody.message}")
                            _isError.value = true
                        }
                    } else {
                        // Handle unexpected response body type
                        Log.e("LoginScreenViewModel", "Unexpected response body type: $responseBody")
                        _isError.value = true
                    }
                } else {
                    // Handle registration failure
                    Log.e("LoginScreenViewModel", "Registration failed with code: ${response.code()}")
                    _isError.value = true
                }
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("LoginScreenViewModel", "Exception during registration: $e")
                _isError.value = true
            } finally {
                _loading.value = false
            }
        }
}