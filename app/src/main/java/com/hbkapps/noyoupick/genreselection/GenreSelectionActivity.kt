package com.hbkapps.noyoupick.genreselection

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.GenreItem
import kotlinx.android.synthetic.main.activity_genre_selection.*

class GenreSelectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager

    private var mGenreList : ArrayList<GenreItem> = ArrayList()
    private var mSelectedGenreList : ArrayList<GenreItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_selection)

        setUpRecyclerView()

        btnSubmitGenreChoice.setOnClickListener {
            if (mSelectedGenreList.isNotEmpty()) {
                val mediaType = when (intent.getIntExtra("MEDIA_TYPE", 0)) {
                    Constants.MEDIA_TYPE_MOVIE -> "Movie"
                    Constants.MEDIA_TYPE_TV -> "TV Show"
                    Constants.MEDIA_TYPE_BOTH -> "Both"
                    else -> "ERROR"
                }

                Toast.makeText(this, "Media type is $mediaType, list of genres is: $mSelectedGenreList", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setUpRecyclerView() {
        mGenreList = setUpGenreList()
        viewManager = LinearLayoutManager(this)
        viewAdapter = GenreSelectionViewAdapter(mGenreList) { genre: GenreItem ->
            genreClicked(genre)
        }

        recyclerView = rvGenreChoices.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun genreClicked(genreItem: GenreItem) {
        if (genreItem.isSelected) {
            mSelectedGenreList.add(genreItem)
        } else {
            mSelectedGenreList.remove(genreItem)
        }

        if (mSelectedGenreList.isEmpty()) {
            btnSubmitGenreChoice.setBackgroundColor(ContextCompat.getColor(this,
                R.color.unselected_submit_button_background
            ))
            btnSubmitGenreChoice.setTextColor(ContextCompat.getColor(this,
                R.color.unselected_submit_button_text
            ))
        } else {
            btnSubmitGenreChoice.setTextColor(ContextCompat.getColor(this, R.color.off_black))
            btnSubmitGenreChoice.background = ContextCompat.getDrawable(this,
                R.drawable.button_rectangular_filled_background
            )
        }
    }

    private fun setUpGenreList() : ArrayList<GenreItem> {
        val genreList = ArrayList<GenreItem>()
        genreList.add(GenreItem(10759, "Action & Adventure", false))
        genreList.add(GenreItem(16, "Animation", false))
        genreList.add(GenreItem(35, "Comedy", false))
        genreList.add(GenreItem(80, "Crime", false))
        genreList.add(GenreItem(99, "Documentary", false))
        genreList.add(GenreItem(18, "Drama", false))
        genreList.add(GenreItem(10751, "Family", false))
        genreList.add(GenreItem(10762, "Kids", false))
        genreList.add(GenreItem(9648, "Mystery", false))
        genreList.add(GenreItem(10763, "News", false))
        genreList.add(GenreItem(10764, "Reality", false))
        genreList.add(GenreItem(10765, "Sci-fi and Fantasy", false))
        genreList.add(GenreItem(10768, "War & Politics", false))
        genreList.add(GenreItem(37, "Western", false))

        return genreList
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
    }
}