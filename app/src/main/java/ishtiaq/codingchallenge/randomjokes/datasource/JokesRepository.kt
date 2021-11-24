package ishtiaq.codingchallenge.randomjokes.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ishtiaq.codingchallenge.randomjokes.model.Joke
import ishtiaq.codingchallenge.randomjokes.viewmodel.ApiStatus
import java.io.IOException

class JokesRepository(private val database: JokesDatabase) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    fun getJokesFromDatabase() = database.jokeDao.getJokes()

    private suspend fun insertJokesToDatabase(jokes: List<Joke>) = database.jokeDao.insertAll(jokes)
    private suspend fun cleanJokesFromDatabase() = database.jokeDao.nukeTableData()


    suspend fun refreshJokes(category: String, vararg blacklistFlags: String, amount: Int = 10) {

        val jokesFromCloud =
            getJokesFromCloud(category, blacklistFlags = blacklistFlags, amount = amount)

        // fresh jokes in database
        cleanJokesFromDatabase()
        insertJokesToDatabase(jokesFromCloud)
    }

/**
 * Fetching api data and returning List of Jokes object.
 * @see ApiStatus pushed through _status which can be observed by the backing
 * variable status
 * */
    private suspend fun getJokesFromCloud(
        category: String,
        vararg blacklistFlags: String, // Pass blacklist flags
        amount: Int = 10 // As requested
    ): List<Joke> {

        _status.value = ApiStatus.LOADING

        // for function calling convenience to url string (nsfw,racist etc)
        val argAsStr = blacklistFlags.asList().joinToString(separator = ",") { it }

        try {
            val response = JokeApi.jokeInterface.getJokes(category, argAsStr, amount)

            if (response.error) {
                _status.value = ApiStatus.ERROR
            } else with(response) {
                // Change objects in list to suite databinding
                jokes.mapIndexed { index, joke ->
                    if (joke.joke == "has-punchline") {
                        jokes[index] = joke.copy(joke = joke.setup)
                    }
                }
                _status.value = ApiStatus.DONE
                return jokes
            }
        } catch (networkException: IOException) {
            _status.value = ApiStatus.ERROR
            networkException.printStackTrace()
        }

        return emptyList()
    }
}