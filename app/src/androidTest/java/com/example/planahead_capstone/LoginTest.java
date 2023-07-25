package com.example.planahead_capstone;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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

import android.view.View;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

    private IdlingResource idlingResource;

    @Before
    public void setUp() {
        // Initialize custom ViewIdlingResource for the main view
        activityScenarioRule.getScenario().onActivity(activity -> {
            View view = activity.getWindow().getDecorView();
            idlingResource = new ViewIdlingResource(view);
            IdlingRegistry.getInstance().register(idlingResource);
        });
    }

    @After
    public void tearDown() {
        // Unregister the custom ViewIdlingResource
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    @Test
    public void testSuccessfulLogin() {
        // Enter valid credentials in the username and password fields
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("admin"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("admin"));

        // Click on the sign-in button
        Espresso.onView(ViewMatchers.withId(R.id.login))
                .perform(ViewActions.click());

        // Check if the HomeActivity is displayed
        Espresso.onView(withId(R.id.viewPager))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLoginFailureWithInvalidCredentials() {
        // Enter invalid credentials in the username and password fields
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("invalid_username"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("invalid_password"));

        // Click on the sign-in button
        Espresso.onView(ViewMatchers.withId(R.id.login))
                .perform(ViewActions.click());

        // Check if the error toast message is displayed
        Espresso.onView(withText("Invalid username or password"))
                .check(matches(isDisplayed()));
    }
}
