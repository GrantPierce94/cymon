package com.example.battleparty;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.cymonbattle.R;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PartyChatTest {

    private ActivityScenario<MainScreen> mainScreenScenario;

    @Before
    public void setUp() {
        // Launch the MainScreen activity
        mainScreenScenario = ActivityScenario.launch(MainScreen.class);

        // Perform necessary actions in MainScreen to navigate to PartyChat
        // For instance, if there's a button click to navigate to PartyChat, perform that action here
        Espresso.onView(ViewMatchers.withId(R.id.btnPartyChat))
                .perform(ViewActions.click());
    }

    @Test
    public void testSendMessage() {
        // Type in the username EditText with ID et1
        Espresso.onView(ViewMatchers.withId(R.id.et1))
                .perform(ViewActions.typeText("toUsername"), ViewActions.closeSoftKeyboard());

        // Click the Connect button with ID bt1
        Espresso.onView(ViewMatchers.withId(R.id.bt1))
                .perform(ViewActions.click());

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Type "hello" into the chat input EditText with ID message_input
        Espresso.onView(ViewMatchers.withId(R.id.message_input))
                .perform(ViewActions.typeText("hello"), ViewActions.closeSoftKeyboard());

        // Click the send button with ID bt2
        Espresso.onView(ViewMatchers.withId(R.id.bt2))
                .perform(ViewActions.click());

        // Wait for 2 seconds after sending the message
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add assertions or verifications based on expected behavior
        // For example, check if the message is sent successfully
    }
    // Other test methods
}



