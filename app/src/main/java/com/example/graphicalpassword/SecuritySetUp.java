package com.example.graphicalpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SecuritySetUp extends AppCompatActivity {
    private EditText securityQuestionText;
    private EditText securityAnswerText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_security);

        securityQuestionText = findViewById(R.id.security_question_text);
        securityAnswerText = findViewById(R.id.security_answer_text);
        Button createAccountButton = findViewById(R.id.create_security_btn);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String securityQuestion = securityQuestionText.getText().toString();
                String securityAnswer = securityAnswerText.getText().toString();

                if (!securityQuestion.isEmpty() && !securityAnswer.isEmpty()) {
                    // Send data to CreateProfile.class
                    Intent createProfileIntent = new Intent(SecuritySetUp.this, CreateProfile.class);

                    createProfileIntent.putExtra("username", getIntent().getStringExtra("username"));
                    createProfileIntent.putExtra("textPassword", getIntent().getStringExtra("textPassword"));
                    createProfileIntent.putExtra("graphicalPassword", getIntent().getStringExtra("graphicalPassword"));
                    createProfileIntent.putExtra("securityQuestion", securityQuestion);
                    createProfileIntent.putExtra("securityAnswer", securityAnswer);

                    startActivity(createProfileIntent);
                } else {
                    // Security question or answer is empty
                    Toast.makeText(SecuritySetUp.this, "Please enter both security question and answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
