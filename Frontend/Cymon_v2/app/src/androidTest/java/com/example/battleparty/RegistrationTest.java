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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.cymonbattle.R;

import java.util.Random;


@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RegistrationTest {

    private static final int SIMULATED_DELAY_MS = 1500;
    Random rand = new Random();

    @Rule
    public ActivityScenarioRule<RegistrationActivity> registrationRule = new ActivityScenarioRule<>(RegistrationActivity.class);

    @Rule
    public ActivityScenarioRule<SignInActivity> signInRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void registration() {

        int rUserId = rand.nextInt(10000);
        String testUserEmail = "TestEmail" + rUserId;
        String testUserName = "TestName" + rUserId;

        onView(withId(R.id.main_editText_email))
                .perform(typeText(testUserEmail), closeSoftKeyboard());
        onView(withId(R.id.main_editText_password))
                .perform(typeText("testPassword"), closeSoftKeyboard());
        onView(withId(R.id.main_editText_name))
                .perform(typeText(testUserName), closeSoftKeyboard());
        onView(withId(R.id.main_button_register))
                .perform(click());

        //put thread to sleep to allow volley/websocket time to handle
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.main_textView_data))
                .check(matches(withSubstring(testUserEmail)));

    }

}//

