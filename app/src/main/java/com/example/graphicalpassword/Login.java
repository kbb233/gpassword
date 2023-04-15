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

public class Login extends AppCompatActivity {
    private EditText userInput;
    private Button checkUsernameLogin;
    private UserDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userInput = findViewById(R.id.user_input);
        checkUsernameLogin = findViewById(R.id.check_username_login);

        dbHelper = new UserDataBaseHelper(this);

        checkUsernameLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userInput.getText().toString().trim();

                if (isUsernameExist(username)) {
                    Intent intent = new Intent(Login.this, Login_Password.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Username does not exist. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isUsernameExist(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {UserDataBaseHelper.COLUMN_USERNAME};
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

        boolean usernameExists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return usernameExists;
    }
}