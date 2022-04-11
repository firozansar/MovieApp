package info.firozansari.movieapp.data.api.response

import com.google.gson.annotations.SerializedName

data class PageResponse(
  @SerializedName("page") val page: Int,
  @SerializedName("total_results") var totalResults: Int,
  @SerializedName("total_pages") var totalPages: Int,
  @SerializedName("results") var results: List<info.firozansari.movieapp.data.api.response.MovieResponse>? = null
)
