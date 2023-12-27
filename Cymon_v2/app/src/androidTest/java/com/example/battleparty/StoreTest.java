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
public class StoreTest {

    @Rule
    public ActivityScenarioRule<Store> activityScenarioRule = new ActivityScenarioRule<>(Store.class);

    @Test
    public void testItemClick() {
        Espresso.onView(withId(R.id.btnItem1)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking Item 1
    }

    @Test
    public void testItem2Click() {
        Espresso.onView(withId(R.id.btnItem2)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking Item 2
    }

    @Test
    public void testItem3Click() {
        Espresso.onView(withId(R.id.btnItem3)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking Item 3
    }

    @Test
    public void testItem4Click() {
        Espresso.onView(withId(R.id.btnItem4)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking Item 4
    }

    @Test
    public void testItem5Click() {
        Espresso.onView(withId(R.id.btnItem5)).perform(ViewActions.click());
        // Add assertions or verifications for the behavior after clicking Item 5
    }

    // Add more test methods as needed for other interactions with buttons
}

