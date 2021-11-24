package ishtiaq.codingchallenge.randomjokes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.*
import ishtiaq.codingchallenge.randomjokes.databinding.ActivityMainBinding
import ishtiaq.codingchallenge.randomjokes.view.JokesListFragment
import ishtiaq.codingchallenge.randomjokes.view.OpenCountFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var _fragmentContainer: FragmentContainerView
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
        _fragmentContainer = _binding.fragmentContainer

    }


    override fun onPostResume() {
        super.onPostResume()
        if (RandJokesApp.appOpened) {

            makeFullScreen(this) // splash screen mode
            changeFragment(OpenCountFragment.newInstance(), _fragmentContainer.id)
            RandJokesApp.appOpened = false

            lifecycleScope.launch {
                delay(3000) // As Requested
                makeDefaultScreen(this@MainActivity)
                changeFragment(JokesListFragment.newInstance(), _fragmentContainer.id)
            }

        } else {
            makeDefaultScreen(this)
            changeFragment(JokesListFragment.newInstance(), _fragmentContainer.id)
        }
    }


}