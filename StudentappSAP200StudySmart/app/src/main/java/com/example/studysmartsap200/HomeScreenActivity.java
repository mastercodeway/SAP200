package com.example.studysmartsap200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreenActivity extends AppCompatActivity {

    TextView displayUsername;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        displayUsername = findViewById(R.id.welcomeName);
        GridLayout gridLayout = findViewById(R.id.homescreengrid);

        sessionManager = SessionManager.getInstance(getApplicationContext());
        displayUsername.setText(sessionManager.getUsername());

        // Loopa igenom alla barn i GridLayout på hem skärmen och sätter klick listner. Kör kod baserat på id i gridlayouten.
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final View childView = gridLayout.getChildAt(i);

            // Lägg till en klicklyssnare för varje CardView
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Identifiera vilket CardView som klickades baserat på dess id
                    int id = childView.getId();
                    if (id == R.id.forumCardView) {

                        Intent intent = new Intent(getApplicationContext(), ForumCategoryActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.cardView2) {
                        Toast.makeText(HomeScreenActivity.this, "CardView 2 klickades", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.cardView3) {
                        Toast.makeText(HomeScreenActivity.this, "CardView 3 klickades", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.cardView4) {
                        Toast.makeText(HomeScreenActivity.this, "CardView 4 klickades", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomeScreenActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            BottomNavigationView bottomNavigationView = findViewById(R.id.homeScreenBnv);
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

                return false;
            });
        }

    }


}