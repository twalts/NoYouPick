package com.hbkapps.noyoupick.genreselection

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.GenreItem

class GenreSelectionViewAdapter(private var genreList : ArrayList<GenreItem>, private val clickListener: (GenreItem) -> Unit)
    : RecyclerView.Adapter<GenreSelectionViewAdapter.GenreViewHolder>() {

    class GenreViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {

        fun bind(genre: GenreItem, clickListener: (GenreItem) -> Unit, curPosition: Int) {
            textView.text = genre.genreName
            textView.setOnClickListener {
                genre.isSelected = !genre.isSelected
                clickListener(genre)
                checkSelected(genre,curPosition)
            }
            checkSelected(genre, curPosition)
        }

        private fun checkSelected(genre: GenreItem, curPosition: Int) {
            if (adapterPosition == curPosition) {
                if (genre.isSelected) {
                    textView.setTextColor(Color.parseColor("#151515"))
                    textView.setBackgroundResource(R.drawable.button_rectangular_filled_background)
                    textView.setTypeface(null, Typeface.BOLD)
                } else {
                    textView.setTextColor(Color.parseColor("#ffffff"))
                    textView.setBackgroundResource(R.drawable.button_rectangular_stroke_background)
                    textView.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_selection_button_row, parent, false) as TextView

        return GenreViewHolder(textView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genreList[position], clickListener, position)
    }

    override fun getItemCount() = genreList.size

}