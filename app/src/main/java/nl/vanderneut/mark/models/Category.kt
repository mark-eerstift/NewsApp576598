package nl.vanderneut.mark.models
import com.squareup.moshi.Json

data class Category(
    @field:Json(name = "Id") val id: Int? = null,
    @field:Json(name = "Name") val name: String? = null
)
