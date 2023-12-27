package com.example.battleparty;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.cymonbattle.R;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SignInTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<SignInActivity> signInRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void signIn() {

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

