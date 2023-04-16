package com.example.graphicalpassword;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Password_Recovery extends AppCompatActivity {
    private UserDataBaseHelper dbHelper;
    private TextView securityQuestionDisplay;
    private EditText securityAnswerInput;
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_recovery);

        dbHelper = new UserDataBaseHelper(this);

        securityQuestionDisplay = findViewById(R.id.security_question_display);
        securityAnswerInput = findViewById(R.id.security_answer_input);
        Button checkSecurityBtn = findViewById(R.id.check_securoty_btn);
        Button backLoginBtn = findViewById(R.id.back_login_btn);

        // Get the username from the previous activity
        username = getIntent().getStringExtra("username");

        // Display the security question
        String securityQuestion = getSecurityQuestion(username);
        securityQuestionDisplay.setText(securityQuestion);
        checkSecurityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = securityAnswerInput.getText().toString();
                if (checkSecurityAnswer(username, userAnswer)) {
                    Intent recoveryPasswordIntent = new Intent(Password_Recovery.this, Password_Recovery_Process.class);
                    recoveryPasswordIntent.putExtra("username", username);
                    startActivity(recoveryPasswordIntent);
                } else {
                    Toast.makeText(Password_Recovery.this, "Incorrect answer. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Go back to login page
        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backLoginIntent = new Intent(Password_Recovery.this, Login.class);
                startActivity(backLoginIntent);
            }
        });
    }

    private String getSecurityQuestion(String username) {
        Cursor cursor = dbHelper.getUserByUsername(username);

        if (cursor.moveToFirst()) {
            String question = cursor.getString(cursor.getColumnIndex(UserDataBaseHelper.COLUMN_QUESTION));
            cursor.close();
            return question;
        } else {
            cursor.close();
            return "Error: Question not found";
        }
    }

    private boolean checkSecurityAnswer(String username, String userAnswer) {
        Cursor cursor = dbHelper.getUserByUsername(username);

        if (cursor.moveToFirst()) {
            String storedAnswer = cursor.getString(cursor.getColumnIndex(UserDataBaseHelper.COLUMN_ANSWER));
            cursor.close();
            return userAnswer.equals(storedAnswer);
        } else {
            cursor.close();
            return false;
        }
    }
}
