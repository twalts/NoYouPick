package com.hbkapps.noyoupick.genreselection

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import kotlinx.android.synthetic.main.activity_genre_selection.*
import javax.inject.Inject

class GenreSelectionActivity : BaseActivity() {

    @Inject
    lateinit var presenter : GenreSelectionPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager

    private var mGenreList : ArrayList<GenreItem> = ArrayList()
    private var mSelectedGenreList : ArrayList<GenreItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_selection)
        application.applicationComponent.inject(this)
        setUpRecyclerView()

        btnSubmitGenreChoice.setOnClickListener {
            presenter.checkIfUserCanSubmit(mSelectedGenreList, genreSelectionInterface)
        }

    }

    private val genreSelectionInterface: GenreSelectionPresenter.GenreSelectionInterface = object : GenreSelectionPresenter.GenreSelectionInterface {
        override fun setSubmitButtonHighlighted() {
            btnSubmitGenreChoice.setBackgroundColor(ContextCompat.getColor(this@GenreSelectionActivity,
                R.color.unselected_submit_button_background
            ))
            btnSubmitGenreChoice.setTextColor(ContextCompat.getColor(this@GenreSelectionActivity,
                R.color.unselected_submit_button_text
            ))
        }

        override fun setSubmitButtonUnhighlighted() {
            btnSubmitGenreChoice.setTextColor(ContextCompat.getColor(this@GenreSelectionActivity, R.color.off_black))
            btnSubmitGenreChoice.background = ContextCompat.getDrawable(this@GenreSelectionActivity,
                R.drawable.button_rectangular_filled_background
            )
        }

        override fun displayInfoToastAfterSubmit() {
            val mediaType = when (intent.getIntExtra("MEDIA_TYPE", 0)) {
                Constants.MEDIA_TYPE_MOVIE -> "Movie"
                Constants.MEDIA_TYPE_TV -> "TV Show"
                Constants.MEDIA_TYPE_BOTH -> "Both"
                else -> "ERROR"
            }
            Toast.makeText(
                this@GenreSelectionActivity,
                "Media type is $mediaType, list of genres is: $mSelectedGenreList",
                Toast.LENGTH_SHORT
            ).show()
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
        mSelectedGenreList = presenter.addOrRemoveGenreItemFromList(genreItem, mSelectedGenreList)
        presenter.setSubmitButtonHighlightedOrUnHighlighted(mSelectedGenreList, genreSelectionInterface)
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