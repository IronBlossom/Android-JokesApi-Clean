package ishtiaq.codingchallenge.randomjokes.model


import androidx.room.Entity
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class Response(
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "error")
    val error: Boolean,
    @Json(name = "jokes")
    val jokes: MutableList<Joke>
) : Serializable