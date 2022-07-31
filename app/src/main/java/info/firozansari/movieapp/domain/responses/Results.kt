package info.firozansari.movieapp.domain.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "IN")
    var iN: IN? = null
)

@JsonClass(generateAdapter = true)
data class IN(
    @Json(name = "link")
    var link: String? = null
)
