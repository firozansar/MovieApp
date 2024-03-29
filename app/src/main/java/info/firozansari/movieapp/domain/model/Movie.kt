package info.firozansari.movieapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * This data class represents the Movie.
 * The annotations used was the Retrofit and Gson library.
 * @author Firoz Ansari
 */
@Entity(tableName = "favorite_movies")
@Parcelize
open class Movie(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = null,
    val votes: Int,
    val isVideo: Boolean,
    val votesAverage: Float,
    val title: String,
    val popularity: Float,
    val posterPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val backdropPath: String,
    val isAdult: Boolean,
    val overview: String,
    val releaseDate: String,
    var isFavorite: Boolean = false
) : Parcelable
