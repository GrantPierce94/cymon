package com.example.battleparty;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;

import com.example.battleparty.WebSockets.WebSocketManager;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class PartyCreationTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<PlayerIDActivity> idActivity = new ActivityScenarioRule<>(PlayerIDActivity.class);

    @Rule
    public ActivityScenarioRule<RegistrationActivity> registrationRule = new ActivityScenarioRule<>(RegistrationActivity.class);

    @Test
    public void createPartyTest() {

        TestingHelper.registrationHelper();
        String playerName = WebSocketManager.getPlayer().getName();

        onView(withId(R.id.btnPartyCreator))
                .perform(click());

        onView(withId(R.id.button2))
                .perform(click());

        onView(withId(R.id.txtPartyName))
                .perform(typeText("testingParty"), closeSoftKeyboard());

        onView(withId(R.id.btnContinue))
                .perform(click());

        onView(withId(R.id.btnCreate))
                .perform(click());

        //allow time for handling
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //check that player signed in is placed in the party
        idActivity.getScenario().onActivity(activity -> {
            String checkStr = activity.getTestingString();

            assertThat(checkStr, containsString(playerName));

        });
    }


}
