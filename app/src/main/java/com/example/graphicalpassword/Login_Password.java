package com.example.graphicalpassword;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Login_Password extends AppCompatActivity {
    private UserDataBaseHelper dbHelper;
    private Button setGP;
    private Button passwordRecovery;
    private Button enterBtn;
    private EditText textPasswordLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password);

        // Get username from Login.class
        Bundle extras = getIntent().getExtras();
        TextView displayUsername = findViewById(R.id.username_login);
        String name = extras.getString("username");
        displayUsername.setText(name);

        //set up database
        dbHelper = new UserDataBaseHelper(this);

        //intend to graphical password
        setGP = findViewById(R.id.set_GP);
        TextView displayGP = findViewById(R.id.GP_display);
        ActivityResultLauncher<Intent> graphicalPasswordActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String graphicalPassword = data.getStringExtra(GraphicalPasswordActivity_Login.EXTRA_GRAPHICAL_PASSWORD);
                            displayGP.setText(graphicalPassword);
                        }
                    }
                });
        setGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Password.this, GraphicalPasswordActivity_Login.class);
                graphicalPasswordActivityResultLauncher.launch(intent);
            }
        });

        //password recovery
        passwordRecovery = findViewById(R.id.password_recovery);
        passwordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recoveryIntent = new Intent(Login_Password.this, Password_Recovery.class);
                recoveryIntent.putExtra("username", name);
                startActivity(recoveryIntent);
            }
        });

        //check password
        enterBtn = findViewById(R.id.enter_btn);
        textPasswordLogin = findViewById(R.id.text_password_login);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textPassword = textPasswordLogin.getText().toString().trim();
                if(textPassword.isEmpty()){
                    textPassword = "null";
                }
                String graphicalPassword = displayGP.getText().toString().trim();
                if(graphicalPassword.isEmpty()){
                    graphicalPassword = "null";
                }
                if(textPassword.equals("null") && graphicalPassword.equals("null")){
                    Toast.makeText(Login_Password.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                }
                if (validatePasswords(name, textPassword, graphicalPassword)) {
                    Intent userProfileIntent = new Intent(Login_Password.this, UserProfile.class);
                    userProfileIntent.putExtra("username", name);
                    startActivity(userProfileIntent);
                } else {
                    Toast.makeText(Login_Password.this, "Incorrect password(s). Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatePasswords(String username, String textPassword, String graphicalPassword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {UserDataBaseHelper.COLUMN_TEXT_PASSWORD, UserDataBaseHelper.COLUMN_GRAPH_PASSWORD};
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

        if (cursor.moveToFirst()) {
            String storedTextPassword = cursor.getString(cursor.getColumnIndex(UserDataBaseHelper.COLUMN_TEXT_PASSWORD));
            String storedGraphPassword = cursor.getString(cursor.getColumnIndex(UserDataBaseHelper.COLUMN_GRAPH_PASSWORD));
            cursor.close();
            return textPassword.equals(storedTextPassword) && graphicalPassword.equals(storedGraphPassword);
        } else {
            cursor.close();
            return false;
        }
    }

}
