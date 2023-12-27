package com.example.battleparty;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
public class MainScreenTest {

    @Rule
    public ActivityScenarioRule<MainScreen> activityRule = new ActivityScenarioRule<>(MainScreen.class);

    @Test
    public void testActivePartyButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnActiveParty)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Active Party button
    }

    @Test
    public void testLeaderboardsButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnLeaderboards)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Leaderboards button
    }

    @Test
    public void testPartyCreatorButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnPartyCreator)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Party Creator button
    }

    @Test
    public void testFriendsButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnFriends)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Friends button
    }

    @Test
    public void testStoreButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnStore)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Store button
    }

    @Test
    public void testPartyChatButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnPartyChat)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Party Chat button
    }

    @Test
    public void testBackButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnBack)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking the Back button
    }
}

