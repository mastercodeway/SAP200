package com.example.studysmartsap200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PasswordAuthentication;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 TextView registertextView;
 EditText editTextusername;
 EditText editTextPassword;
 Button loginBtn;
 TextView txtViewIncorrectInput;
 private DatabaseManager db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String dbName = "user_database";

// Radera databasen
/*
        if (this.deleteDatabase(dbName)) {
            Log.d("DatabaseDeletion", "Databasen " + dbName + " har raderats.");
        } else {
            Log.e("DatabaseDeletion", "Misslyckades med att radera databasen " + dbName);
        }
*/

        editTextusername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        registertextView = findViewById(R.id.registerTextView);
        txtViewIncorrectInput = findViewById(R.id.incorrectLogin);
        Intent homeScreenIntent = new Intent(this, HomeScreenActivity.class);
        Intent intent = new Intent(this, RegisterUserActivity.class);

        loginBtn = findViewById(R.id.loginBtn);
        Context context = getApplicationContext();
        DatabaseManager db = DatabaseManager.getInstance(this);

        // Se alla användare i databasen.

        List<User> userList = db.getAllUsers();
        for(User user : userList){
            Log.i("mums", user.toString());
        }


        registertextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        // Kollar om användarnamn och lösenord överensstämmer med inloggning uppgifter när användaren klickar på knapp.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextusername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!db.verifyLogin(username, password))
                {
                    Toast.makeText(getApplicationContext(), "Inloggningsuppgifterna är fel försök igen", Toast.LENGTH_LONG).show();
                }
                else
                {
                    User user = db.getUser(username);
                    String loggedInUser = user.getUsername();
                    SessionManager.getInstance(context).saveUsername(loggedInUser);

                    startActivity(homeScreenIntent);
                }

            }
        });
        // Tar bort text ur username fältet
        editTextusername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextusername.setText("");
                }
            }
        });
        // Tar bort text ur password fältet
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextPassword.setText("");
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });











    }

}