package com.allysonjeronimo.muvis.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.extensions.load
import com.allysonjeronimo.muvis.model.db.entity.Movie

class MovieListAdapter(
    private val movies:List<Movie>,
    private val onClick: (movie:Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onClick(movie)
        }
    }

    override fun getItemCount():Int = movies.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageCover = itemView.findViewById<ImageView>(R.id.image_poster)

        fun bind(movie: Movie){
            movie.posterPath?.let {
                imageCover.load("https://image.tmdb.org/t/p/w185/$it")
            }
        }
    }
}