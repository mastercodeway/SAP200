package com.example.studysmartsap200;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

public class ThreadForumActivty extends AppCompatActivity {
    private ArrayList<ForumThread> threadList = new ArrayList<>();
    DatabaseManager db = DatabaseManager.getInstance(this);
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thread_forum_activty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        int categoryId = intent.getIntExtra("categoryID", convertCategoryStringToID(category));
        threadList = db.getAllThreads(categoryId);
        sessionManager = SessionManager.getInstance(getApplicationContext());
        for(ForumThread thread : threadList)
        {
            Log.i("mums", thread.toString());
        }
        // android development for beginner, John Horton. För att skapa mina trådar genom att skapa dynamiska cardview.
        RecyclerView recyclerView = findViewById(R.id.threadForumRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ThreadRecycleAdapter adapter = new ThreadRecycleAdapter(this, threadList);
        adapter.setOnItemClickListener(new ThreadRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int threadID = threadList.get(position).getId();
                String threadTitle = threadList.get(position).getThreadTitle();
                Log.i("mums","TrådInlägg: " + threadID);
                Intent postIntent = new Intent(ThreadForumActivty.this, PostActivity.class);
                postIntent.putExtra("threadId", threadID);
                postIntent.putExtra("threadTitle", threadTitle);
                startActivity(postIntent);
            }
        });
        recyclerView.setAdapter(adapter);

        Button addThreadBtn = findViewById(R.id.addThreadBtn);
        addThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skapa en Intent för att starta den andra aktiviteten
                Intent createThreadActivity = new Intent(ThreadForumActivty.this, CreateThreadActivity.class);
                createThreadActivity.putExtra("category", category);

                startActivity(createThreadActivity);
            }
        });
        // https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        BottomNavigationView bottomNavigationView = findViewById(R.id.threadForumBnv);
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
                Intent categoryForum = new Intent(this, ForumCategoryActivity.class);
                startActivity(categoryForum);
            }
            return false;
        });
    }
    private static int convertCategoryStringToID(String category){
        if (category != null) {
            switch(category) {
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
            return -1; // or handle the case when category is null
        }
    }


}