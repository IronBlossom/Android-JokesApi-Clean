package ishtiaq.codingchallenge.randomjokes.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import ishtiaq.codingchallenge.randomjokes.R
import ishtiaq.codingchallenge.randomjokes.adapter.JokeAdapter
import ishtiaq.codingchallenge.randomjokes.databinding.BottomsheetJokesAttrBinding
import ishtiaq.codingchallenge.randomjokes.databinding.FragmentJokesListBinding
import ishtiaq.codingchallenge.randomjokes.datasource.JokesRepository
import ishtiaq.codingchallenge.randomjokes.datasource.getDatabase
import ishtiaq.codingchallenge.randomjokes.model.Joke
import ishtiaq.codingchallenge.randomjokes.viewmodel.ApiStatus
import ishtiaq.codingchallenge.randomjokes.viewmodel.JokesListViewModel
import kotlinx.coroutines.launch

class JokesListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentJokesListBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    private val viewModel: JokesListViewModel by viewModels()
    private lateinit var repo: JokesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = JokesRepository(getDatabase(activity?.application!!))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJokesListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rcvJokes.adapter = JokeAdapter { joke -> showJokeAttr(joke) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.swipeRefreshLayout.setOnRefreshListener(this)

        repo.status.observe(this) { apiStatus ->
            onApiStatusReceived(apiStatus)
        }
    }

    private fun showJokeAttr(joke: Joke) {
        val popup = BottomSheetDialog(this.requireContext())
        val btmShtBinding = DataBindingUtil.inflate<BottomsheetJokesAttrBinding>(
            layoutInflater,
            R.layout.bottomsheet_jokes_attr,
            null,
            false
        )
        btmShtBinding.joke=joke
        popup.setContentView(btmShtBinding.root)
        popup.show()
    }

    private fun onApiStatusReceived(apiStatus: ApiStatus?) {
        when (apiStatus) {
            ApiStatus.LOADING -> binding.swipeRefreshLayout.isRefreshing = true
            ApiStatus.DONE -> binding.swipeRefreshLayout.isRefreshing = false

            ApiStatus.ERROR -> {
                binding.swipeRefreshLayout.isRefreshing = false
                Toast.makeText(activity, "Check internet connection", Toast.LENGTH_SHORT).show()
            }
            null -> Log.wtf(
                "WIN MILLION DOLLER!!!",
                "visit: https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions"
            )
        }
    }

    override fun onRefresh() {
        lifecycleScope.launch {
            repo.refreshJokes("Any")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            JokesListFragment()
    }


}