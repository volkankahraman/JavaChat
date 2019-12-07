package com.example.javachat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login,register;


    FirebaseUser firebaseUser;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setEnabled(true);
        register.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                startActivity(new Intent(StartActivity.this, LoginActivity.class));

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.setEnabled(false);
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));

            }
        });
    }
}
