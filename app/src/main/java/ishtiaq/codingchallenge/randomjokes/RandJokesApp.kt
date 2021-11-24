package ishtiaq.codingchallenge.randomjokes

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class RandJokesApp : Application(), LifecycleEventObserver {

    override fun onCreate() {
        super.onCreate()

        appPreferences = getSharedPreferences(RandJokesApp::class.qualifiedName, MODE_PRIVATE)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        if (event == Lifecycle.Event.ON_START) {
            var tempTimes = appPreferences?.getLong(TIMES_APP_OPENED.first, TIMES_APP_OPENED.second)
            appPreferences.edit {
                tempTimes++
                putLong(TIMES_APP_OPENED.first, tempTimes)
                putBoolean(IS_APP_OPENED.first, true)
            }
        }

    }

    companion object {
        private lateinit var appPreferences: SharedPreferences
        private val TIMES_APP_OPENED = Pair("app_open_counter", 0L)
        private val IS_APP_OPENED = Pair("app_opened", true)

        val openCount: Long
            get() = appPreferences?.getLong(TIMES_APP_OPENED.first, TIMES_APP_OPENED.second)
        var appOpened: Boolean
            get() = appPreferences?.getBoolean(IS_APP_OPENED.first, IS_APP_OPENED.second)
            set(value) = appPreferences.edit {
                putBoolean(IS_APP_OPENED.first, value)
            }
    }

}

