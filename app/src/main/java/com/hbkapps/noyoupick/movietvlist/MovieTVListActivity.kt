package com.hbkapps.noyoupick.movietvlist

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Cast
import com.hbkapps.noyoupick.model.Crew
import com.hbkapps.noyoupick.model.Media
import com.hbkapps.noyoupick.repository.TmdbRepository
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_movie_tv_display.*
import java.util.Stack
import javax.inject.Inject

class MovieTVListActivity : BaseActivity(), CardStackListener {

    @Inject
    lateinit var presenter : MovieTVListPresenter
    lateinit var layoutManager: CardStackLayoutManager

    var currStackPos = 0
    var userSelections = Stack<Pair<Media, Direction>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tv_display)
        application.applicationComponent.inject(this)
        presenter.loadMediaListFromRepo(mediaListListener)
    }

    private val mediaListListener: MovieTVListPresenter.MediaListListener = object : MovieTVListPresenter.MediaListListener {
        override fun loadMediaList(mediaList: List<Media>) {
            setLayoutManagerOptions()
            setUpButtons()

            val adapter = MovieTVListAdapter(mediaList, presenter.tmdbRepository)
            userSelectedMediaCv.layoutManager = layoutManager
            userSelectedMediaCv.adapter = adapter
        }
    }

    private fun setLayoutManagerOptions() {
        layoutManager = CardStackLayoutManager(applicationContext, this@MovieTVListActivity)
        layoutManager.apply {
            setDirections(arrayListOf(Direction.Right, Direction.Left))
            setCanScrollHorizontal(true)
            setVisibleCount(4)
            setStackFrom(StackFrom.Top)
            setTranslationInterval(8f)
            setScaleInterval(.90f)
            setCanScrollVertical(false)
            setMaxDegree(0f)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        }
    }

    private fun setUpButtons() {
        dislikeButton.setOnClickListener {
            val dislikeAnimation = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(600)
                .setInterpolator(DecelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(dislikeAnimation)
            userSelectedMediaCv.swipe()
        }

        likeButton.setOnClickListener {
            val likeAnimation = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(600)
                .setInterpolator(DecelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(likeAnimation)
            userSelectedMediaCv.swipe()
        }

        rewindButton.setOnClickListener {
            if (userSelections.isNotEmpty()) {
                val rewindAnimation = RewindAnimationSetting.Builder()
                        .setDirection(userSelections.peek().second)
                        .setDuration(200)
                        .setInterpolator(DecelerateInterpolator())
                        .build()
                layoutManager.setRewindAnimationSetting(rewindAnimation)
                userSelectedMediaCv.rewind()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
        presenter.clearMediaListFromSelection()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        if (direction != null) {
            userSelections.push(Pair(presenter.getMediaList()[currStackPos], direction))
        }

        if (currStackPos == presenter.getMediaList().size - 1) {
            var sb = StringBuilder()
            for (pair in userSelections) {
                if (pair.second == Direction.Right) {
                    sb.append("${pair.first.getMediaTitle()} \n")
                }
            }
            Toast.makeText(this, "you liked: \n $sb", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCardRewound() {
        userSelections.pop()
    }

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {
        currStackPos = position
    }
}