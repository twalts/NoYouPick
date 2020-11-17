package com.hbkapps.noyoupick.landing


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.hbkapps.noyoupick.R
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class LandingActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityScenarioRule(LandingActivity::class.java)

    @Test
    fun nextButtonIsHighlighted_When_Movie_Button_isClicked() {
        val movieButton = onView(
                allOf(withId(R.id.btnPickMovie),
                        isDisplayed()))
        movieButton.perform(click())

        val nextButton = onView(
                allOf(withId(R.id.btnNext),
                        isDisplayed()))
        //check if next button tag has been set to 'selected button' drawable ID
        nextButton.check(matches(withTagValue(`is`(R.drawable.button_rectangular_filled_background))))
    }

    @Test
    fun movieButtonIsUnhighlighted_When_TV_Button_isClicked() {
        val movieButton = onView(
                allOf(withId(R.id.btnPickMovie),
                        isDisplayed()))
        movieButton.perform(click())

        val tvButton = onView(
                allOf(withId(R.id.btnPickTelevision),
                        isDisplayed()))
        tvButton.perform(click())

        //check if next button tag has been set to 'unselected button' drawable ID
        movieButton.check(matches(withTagValue(`is`(R.drawable.button_rectangular_stroke_background))))
    }
}