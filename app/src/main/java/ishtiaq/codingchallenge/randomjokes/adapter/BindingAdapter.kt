package ishtiaq.codingchallenge.randomjokes.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ishtiaq.codingchallenge.randomjokes.model.Joke


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Joke>?) {
    val adapter = recyclerView.adapter as JokeAdapter
    adapter.submitList(data)
}
