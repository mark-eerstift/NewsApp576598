package nl.vanderneut.mark.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "UserName") val userName: String,
    @Json(name = "Password") val password: String
)