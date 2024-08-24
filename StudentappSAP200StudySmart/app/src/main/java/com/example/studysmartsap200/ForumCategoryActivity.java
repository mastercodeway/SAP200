package com.example.studysmartsap200;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class ForumCategoryActivity extends AppCompatActivity {
    private GridView gridViewCategoryForum;
    TextView textView;
    ArrayList<Category> categories = new ArrayList<Category>();

    GridCategoryForumAdapter adapter;
    private DatabaseManager db;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forum_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gridViewCategoryForum = findViewById(R.id.forumCategoryView);
        db = DatabaseManager.getInstance(this);
        sessionManager = SessionManager.getInstance(getApplicationContext());



        Category math = new Category("Matte");
        Category swedish = new Category("Svenska");
        Category programming = new Category("Programmering");
        Category physics = new Category("Fysik");
        Category English = new Category("Engelska");
        Category socialStudies = new Category("Samhällskunskap");
        Category geography  = new Category("Geografi");
        Category biology = new Category("Naturkunskap");

        categories.add(math);
        categories.add(swedish);
        categories.add(programming);
        categories.add(physics);
        categories.add(English);
        categories.add(socialStudies);
        categories.add(geography);
        categories.add(biology);

        adapter = new GridCategoryForumAdapter(this, categories);
        Log.i("NumberOfCategories", "number: " + adapter.getCount());
        gridViewCategoryForum.setAdapter(adapter);
        // bottom navigation
        // https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
       BottomNavigationView bottomNavigationView = findViewById(R.id.forumCategoryBnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.navigation_home)
            {
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
            }
            return false;
        });


    }
}