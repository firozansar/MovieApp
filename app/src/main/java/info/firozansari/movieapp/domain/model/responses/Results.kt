package info.firozansari.movieapp.domain.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import info.firozansari.movieapp.domain.model.responses.IN

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "IN")
    var iN: IN? = null
)
