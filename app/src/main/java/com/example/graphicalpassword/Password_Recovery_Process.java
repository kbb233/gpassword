package com.example.graphicalpassword;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Password_Recovery_Process extends AppCompatActivity {
    private EditText resetTp1Text, resetTp2Text;
    private TextView resetGpText;
    private Button resetGraphicalBtn, resetBtn;
    private UserDataBaseHelper userDatabaseHelper;
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_recovery_process);

        resetTp1Text = findViewById(R.id.reset_tp1_text);
        resetTp2Text = findViewById(R.id.reset_tp2_text);
        resetGpText = findViewById(R.id.reset_gp_text);
        resetGraphicalBtn = findViewById(R.id.reset_graphical_btn);
        resetBtn = findViewById(R.id.reset_btn);

        userDatabaseHelper = new UserDataBaseHelper(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        // Implement the logic to set the graphical password
        ActivityResultLauncher<Intent> graphicalPasswordActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String graphicalPassword = data.getStringExtra(GraphicalPasswordActivity_Recovery.EXTRA_GRAPHICAL_PASSWORD);
                            resetGpText.setText(graphicalPassword);
                        }
                    }
                });
        resetGraphicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Password_Recovery_Process.this, GraphicalPasswordActivity_Recovery.class);
                graphicalPasswordActivityResultLauncher.launch(intent);
            }
        });


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = resetTp1Text.getText().toString().trim();
                String password2 = resetTp2Text.getText().toString().trim();
                String graphicalPassword = resetGpText.getText().toString().trim();

                if ((password1.isEmpty()&&password2.isEmpty()) && graphicalPassword.isEmpty()) {
                    Toast.makeText(Password_Recovery_Process.this, "Please enter your new password.", Toast.LENGTH_SHORT).show();
                } else if (!password1.equals(password2)) {
                    Toast.makeText(Password_Recovery_Process.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    if(password1.isEmpty()){
                        password1 = "null";
                    }
                    if(graphicalPassword.isEmpty()){
                        graphicalPassword = "null";
                    }
                    updateUserPassword(username, password1, graphicalPassword);
                    Toast.makeText(Password_Recovery_Process.this, "Password reset successfully.", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(Password_Recovery_Process.this, Login.class);
                    startActivity(loginIntent);
                }
            }
        });
    }

    private void updateUserPassword(String username, String newPassword, String newGraphicalPassword) {
        Cursor user = userDatabaseHelper.getUserByUsername(username);
        if (user != null && user.moveToFirst()) {
            userDatabaseHelper.updateUser(username, newPassword, newGraphicalPassword);
        }
    }

}
