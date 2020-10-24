package com.hbkapps.noyoupick.genreselection

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.GenreItem

class GenreSelectionAdapter(private var genreList : List<GenreItem>, val selectionListener : GenreSelectionPresenter.GenreSelectionInterface, private val clickListener: (GenreItem) -> Unit)
    : RecyclerView.Adapter<GenreSelectionAdapter.GenreViewHolder>() {

    class GenreViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_selection_button_row, parent, false) as TextView

        return GenreViewHolder(textView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.textView.text = genreList[position].genreName
        holder.textView.setOnClickListener {
            clickListener(genreList[position])
            checkSelected(position, holder)
        }
        checkSelected(position, holder)
    }

    private fun checkSelected(position: Int, holder: GenreViewHolder) {
        if (holder.adapterPosition == position) {
            if (selectionListener.getSelectedGenres().contains(genreList[position])) {
                holder.textView.setTextColor(Color.parseColor("#151515"))
                holder.textView.setBackgroundResource(R.drawable.button_rectangular_filled_background)
                holder.textView.setTypeface(null, Typeface.BOLD)
            } else {
                holder.textView.setTextColor(Color.parseColor("#ffffff"))
                holder.textView.setBackgroundResource(R.drawable.button_rectangular_stroke_background)
                holder.textView.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun getItemCount() = genreList.size

}