package info.firozansari.movieapp.domain.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import info.firozansari.movieapp.domain.responses.Cast

@JsonClass(generateAdapter = true)
data class MediaCastResponse(
    @Json(name = "cast")
    var cast: List<Cast>,
//    @Json(name = "crew")
//    var crew: List<Crew>,
    @Json(name = "id")
    var id: Int
)
