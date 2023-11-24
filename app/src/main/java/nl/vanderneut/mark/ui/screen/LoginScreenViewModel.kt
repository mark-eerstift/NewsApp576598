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

class LoginScreenViewModel(
    private val repository: Repository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> get() = _isError

    private val _registrationSuccessful = MutableStateFlow(false)
    val registrationSuccessful: StateFlow<Boolean> get() = _registrationSuccessful

    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _isError.value = true
        }
    }

    // Method to handle successful registration
    private fun handleSuccessfulRegistration() {
        // Set the registrationSuccessful state to true
        _registrationSuccessful.value = true
    }

    // Method to clear registration success state
    fun clearRegistrationSuccess() {
        _registrationSuccessful.value = false
    }

    // Method to handle successful login
    private fun handleSuccessfulLogin(authToken: String) {
        // Set the AuthToken in shared preferences
        sharedPreferencesHelper.setAuthToken(authToken)
    }

    // Method to check if the user is logged in
    fun isLoggedIn(): Boolean {
        return sharedPreferencesHelper.getAuthToken() != null
    }

    // Method to handle user logout
    fun logout() {
        // Clear the AuthToken in shared preferences
        sharedPreferencesHelper.clearAuthToken()
    }

    fun handleLoginOrRegister(email: String, password: String, isLogin: Boolean, home: () -> Unit) =
        viewModelScope.launch(errorHandler) {
            _loading.value = true
            try {
                if (isLogin) {
                    // Make the API call to login
                    Log.d("LoginScreenViewModel", "Attempting login for email: $email")

                    val response = repository.login(email, password)

                    if (response.isSuccessful) {
                        // Successful login, handle the response
                        Log.d("LoginScreenViewModel", "Login successful")
                        val authTokenResponse = response.body() as AuthTokenResponse
                        Log.d("authToken", authTokenResponse.toString())
                        // Save the token
                        handleSuccessfulLogin(authTokenResponse.authToken)
                        // Do something with authTokenResponse, like saving the token
                        home()
                    } else {
                        // Handle login failure
                        Log.e("LoginScreenViewModel", "Login failed with code: ${response.code()}")
                        _isError.value = true
                    }
                } else {
                    // Make the API call to register
                    Log.d("LoginScreenViewModel", "Attempting registration for email: $email")
                    val response = repository.register(email, password)

                    if (response.isSuccessful) {
                        // Check the actual type of the response body
                        val responseBody = response.body()
                        if (responseBody is RegisterResponse) {
                            if (responseBody.success) {
                                // Successful registration, handle the response
                                Log.d("LoginScreenViewModel", "Registration successful: ${responseBody.success}")
                                handleSuccessfulRegistration()
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
                }
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("LoginScreenViewModel", "Exception during operation: $e")
                _isError.value = true
            } finally {
                _loading.value = false
            }
        }

    // Method to clear errors
    fun clearError() {
        _isError.value = false
    }
}
