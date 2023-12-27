package com.example.battleparty;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

/*
Class that simulates Signing in or registering to help later tests be more concise
 */
public class TestingHelper {

    private static final int SIMULATED_DELAY_MS = 1500;

    public static void registrationHelper() {

        Random rand = new Random();
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

        onView(withId(R.id.main_button_register))
                .perform(click());

    }

    public void signInHelper() {

//        SignInActivity signInMock = mock(SignInActivity.class);
        String userEmail = "email1";
        String userPass = "password1";

        onView(withId(R.id.email))
                .perform(typeText(userEmail), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText(userPass), closeSoftKeyboard());

        //delay for server communications
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


}
