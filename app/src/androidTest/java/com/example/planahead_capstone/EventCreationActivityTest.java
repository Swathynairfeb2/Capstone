//package com.example.planahead_capstone;
//import android.database.sqlite.SQLiteDatabase;
//import android.widget.DatePicker;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import androidx.test.espresso.contrib.PickerActions;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//
//import org.hamcrest.Matchers;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.CoreMatchers.not;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//
//
//@RunWith(AndroidJUnit4.class)
//public class EventCreationActivityTest {
//
//    @Rule
//    public ActivityTestRule<EventCreationActivity> activityRule = new ActivityTestRule<>(EventCreationActivity.class);
//
//    @Mock
//    private DatabaseHelper mockDatabaseHelper;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        activityRule.getActivity().databaseHelper = mockDatabaseHelper;
//    }
//
//    @Test
//    public void testEventCreation_Success() {
//        // Mock input data
//        String eventName = "Birthday Party";
//        String eventLocation = "Home";
//        String eventDate = "07/20/2023";
//        String eventTime = "12:00";
//        String eventBudget = "100";
//
//        // Set up the database mock to return a successful event creation
//        when(mockDatabaseHelper.getWritableDatabase()).thenReturn(mock(SQLiteDatabase.class));
//        when(mockDatabaseHelper.getAllCategories()).thenReturn(new ArrayList<>()); // Mock empty categories
//
//        // Perform user actions (enter event details and click "Create Event" button)
//        onView(withId(R.id.editTextEventName)).perform(typeText(eventName), closeSoftKeyboard());
//        onView(withId(R.id.editTextEventLocation)).perform(typeText(eventLocation), closeSoftKeyboard());
//        onView(withId(R.id.textViewEventDate)).perform(click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 7, 20));
//        onView(withId(android.R.id.button1)).perform(click()); // Click OK on DatePicker
//        onView(withId(R.id.textViewEventTime)).perform(click());
//        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 0));
//        onView(withId(android.R.id.button1)).perform(click()); // Click OK on TimePicker
//        onView(withId(R.id.editTextEventBudget)).perform(typeText(eventBudget), closeSoftKeyboard());
//        onView(withId(R.id.buttonCreateEvent)).perform(click());
//
//        // Verify that the event was saved to the database
//        verify(mockDatabaseHelper).insertEvent(
//                eq(eventName),
//                eq(eventLocation),
//                eq(eventDate),
//                eq(eventTime),
//                eq(eventBudget),
//                anyLong() // eventId will be any long value since we don't know it in advance
//        );
//
//        // Verify that a toast message appears indicating successful event creation
//        onView(withText("Event created: " + eventName))
//                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//
//        // Verify that the activity finishes and returns to the previous screen
//        assertTrue(activityRule.getActivity().isFinishing());
//    }
//
//    @Test
//    public void testEventCreation_MissingFields() {
//        // Perform user actions (click "Create Event" button without entering any details)
//        onView(withId(R.id.buttonCreateEvent)).perform(click());
//
//        // Verify that toast messages appear indicating the required fields are missing
//        onView(withText("Please enter event name")).check(matches(isDisplayed()));
//        onView(withText("Please enter event location")).check(matches(isDisplayed()));
//        onView(withText("Please select event date")).check(matches(isDisplayed()));
//        onView(withText("Please select event time")).check(matches(isDisplayed()));
//        onView(withText("Please enter event budget")).check(matches(isDisplayed()));
//
//        // Verify that the activity remains open
//        assertFalse(activityRule.getActivity().isFinishing());
//    }
//}
//
