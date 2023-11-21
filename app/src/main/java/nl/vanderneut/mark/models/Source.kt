package nl.vanderneut.mark.models

import com.squareup.moshi.Json

data class Source(
    @field:Json(name = "Id") val id: String? = null,
    @field:Json(name = "Name") val name: String? = null
)


