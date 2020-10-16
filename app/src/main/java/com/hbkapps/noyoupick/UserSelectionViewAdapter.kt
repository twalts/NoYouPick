package com.hbkapps.noyoupick

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserSelectionViewAdapter(private val genreList : ArrayList<String>, val clickListener: (TextView) -> Unit) : RecyclerView.Adapter<UserSelectionViewAdapter.GenreViewHolder>() {

    class GenreViewHolder(val textView : TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(genre: String, clickListener: (TextView) -> Unit) {
            textView.text = genre
            textView.setOnClickListener { clickListener(textView) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_selection_button_row, parent, false) as TextView

        return GenreViewHolder(textView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genreList[position], clickListener)
    }

    override fun getItemCount() = genreList.size

}