package ishtiaq.codingchallenge.randomjokes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ishtiaq.codingchallenge.randomjokes.databinding.RowJokeItemBinding
import ishtiaq.codingchallenge.randomjokes.model.Joke

val itemLayoutParam: FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )


class JokeAdapter(private val onItemClicked: (Joke) -> Unit) :
    ListAdapter<Joke, JokeAdapter.TheViewHolder>(DiffCallback) {
    init {
        itemLayoutParam.height = 280
        itemLayoutParam.bottomMargin= 32
    }

    class TheViewHolder(private var binding: RowJokeItemBinding, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.layoutParams = itemLayoutParam
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(joke: Joke) {
            binding.joke = joke
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.jokeId == newItem.jokeId
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.setup == newItem.setup
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheViewHolder {


        val viewHolder = RowJokeItemBinding.inflate(
            LayoutInflater.from(parent.context)
        )

        return TheViewHolder(viewHolder) {
            getItem(it)?.let(onItemClicked)
        }
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val joke = getItem(position)
        holder.bind(joke)
    }
}