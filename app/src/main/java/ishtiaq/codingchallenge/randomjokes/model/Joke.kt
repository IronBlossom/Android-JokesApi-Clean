package ishtiaq.codingchallenge.randomjokes.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class Joke(
    @PrimaryKey
    @Json(name = "id") val jokeId: Int,
    @Json(name = "category") val category: String,
    @Json(name = "delivery") val delivery: String? = "no-punchline",
    @Json(name = "flags") @Embedded val flags: Flags,
    @Json(name = "joke") var joke: String? = "has-punchline",
    @Json(name = "lang") val lang: String,
    @Json(name = "safe") val safe: Boolean,
    @Json(name = "setup") val setup: String? = "no-punchline",
    @Json(name = "type") val type: String
) : Serializable