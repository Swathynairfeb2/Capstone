package com.example.planahead_capstone;

import android.content.Context;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {

    private Context context;
    private IdlingResource idlingResource;

    @Rule
    public ActivityScenarioRule<SignUp> activityScenarioRule = new ActivityScenarioRule<>(SignUp.class);

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Additional setup if needed

        // Initialize custom ViewIdlingResource for the main view
        activityScenarioRule.getScenario().onActivity(activity -> {
            View view = activity.getWindow().getDecorView();
            idlingResource = new ViewIdlingResource(view);
            IdlingRegistry.getInstance().register(idlingResource);
        });
    }


    @After
    public void tearDown() {
        // Clean up any test data if needed

        // Unregister the custom ViewIdlingResource
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    @Test
    public void testRegisterUserWithMatchingPasswords() {
        // Enter valid data in username, password, and confirm password fields
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("testuser"));

        // Enter password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("testpassword"));

        // Enter confirm password (matching)
        Espresso.onView(ViewMatchers.withId(R.id.repassword))
                .perform(ViewActions.typeText("testpassword"));

        // Click on the sign-up button
        Espresso.onView(ViewMatchers.withId(R.id.signup))
                .perform(ViewActions.click());

        // Check if the success toast message is displayed
        Espresso.onView(withText("Registered successfully"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterUserWithMismatchedPasswords() {
        // Enter valid data in username, password, and mismatched confirm password fields
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("testuser"));

        // Enter password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("testpassword"));

        // Enter confirm password (mismatched)
        Espresso.onView(ViewMatchers.withId(R.id.repassword))
                .perform(ViewActions.typeText("mismatchedpassword"));

        // Click on the sign-up button
        Espresso.onView(ViewMatchers.withId(R.id.signup))
                .perform(ViewActions.click());

        // Check if the error toast message is displayed
        Espresso.onView(withText("Registration failed"))
                .check(matches(isDisplayed()));
    }
}
