package ishtiaq.codingchallenge.randomjokes.datasource

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.*
import ishtiaq.codingchallenge.randomjokes.model.Joke

@Dao
interface JokeDao {
    @Query("select * from Joke")
    fun getJokes(): LiveData<List<Joke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // now will never happen since cleaning the table before inserting
    suspend fun insertAll(jokes: List<Joke>)

    @Query("delete from Joke")
    suspend fun nukeTableData()
}

@Database(entities = [Joke::class], version = 1)

abstract class JokesDatabase : RoomDatabase() {
    abstract val jokeDao: JokeDao
}

private lateinit var INSTANCE: JokesDatabase

fun getDatabase(application: Application): JokesDatabase {
    synchronized(JokesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                application.applicationContext,
                JokesDatabase::class.java,
                "jokes.db"
            ).build()
        }
    }

    return INSTANCE
}
