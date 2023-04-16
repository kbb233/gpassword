package com.example.graphicalpassword;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateProfile extends AppCompatActivity {
    private EditText profileText;
    private UserDataBaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);

        profileText = findViewById(R.id.profile_text);
        dbHelper = new UserDataBaseHelper(this);

        Button createAccountButton = findViewById(R.id.create_acc_btn);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String profile = profileText.getText().toString();

                if (!profile.isEmpty()) {
                    // Insert data into the database
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(UserDataBaseHelper.COLUMN_USERNAME, getIntent().getStringExtra("username"));
                    values.put(UserDataBaseHelper.COLUMN_TEXT_PASSWORD, getIntent().getStringExtra("textPassword"));
                    values.put(UserDataBaseHelper.COLUMN_GRAPH_PASSWORD, getIntent().getStringExtra("graphicalPassword"));
                    values.put(UserDataBaseHelper.COLUMN_QUESTION, getIntent().getStringExtra("securityQuestion"));
                    values.put(UserDataBaseHelper.COLUMN_ANSWER, getIntent().getStringExtra("securityAnswer"));
                    values.put(UserDataBaseHelper.COLUMN_PROFILE, profile);
                    long newRowId = db.insert(UserDataBaseHelper.TABLE_USERS, null, values);

                    if (newRowId != -1) {
                        // Send data to UserProfile.class
                        Intent userProfileIntent = new Intent(CreateProfile.this, UserProfile.class);
                        userProfileIntent.putExtra("username", getIntent().getStringExtra("username"));
                        startActivity(userProfileIntent);
                    } else {
                        Toast.makeText(CreateProfile.this, "Error saving profile", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // Profile text is empty
                    Toast.makeText(CreateProfile.this, "Please enter your profile information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
