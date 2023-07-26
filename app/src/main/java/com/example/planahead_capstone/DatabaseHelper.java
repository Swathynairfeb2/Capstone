package com.example.planahead_capstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PlanAhead.db";
    private static final int DATABASE_VERSION = 4; // Increment the version number

    // User table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Events table
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_EVENT_ID = "id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_LOCATION = "event_location";
    private static final String COLUMN_EVENT_DATE = "event_date";
    private static final String COLUMN_EVENT_TIME = "event_time";
    private static final String COLUMN_EVENT_BUDGET = "event_budget";

    // Category table
    private static final String TABLE_CATEGORY = "category";
    private static final String COLUMN_CATEGORY_ID = "cat_id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    // EventCategory table
    private static final String TABLE_EVENT_CATEGORY = "event_category";
    private static final String COLUMN_EVENT_CATEGORY_ID = "id";
    private static final String COLUMN_EVENT_CATEGORY_EVENT_ID = "event_id";
    private static final String COLUMN_EVENT_CATEGORY_CATEGORY_ID = "category_id";
    //todo table
    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_TODO_ID = "id";
    public static final String COLUMN_TODO_NAME = "task_name";
    public static final String COLUMN_TODO_COMPLETED = "completed";
    public static final String COLUMN_EVENTID = "event_Id";

    //guest table
    public static final String TABLE_GUESTS = "guests";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EVID = "event_id";

    // Budget table

    public static final String TABLE_BUDGET = "budget";
    public static final String COLUMN_BUDGET_ID = "id";
    public static final String COLUMN_BUDGET_CATEGORY_NAME = "Budget_category_name";
    public static final String COLUMN_BUDGET_AMOUNT = "amount";
    public static final String COLUMN_BUDGET_EVENT_ID = "eventId";

    //user details table

    public static final String TABLE_USER_DETAILS = "userdetails";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_UEMAIL = "email";
    public static final String COLUMN_UPHONE = "phone";
    public static final String COLUMN_UUSER_ID = "id"; // Foreign key to the users table


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS +
                "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);

        String createEventsTableQuery = "CREATE TABLE " + TABLE_EVENTS +
                "(" + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT, " +
                COLUMN_EVENT_LOCATION + " TEXT, " +
                COLUMN_EVENT_DATE + " TEXT, " +
                COLUMN_EVENT_TIME + " TEXT, " +
                COLUMN_EVENT_BUDGET + " TEXT)";
        db.execSQL(createEventsTableQuery);

        String createCategoryTableQuery = "CREATE TABLE " + TABLE_CATEGORY +
                "(" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT)";
        db.execSQL(createCategoryTableQuery);

        String createEventCategoryTableQuery = "CREATE TABLE " + TABLE_EVENT_CATEGORY +
                "(" + COLUMN_EVENT_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_CATEGORY_EVENT_ID + " INTEGER, " +
                COLUMN_EVENT_CATEGORY_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_EVENT_CATEGORY_EVENT_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + "), " +
                "FOREIGN KEY(" + COLUMN_EVENT_CATEGORY_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(createEventCategoryTableQuery);

        String createBudgetTableQuery = "CREATE TABLE " + TABLE_BUDGET +
                "(" + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BUDGET_CATEGORY_NAME + " TEXT, " +
                COLUMN_BUDGET_AMOUNT + " DOUBLE, " +
                COLUMN_BUDGET_EVENT_ID + " TEXT)";
        db.execSQL(createBudgetTableQuery);

        String createTodoTableQuery = "CREATE TABLE " + TABLE_TODO +
                "(" + COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO_NAME + " TEXT, " +
                COLUMN_TODO_COMPLETED + " INTEGER, " +
                COLUMN_EVENTID + " TEXT)";
        db.execSQL(createTodoTableQuery);

        String CREATE_TABLE_GUESTS = "CREATE TABLE " + TABLE_GUESTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_EVID + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_GUESTS);

        String CREATE_TABLE_USER_DETAILS =
                "CREATE TABLE " + TABLE_USER_DETAILS + " (" +
                        COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_FIRST_NAME + " TEXT," +
                        COLUMN_LAST_NAME + " TEXT," +
                        COLUMN_UEMAIL + " TEXT," +
                        COLUMN_UPHONE + " TEXT," +
                        COLUMN_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_USER_DETAILS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        onCreate(db);
    }

    public String getColumnNameEventName() {
        return COLUMN_EVENT_NAME;
    }

    public String getColumnNameEventLocation() {
        return COLUMN_EVENT_LOCATION;
    }

    public String getColumnNameEventDate() {
        return COLUMN_EVENT_DATE;
    }

    public String getColumnNameEventTime() {
        return COLUMN_EVENT_TIME;
    }

    public String getColumnNameEventBudget() {
        return COLUMN_EVENT_BUDGET;
    }

    public String getTableNameEvents() {
        return TABLE_EVENTS;
    }

    public String getColumnNameEventCategoryEventId() {
        return COLUMN_EVENT_CATEGORY_EVENT_ID;
    }

    public String getColumnNameEventCategoryCategoryId() {
        return COLUMN_EVENT_CATEGORY_CATEGORY_ID;
    }

    public String getTableNameEventCategory() {
        return TABLE_EVENT_CATEGORY;
    }

    public long insertEvent(String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, eventName);
        values.put(COLUMN_EVENT_LOCATION, eventLocation);
        values.put(COLUMN_EVENT_DATE, eventDate);
        values.put(COLUMN_EVENT_TIME, eventTime);
        values.put(COLUMN_EVENT_BUDGET, eventBudget);

        long eventId = db.insert(TABLE_EVENTS, null, values);
        db.close();

        return eventId;
    }

    public void addCategory(String categoryName) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);

        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndexId = cursor.getColumnIndex(COLUMN_CATEGORY_ID);
            int columnIndexName = cursor.getColumnIndex(COLUMN_CATEGORY_NAME);

            do {
                int categoryId = cursor.getInt(columnIndexId);
                String categoryName = cursor.getString(columnIndexName);

                Category category = new Category(categoryId, categoryName);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return categoryList;
    }

    public List<EventDetails> getUpcomingEvents() {
        List<EventDetails> upcomingEvents = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndexEventId = cursor.getColumnIndex(COLUMN_EVENT_ID);
            int columnIndexEventName = cursor.getColumnIndex(COLUMN_EVENT_NAME);
            int columnIndexEventLocation = cursor.getColumnIndex(COLUMN_EVENT_LOCATION);
            int columnIndexEventDate = cursor.getColumnIndex(COLUMN_EVENT_DATE);
            int columnIndexEventTime = cursor.getColumnIndex(COLUMN_EVENT_TIME);
            int columnIndexEventBudget = cursor.getColumnIndex(COLUMN_EVENT_BUDGET);

            do {
                String eventId = cursor.getString(columnIndexEventId);
                String eventName = cursor.getString(columnIndexEventName);
                String eventLocation = cursor.getString(columnIndexEventLocation);
                String eventDate = cursor.getString(columnIndexEventDate);
                String eventTime = cursor.getString(columnIndexEventTime);
                String eventBudget = cursor.getString(columnIndexEventBudget);

                EventDetails event = new EventDetails(eventId, eventName, eventLocation, eventDate, eventTime, eventBudget);
                upcomingEvents.add(event);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return upcomingEvents;
    }




    public void mapEventToCategory(int eventId, int categoryId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_CATEGORY_EVENT_ID, eventId);
        values.put(COLUMN_EVENT_CATEGORY_CATEGORY_ID, categoryId);

        db.insert(TABLE_EVENT_CATEGORY, null, values);
        db.close();
    }

    public EventDetails getEventById(String eventId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, null, COLUMN_EVENT_ID + " = ?",
                new String[]{eventId}, null, null, null);

        EventDetails event = null;

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndexEventName = cursor.getColumnIndex(COLUMN_EVENT_NAME);
            int columnIndexEventLocation = cursor.getColumnIndex(COLUMN_EVENT_LOCATION);
            int columnIndexEventDate = cursor.getColumnIndex(COLUMN_EVENT_DATE);
            int columnIndexEventTime = cursor.getColumnIndex(COLUMN_EVENT_TIME);
            int columnIndexEventBudget = cursor.getColumnIndex(COLUMN_EVENT_BUDGET);

            String eventName = cursor.getString(columnIndexEventName);
            String eventLocation = cursor.getString(columnIndexEventLocation);
            String eventDate = cursor.getString(columnIndexEventDate);
            String eventTime = cursor.getString(columnIndexEventTime);
            String eventBudget = cursor.getString(columnIndexEventBudget);

            event = new EventDetails(eventId, eventName, eventLocation, eventDate, eventTime, eventBudget);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return event;
    }
    public void updateEvent(int eventId, String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, eventName);
        values.put(COLUMN_EVENT_LOCATION, eventLocation);
        values.put(COLUMN_EVENT_DATE, eventDate);
        values.put(COLUMN_EVENT_TIME, eventTime);
        values.put(COLUMN_EVENT_BUDGET, eventBudget);

        db.update(TABLE_EVENTS, values, COLUMN_EVENT_ID + "= ?", new String[]{String.valueOf(eventId)});

        db.close();
    }
    public void deleteEvent(String eventId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_EVENTS, COLUMN_EVENT_ID + " = ?", new String[]{eventId});
        db.close();
    }

    public void updateCategory(int categoryId, String categoryName) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);

        db.update(TABLE_CATEGORY, values, COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)});
        db.close();
    }
    // Method to delete a category from the database
    public void deleteCategory(int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)});
        db.close();
    }
    public long insertTask(String taskName, boolean completed, String eventId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_NAME, taskName);
        values.put(COLUMN_TODO_COMPLETED, completed ? 1 : 0);
        values.put(COLUMN_EVENTID, eventId);
        long taskId = db.insert(TABLE_TODO, null, values);
        db.close();
        return taskId;
    }
    public List<TodoTask> getAllTasks() {
        List<TodoTask> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_TODO_ID, COLUMN_TODO_NAME, COLUMN_TODO_COMPLETED,COLUMN_EVENTID};
        Cursor cursor = db.query(TABLE_TODO, columns, null, null, null, null, null);
        if (cursor != null) {
            int columnIndexId = cursor.getColumnIndex(COLUMN_TODO_ID);
            int columnIndexName = cursor.getColumnIndex(COLUMN_TODO_NAME);
            int columnIndexCompleted = cursor.getColumnIndex(COLUMN_TODO_COMPLETED);
            int columnIndexEventId = cursor.getColumnIndex(COLUMN_EVENTID);
            while (cursor.moveToNext()) {
                long taskId = cursor.getLong(columnIndexId);
                String taskName = cursor.getString(columnIndexName);
                int completedInt = cursor.getInt(columnIndexCompleted);
                boolean completed = (completedInt == 1);
                String eventId = cursor.getString(columnIndexEventId);
                TodoTask task = new TodoTask(taskId, taskName, completed, eventId);
                tasks.add(task);
            }
            cursor.close();
        }
        db.close();
        return tasks;
    }




    public void updateTask(long taskId, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_COMPLETED, completed ? 1 : 0);
        db.update(TABLE_TODO, values, COLUMN_TODO_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    public void deleteTask(long taskId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_TODO_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }
    public long addGuestToDatabase(String guestName, String email, String phone, String eventId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, guestName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EVID, eventId);

        long guestId = db.insert(TABLE_GUESTS, null, values);
        db.close();

        return guestId;
    }

    public long insertBudget(String categoryName, double amount,String eventID) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_CATEGORY_NAME, categoryName);
        values.put(COLUMN_BUDGET_AMOUNT, amount);
        values.put(COLUMN_BUDGET_EVENT_ID,eventID);

        long budgetId = db.insert(TABLE_BUDGET, null, values);
        db.close();

        return budgetId;
    }



    public void updateBudget(int budgetID,String budgetName, double amount) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_CATEGORY_NAME, budgetName);
        db.update(TABLE_BUDGET, values, COLUMN_BUDGET_ID + " = ?", new String[]{String.valueOf(budgetID)});
        values.put(COLUMN_BUDGET_AMOUNT, amount);

        db.update(TABLE_BUDGET, values, COLUMN_BUDGET_ID + " = ?", new String[]{String.valueOf(budgetID)});
        db.close();
    }
    public void deleteBudget(int budgetId) {
        SQLiteDatabase Db = getWritableDatabase();
        String whereClause = DatabaseHelper.COLUMN_BUDGET_ID + " = ?";
        String[] whereArgs = {String.valueOf(budgetId)};

        Db.delete(DatabaseHelper.TABLE_BUDGET, whereClause, whereArgs);

        Db.close();
    }

    public Guest getGuestById(String guestId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PHONE,
                COLUMN_EVID
        };
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {guestId};

        Cursor cursor = db.query(
                TABLE_GUESTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Guest guest = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
            int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int eventIdColumnIndex = cursor.getColumnIndex(COLUMN_EVID);

            String id = cursor.getString(idColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String phone = cursor.getString(phoneColumnIndex);
            String eventId = cursor.getString(eventIdColumnIndex);

            guest = new Guest(id, name, email, phone, eventId);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return guest;
    }
    public void updateGuest(String guestId, String guestName, String email, String phone, String eventId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, guestName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EVID, eventId);

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {guestId};

        db.update(TABLE_GUESTS, values, selection, selectionArgs);

        db.close();
    }
    public void deleteGuest(String guestId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {guestId};
        db.delete(DatabaseHelper.TABLE_GUESTS, selection, selectionArgs);
    }


}