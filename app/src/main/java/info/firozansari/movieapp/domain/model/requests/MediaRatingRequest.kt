package info.firozansari.movieapp.domain.model.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaRatingRequest(
    @Json(name = "value")
    var value: Float
)
