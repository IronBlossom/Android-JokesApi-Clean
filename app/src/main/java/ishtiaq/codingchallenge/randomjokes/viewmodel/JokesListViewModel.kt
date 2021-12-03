package ishtiaq.codingchallenge.randomjokes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ishtiaq.codingchallenge.randomjokes.datasource.JokesRepository
import ishtiaq.codingchallenge.randomjokes.datasource.getDatabase
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, DONE, ERROR }
class JokesListViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = JokesRepository(getDatabase(application))

    val jokes = repo.getJokesFromDatabase()

    init {
        viewModelScope.launch {
            // First time showing jokes, without potential offense.
            repo.refreshJokes(
                "Christmas",
                blacklistFlags = arrayOf(
                    "nsfw",
                    "religious",
                    "political",
                    "racist",
                    "sexist",
                    "explicit"
                ),
                amount = 10
            )
        }
    }

}
