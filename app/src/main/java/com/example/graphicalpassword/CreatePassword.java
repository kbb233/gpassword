package com.example.graphicalpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePassword extends AppCompatActivity {
    private TextView displayGraphicalPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password);
        // Display the username passed from the Register class
        Bundle extras = getIntent().getExtras();
        TextView displayUsername = findViewById(R.id.display_username);
        String name = extras.getString("username");
        displayUsername.setText(name);

        displayGraphicalPassword = findViewById(R.id.display_gp);
        Button setGraphicalPasswordBtn = findViewById(R.id.set_graphical_btn);
        ActivityResultLauncher<Intent> graphicalPasswordActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String graphicalPassword = data.getStringExtra(GraphicalPasswordActivity.EXTRA_GRAPHICAL_PASSWORD);
                            displayGraphicalPassword.setText(graphicalPassword);
                        }
                    }
                });

        setGraphicalPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePassword.this, GraphicalPasswordActivity.class);
                graphicalPasswordActivityResultLauncher.launch(intent);
            }
        });

    }



}
