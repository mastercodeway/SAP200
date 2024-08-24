package com.example.studysmartsap200;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    private ArrayList<Post> posts = new ArrayList<>();
    SessionManager sessionManager;
    DatabaseManager db = DatabaseManager.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_post);
        EditText editTextContent = findViewById(R.id.editTextAddPost);
        Button addPostBtn = findViewById(R.id.btnAddPost);
        sessionManager = SessionManager.getInstance(getApplicationContext());
        String username = sessionManager.getUsername();
        Intent intent = getIntent();
        String threadTitle = intent.getStringExtra("threadTitle");
        int threadId = intent.getIntExtra("threadId", -1);
        posts = db.getAllPost(threadId);

        if(posts.size() > 0)
        {
            posts.get(0).setPostTitle(threadTitle);
        }
// Lägger till post.
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                post.setThreadRef(threadId);
                post.setPostContent(editTextContent.getText().toString());
                post.setUserName(username);
                post.setFavorite(false);
                db.addPost(post);
                post.setDate(db.getDateTime());
                posts.add(post);
                editTextContent.setText("");
                populatePosts();
            }
        });
        populatePosts();
// https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        BottomNavigationView bottomNavigationView = findViewById(R.id.postForumBNV);
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
    private void populatePosts()
    {
        RecyclerView recyclerView = findViewById(R.id.postForumRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PostRecycleAdapter adapter = new PostRecycleAdapter(this, posts);
        recyclerView.setAdapter(adapter);
    }
}