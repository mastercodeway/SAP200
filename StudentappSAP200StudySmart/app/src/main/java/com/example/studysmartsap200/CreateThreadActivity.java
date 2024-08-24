package com.example.studysmartsap200;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CreateThreadActivity extends AppCompatActivity {
    SessionManager sessionManager;
    EditText editTextTitle;
    EditText editTextPostDescription;

    int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_thread);

        DatabaseManager db = DatabaseManager.getInstance(this);
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        Button addPostBtn = findViewById(R.id.addPostButton);
        sessionManager = SessionManager.getInstance(getApplicationContext());
        String username = sessionManager.getUsername();
        editTextTitle = findViewById(R.id.editTextPostTitel);
        editTextPostDescription = findViewById(R.id.editTextPostDescription);
        categoryID = convertCategoryStringToID(category);

        // spara tråd och post i databasen
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long threadId = saveThread(username, db);
                if(threadId == -1) return;

                long postId = addPost(threadId, username, db);

                Intent intentForumThread = new Intent(CreateThreadActivity.this, ThreadForumActivty.class);
                intentForumThread.putExtra("categoryID", categoryID);
                startActivity(intentForumThread);

            }
        });
        // https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        // Hämtar navigation meny. Kör olika kod baserat på item id.
        BottomNavigationView bottomNavigationView = findViewById(R.id.createThreadBnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.navigation_home)
            {
                Intent homeScreenIntent = new Intent(this, HomeScreenActivity.class);
                startActivity(homeScreenIntent);
            }
            if(id == R.id.notification)
            {
                Toast.makeText(this, "Jag har fått updateringar", Toast.LENGTH_LONG).show();
            }
            if(id == R.id.profile)
            {
                sessionManager.logOut();
                Intent loginscreen = new Intent(this, MainActivity.class);
                startActivity(loginscreen);
            }
            if(id == R.id.backArrow)
            {
                Intent threadForum = new Intent(this, ThreadForumActivty.class);
                startActivity(threadForum);
            }
            return false;
        });

    }
// sparar ned data i tråd objekt
    private long saveThread(String username, @NonNull DatabaseManager db)
    {
        ForumThread thread = new ForumThread();
        thread.setThreadTitle(editTextTitle.getText().toString());
        thread.setUsername(username);
        thread.setCategoryRefId(categoryID);
        thread.setFavorite(false);
        thread.setReplies(0);
        User user = db.getUser(username);
        thread.setUserRefID(user.getId());
        long threadId = db.addThread(thread);
        if(threadId==-1) {
            Log.e("DATABASE_FAIL", "Thread not saved correctly");
            return threadId;
        }
        Log.i("mums", "post added" + thread.toString());
        return threadId;
    }

    // lägger till post
    private long addPost(long threadId, String username, DatabaseManager db)
    {
        Post post = new Post();
        post.setThreadRef((int)threadId);
        post.setPostContent(editTextPostDescription.getText().toString());
        post.setUserName(username);
        post.setFavorite(false);
        Log.i("mums", "post added" + post.toString());
        return db.addPost(post);

    }
// converterar category som trycks på i forumkategori och gör om den till kategori id för att hålla reda på vilken kategori tråden ska lagra.
    private static int convertCategoryStringToID(String category) {
        if (category != null) {
            switch (category) {
                case "math":
                    return 1;
                case "Svenska":
                    return 2;
                case "Programmering":
                    return 3;
                case "Fysik":
                    return 4;
                case "Engelska":
                    return 5;
                case "Samhällskunskap":
                    return 6;
                case "Geografi":
                    return 7;
                case "Naturkunskap":
                    return 8;
                default:
                    return -1;
            }
        } else {
            return -1;
        }
    }
}