package nl.vanderneut.mark.models

import com.squareup.moshi.Json

data class RegisterResponse(
    @Json(name = "Success") val success: Boolean,
    @Json(name = "Message") val message: String
)