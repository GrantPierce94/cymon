package com.example.battleparty;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import com.example.cymonbattle.R;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class LeaderboardTest {

    @Rule
    public ActivityScenarioRule<LeaderBoard> activityRule = new ActivityScenarioRule<>(LeaderBoard.class);

    @Test
    public void testEnterScoreButton() {
        Espresso.onView(withId(R.id.main_button_enterScore)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Enter Score button
        // For example, check if a dialog appears or a specific action occurs
    }

    @Test
    public void testGoButton() {
        Espresso.onView(withId(R.id.main_button_go)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Go button
        // For instance, check if a new activity starts or a specific action occurs
    }

    @Test
    public void testAdvanceToNxtButton() {
        Espresso.onView(withId(R.id.main_button_advanceToNxt)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Advance to Next button
    }

    @Test
    public void testShowScoresButton() {
        Espresso.onView(withId(R.id.main_button_showScores)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Show Scores button
    }

    @Test
    public void testEnterNameTextField() {
        Espresso.onView(withId(R.id.main_editText_name)).perform(ViewActions.typeText("John Doe"));
        // Add assertions or verifications for the behavior after entering text in the Name field
    }

    @Test
    public void testEnterScoreTextField() {
        Espresso.onView(withId(R.id.main_editText_score)).perform(ViewActions.typeText("100"));
        // Add assertions or verifications for the behavior after entering text in the Score field
    }

    // Add more test methods as needed for other interactions with buttons or fields
}

