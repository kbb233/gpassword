package com.example.graphicalpassword;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private UserDataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbHelper = new UserDataBaseHelper(this);
        initCheckUsername();

    }

    private void initCheckUsername() {
        Button checkbtn = findViewById(R.id.checkUserName);
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check username
                EditText username = findViewById(R.id.username_input);
                String name = username.getText().toString();
                if (usernameExists(name)) {
                    Toast.makeText(Register.this, "Username already exists. Please use another one.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Register.this, CreatePassword.class);
                    intent.putExtra("username", name);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean usernameExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = UserDataBaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(UserDataBaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
