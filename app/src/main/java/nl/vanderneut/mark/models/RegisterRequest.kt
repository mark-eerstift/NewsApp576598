package nl.vanderneut.mark.models

import com.squareup.moshi.Json

data class RegisterRequest(
    @Json(name = "UserName") val username: String,
    @Json(name = "Password") val password: String
)