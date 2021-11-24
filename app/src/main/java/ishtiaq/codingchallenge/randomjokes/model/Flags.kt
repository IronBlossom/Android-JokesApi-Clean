package ishtiaq.codingchallenge.randomjokes.model


import com.squareup.moshi.Json
import java.io.Serializable

data class Flags(
    @Json(name = "explicit")
    val explicit: Boolean,
    @Json(name = "nsfw")
    val nsfw: Boolean,
    @Json(name = "political")
    val political: Boolean,
    @Json(name = "racist")
    val racist: Boolean,
    @Json(name = "religious")
    val religious: Boolean,
    @Json(name = "sexist")
    val sexist: Boolean
): Serializable