package com.example.graphicalpassword;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private UserDataBaseHelper userDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Spinner spinnerMethod=findViewById(R.id.spinner_loginMethod);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.login_method, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMethod.setAdapter(adapter);
        initCheckUsername();
        DatabaseConnection db = new DatabaseConnection();
        db.connect();
    }

    private void initCheckUsername() {
        Button checkbtn = findViewById(R.id.checkUserName);
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check username
                String username = findViewById(R.id.username_input).toString();
                String query = "SELECT username FROM user WHERE username = ?";

            }
        });
    }
}
