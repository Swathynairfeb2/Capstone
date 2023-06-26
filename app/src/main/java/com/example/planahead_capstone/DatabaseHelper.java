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
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

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
    private static final String TABLE_TODO = "todo";
    private static final String COLUMN_TODO_ID = "id";
    private static final String COLUMN_TODO_NAME = "task_name";
    private static final String COLUMN_TODO_COMPLETED = "completed";

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

        String createTodoTableQuery = "CREATE TABLE " + TABLE_TODO +
                "(" + COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO_NAME + " TEXT, " +
                COLUMN_TODO_COMPLETED + " INTEGER)";
        db.execSQL(createTodoTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public long insertTask(String taskName, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_NAME, taskName);
        values.put(COLUMN_TODO_COMPLETED, completed ? 1 : 0);
        long taskId = db.insert(TABLE_TODO, null, values);
        db.close();
        return taskId;
    }

    public List<TodoTask> getAllTasks() {
        List<TodoTask> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_TODO_ID, COLUMN_TODO_NAME, COLUMN_TODO_COMPLETED};
        Cursor cursor = db.query(TABLE_TODO, columns, null, null, null, null, null);
        if (cursor != null) {
            int columnIndexId = cursor.getColumnIndex(COLUMN_TODO_ID);
            int columnIndexName = cursor.getColumnIndex(COLUMN_TODO_NAME);
            int columnIndexCompleted = cursor.getColumnIndex(COLUMN_TODO_COMPLETED);
            while (cursor.moveToNext()) {
                long taskId = cursor.getLong(columnIndexId);
                String taskName = cursor.getString(columnIndexName);
                int completedInt = cursor.getInt(columnIndexCompleted);
                boolean completed = (completedInt == 1);
                TodoTask task = new TodoTask(taskId, taskName, completed);
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
    public void addCategory(String categoryName) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);

        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

}
