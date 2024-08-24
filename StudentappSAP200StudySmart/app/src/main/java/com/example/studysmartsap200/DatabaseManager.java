package com.example.studysmartsap200;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

// Horton, J .(2021). Android programming for beginners. 3. Uppl. Birmingham: Packt Publishing. använt över
// större delen av databasmanager för skapa databas och skapa CRUD funktioner
public class DatabaseManager extends SQLiteOpenHelper {
    private static DatabaseManager instance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user_database";

    // ====================================================================================== TABLES
    private static final String TABLE_USER = "user";
    private static final String TABLE_THREAD = "thread";

    private static final String TABLE_POST = "post";

    // ======================================================================================== USER
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    // ==================================================================================== CATEGORY
    private static final String CATEGORY_ID = "id";

    // ====================================================================================== THREAD
    private static final String THREAD_ID = "thread_id";
    private static final String THREAD_TITLE ="thread_title";
    private static final String THREAD_USERNAME = "thread_username";
    private static final String THREAD_FAVORITE = "thread_favorite";
    private static final String THREAD_DATE = "thread_date";
    private static final String THREAD_REPLIES = "thread_replies";

    // ======================================================================================== POST
    private static final String POST_ID = "post_id";
    private static final String POST_USER = "post_user";
    private static final String POST_CONTENT = "post_content";
    private static final String POST_DATE = "post_date";
    private static final String POST_FAVORITE = "post_favorite";

    private static final String CREATE_TABLE_THREAD = "CREATE TABLE " + TABLE_THREAD +
            "(" + THREAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CATEGORY_ID + " INTEGER,"
            + THREAD_TITLE + " TEXT,"
            + THREAD_DATE + " TEXT,"
            + THREAD_FAVORITE + " INTEGER,"
            + THREAD_REPLIES + " INTEGER,"
            + THREAD_USERNAME + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "))";

    private static final String CREATE_TABLE_POST = "CREATE TABLE " + TABLE_POST +
            "(" + POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + THREAD_ID + " INTEGER,"
            + POST_CONTENT + " TEXT,"
            + POST_USER + " TEXT,"
            + POST_DATE + " TEXT,"
            + POST_FAVORITE + " INTEGER,"
            + "FOREIGN KEY(" + THREAD_ID + ") REFERENCES " + TABLE_THREAD + "(" + THREAD_ID + "))";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_EMAIL + " TEXT" + ")";
// Samma database instance över alla aktiviteter.
    public static synchronized DatabaseManager getInstance(Context context) {


        if (instance == null) {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
// Skapar user table, table thread och table post
    @Override
    public void onCreate(SQLiteDatabase db) {
       

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_THREAD);
        db.execSQL(CREATE_TABLE_POST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THREAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        onCreate(db);
    }
// Lägger till data för forums tråd i databas
    public long addThread(ForumThread thread) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,thread.getUserRefID() );
        values.put(THREAD_USERNAME,thread.getUser());
        values.put(THREAD_FAVORITE,thread.getFavorite());
        values.put(THREAD_REPLIES,thread.getReplies());
        values.put(THREAD_TITLE, thread.getThreadTitle());
        values.put(THREAD_DATE, getDateTime());
        values.put(CATEGORY_ID,thread.getCategoryRefId() );
        long id = db.insert(TABLE_THREAD, null, values);
        db.close();
        return id;
    }
    //Lägger till post
    public long addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THREAD_ID, post.getThreadRef());
        values.put(POST_CONTENT,post.getPostContent());
        values.put(POST_USER, post.getUserName());
        values.put(POST_DATE, getDateTime());
        values.put(POST_FAVORITE, post.isFavorite());
       long id = db.insert(TABLE_POST, null, values);
        db.close();
        return id;
    }
    // hämtar alla post baserat på thread id returnerar en lista med Post
    public ArrayList<Post> getAllPost(int threadId) {
        ArrayList<Post> posts = new ArrayList<Post>();
        String selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE " + THREAD_ID + " = ? ORDER BY " + POST_DATE +  " ASC, " + POST_FAVORITE;


        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(threadId)});
        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.setPostID(Integer.parseInt(cursor.getString(0)));
                post.setThreadRef(Integer.parseInt(cursor.getString(1)));
                post.setPostContent(cursor.getString(2));
                post.setUserName(cursor.getString(3));
                post.setDate(cursor.getString(4));
                int favoriteInt = cursor.getInt(5);
                boolean isFavorite = (favoriteInt == 1);
                post.setFavorite(isFavorite);

                posts.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return posts;
    }
    // Hämtar alla posts baserat på thread id returnerar en lista med Post
    public ArrayList<ForumThread> getAllThreads(int categoryID) {
        ArrayList<ForumThread> threads = new ArrayList<ForumThread>();
        String selectQuery = "SELECT * FROM " + TABLE_THREAD + " WHERE " + CATEGORY_ID + " = ? ORDER BY " + THREAD_DATE +  " DESC, " + THREAD_FAVORITE;


        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(categoryID)});
        if (cursor.moveToFirst()) {
            do {
                ForumThread thread = new ForumThread();
                thread.setId(Integer.parseInt(cursor.getString(0)));
                thread.setCategoryRefId(Integer.parseInt(cursor.getString(1)));
                thread.setThreadTitle(cursor.getString(2));
                thread.setDate(cursor.getString(3));
                thread.setFavorite((Objects.equals(cursor.getString(4), "0")));
                thread.setReplies(Integer.parseInt(cursor.getString(5)));
                thread.setUsername(cursor.getString(6));
                threads.add(thread);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return threads;
    }
    // Sätt datum och tid när thread och post skapas
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    // Lägger till användare
    public long addUser(User user, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(isUsernameExists(user.getUsername())) {
            Toast.makeText(context, "Användarnamnet är upptaget försök igen", Toast.LENGTH_SHORT).show();
            db.close();
            return -1;
        }
        if(isEmailExists(user.getEmail())) {
            Toast.makeText(context, "E-postadressen är upptaget försök igen", Toast.LENGTH_SHORT).show();
            db.close();
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_EMAIL, user.getEmail());
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        return id;
    }

// hämtar användare från databasen
    @SuppressLint("Range")
    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            cursor.close();
        }
        db.close();
        return user;
    }
    // kollar om användarnamn existerar
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?",
                new String[]{username}, null, null, null, null);

        boolean exists = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        return exists;
    }
    // Kollar om email existerar.
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_EMAIL},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }

        return exists;
    }
    // Tar bort användare
    public boolean deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(TABLE_USER,  COLUMN_USERNAME+ "=?", new String[]{username});
        db.close();
        return rowsAffected > 0;
    }
    // hämtar alla users i databasen.  Används bara för att kolla data vid test.
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

// För inloggning kollar om användarnamn och lösenord matchar input.
    public boolean verifyLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean loggedIn = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return loggedIn;
    }
    // Tar bort alla användare i databasen.
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}