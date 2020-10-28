package com.hbkapps.noyoupick.movietvlist

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_movie_tv_display.*
import timber.log.Timber
import javax.inject.Inject

class MovieTVListActivity : BaseActivity(), CardStackListener {

    @Inject
    lateinit var presenter : MovieTVListPresenter
    lateinit var layoutManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tv_display)
        application.applicationComponent.inject(this)
        presenter.loadMediaListFromRepo(mediaListListener)
    }

    private val mediaListListener: MovieTVListPresenter.MediaListListener = object : MovieTVListPresenter.MediaListListener {
        override fun loadMediaList(movieList: List<Movie>, tvList: List<TV>) {
            setLayoutManagerOptions()
            setUpLikeButtons()

            val adapter = MovieTVListAdapter(movieList, tvList)
            userSelectedMediaCv.layoutManager = layoutManager
            userSelectedMediaCv.adapter = adapter
        }
    }

    private fun setLayoutManagerOptions() {
        val rewindSetting = RewindAnimationSetting.Builder()
            .setDirection(Direction.Top)
            .setDuration(200)
            .setInterpolator(DecelerateInterpolator())
            .build()

        layoutManager = CardStackLayoutManager(applicationContext, this@MovieTVListActivity)
        layoutManager.apply {
            setDirections(arrayListOf(Direction.Right, Direction.Left))
            setCanScrollHorizontal(true)
            setVisibleCount(5)
            setStackFrom(StackFrom.Bottom)
            setTranslationInterval(5f)
            setRewindAnimationSetting(rewindSetting)
            setCanScrollVertical(false)
            setMaxDegree(90f)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        }
    }

    private fun setUpLikeButtons() {
        testDislike.setOnClickListener {
            val dislikeAnimation = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(200)
                .setInterpolator(DecelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(dislikeAnimation)
            userSelectedMediaCv.swipe()
        }

        testLike.setOnClickListener {
            val likeAnimation = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(200)
                .setInterpolator(DecelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(likeAnimation)
            userSelectedMediaCv.swipe()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
        presenter.clearMoviesListFromSelection()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        //test function to show data is being passed
        Timber.e(direction?.name)
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {
        //test function to show data is being passed
        Timber.e("${presenter.getCurMediaItemForTesting(position)}")
    }
}