package info.firozansari.movieapp.domain.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import info.firozansari.movieapp.domain.responses.Results

@JsonClass(generateAdapter = true)
data class WatchProvidersResponse(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "results")
    var results: Results? = null
)
