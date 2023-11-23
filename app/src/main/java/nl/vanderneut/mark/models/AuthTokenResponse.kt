package nl.vanderneut.mark.models

import com.squareup.moshi.Json

data class AuthTokenResponse(
    @Json(name = "AuthToken") val authToken: String
)