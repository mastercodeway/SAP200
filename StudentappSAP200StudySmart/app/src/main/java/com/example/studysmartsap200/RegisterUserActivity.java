package com.example.studysmartsap200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterUserActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    EditText editTextRegisterUsername;
    EditText editTextRegisterPassword;
    EditText editTextRegisterEmail;
   TextView txtViewRegisterLogin;


    Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        // Deklarerar värde
        editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername);
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        txtViewRegisterLogin = findViewById(R.id.txtViewRegisterLogin);

        registerBtn = findViewById(R.id.button3);
        Intent loginIntent = new Intent(this, MainActivity.class);

        // tar hand om registrering av användare och dess error hantering.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            Context context = getApplicationContext();
            DatabaseManager dbHandler = new DatabaseManager(context);
            @Override
            public void onClick(View v) {
                User user = new User(
                        editTextRegisterUsername.getText().toString(),
                        editTextRegisterPassword.getText().toString(),
                        editTextRegisterEmail.getText().toString());

                if(!validateUserInput(user.getUsername(), user.getEmail(), user.getPassword()))
                    return;

                long userId = dbHandler.addUser(user, RegisterUserActivity.this);
                if(userId != -1) {
                    Toast.makeText(RegisterUserActivity.this, "Du är nu registrerad", Toast.LENGTH_LONG).show();
                }


                startActivity(loginIntent);
            }
        });

        editTextRegisterUsername.setOnFocusChangeListener(this);
        editTextRegisterPassword.setOnFocusChangeListener(this);
        editTextRegisterEmail.setOnFocusChangeListener(this);

        txtViewRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });

    }

    // Event sätter text till tom sträng vid klick och ändra password till password type.
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int viewId = v.getId();
        if (hasFocus) {
            if (viewId == R.id.editTextRegisterUsername) {
                editTextRegisterUsername.setText("");
            } else if (viewId == R.id.editTextRegisterPassword) {
                editTextRegisterPassword.setText("");
                editTextRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (viewId == R.id.editTextRegisterEmail) {
                editTextRegisterEmail.setText("");
            }
        }
    }

// https://www.baeldung.com/regular-expressions-java Kod för att skapa regular expression vid inloggnig.
    private boolean validatePassword(String password){
        return Pattern.compile("^(?=.*[0-9]).{8,}$").matcher(password).matches();
    }
    private boolean validateEmail(String email){
       return Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$").matcher(email).matches();
    }
    private boolean validateUsername(String username){
        return Pattern.compile("^[a-zA-Z0-9]{4,16}$").matcher(username).matches();
    }

    private boolean validateUserInput(String username, String email, String password )
    {
        final String RetryUsernameMsg = "Felaktigt användarnamn";
        final String RetryPasswordMsg = "Felaktigt lösenord";
        final String RetryEmailMsg = "Felaktigt email";

        if(!validateUsername(username))
        {
            editTextRegisterUsername.setError(RetryUsernameMsg);
        }
        if(!validatePassword(password))
        {
            editTextRegisterPassword.setError(RetryPasswordMsg);
        }
        if(!validateEmail(email))
        {
            editTextRegisterEmail.setError(RetryEmailMsg);
        }
        return (validateUsername(username)
                && validatePassword(password)
                && validateEmail(email));
    }
}