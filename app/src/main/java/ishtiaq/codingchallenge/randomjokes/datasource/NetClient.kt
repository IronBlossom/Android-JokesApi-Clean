package ishtiaq.codingchallenge.randomjokes.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ishtiaq.codingchallenge.randomjokes.model.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "https://v2.jokeapi.dev"

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

interface JokeInterface {
    @GET("joke/{category}")
    suspend fun getJokes(
        @Path("category") category: String = "Any",
        @Query("blacklistFlags") blacklistFlags: String,
        @Query("amount") amount: Int
    ): Response

}

object JokeApi { // Making the httpclient singleton
    val jokeInterface: JokeInterface by lazy {
        retrofit.create(JokeInterface::class.java)
    }
}

