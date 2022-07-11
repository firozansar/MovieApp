package info.firozansari.movieapp.domain.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import info.firozansari.movieapp.domain.model.responses.VideoResult

@JsonClass(generateAdapter = true)
data class Videos(
    @Json(name = "results")
    var videosList: List<VideoResult>
)
