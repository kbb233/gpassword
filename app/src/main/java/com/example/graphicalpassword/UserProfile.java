package com.example.graphicalpassword;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView profileTextView;
    private UserDataBaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        usernameTextView = findViewById(R.id.username_text);
        profileTextView = findViewById(R.id.user_profile_text);

        dbHelper = new UserDataBaseHelper(this);

        // Get the username from the intent extra
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Display the username
        usernameTextView.setText(username);

        // Query the profile from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {UserDataBaseHelper.COLUMN_PROFILE};
        String selection = UserDataBaseHelper.COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                UserDataBaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Display the profile
        if (cursor.moveToFirst()) {
                String profile = cursor.getString(cursor.getColumnIndex(UserDataBaseHelper.COLUMN_PROFILE));
                profileTextView.setText(profile);
        }

        cursor.close();
        db.close();
        initLogoutButton();
    }

    private void initLogoutButton() {
        Button logoutButton = findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });
    }

}
