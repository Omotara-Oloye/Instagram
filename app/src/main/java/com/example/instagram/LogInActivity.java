package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LogInActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button logInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = findViewById(R.id.username_et);
        passwordInput = findViewById(R.id.password_et);
        logInButton = findViewById(R.id.login_btn);
        signUpButton = findViewById(R.id.signUp_btn);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            Log.d("LoginActivity", "Login successful");
            final Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
            startActivity(intent);

        }


        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                logIn(username, password);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                user.setUsername(username);
                user.setPassword(password);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(LogInActivity.this, "Welcome to Instagram", Toast.LENGTH_LONG).show();
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                        }
                    }
                });
                final Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void logIn(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Log.d("LoginActivity", "Login successful");
                    final Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
