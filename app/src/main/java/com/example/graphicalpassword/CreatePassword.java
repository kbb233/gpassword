package com.example.graphicalpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePassword extends AppCompatActivity {
    private TextView displayGraphicalPassword;
    private EditText textPassword1;
    private EditText textPassword2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password);
        // Display the username passed from the Register class
        Bundle extras = getIntent().getExtras();
        TextView displayUsername = findViewById(R.id.display_username);
        String name = extras.getString("username");
        displayUsername.setText(name);
        initConfirmButton();

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

    private void initConfirmButton() {
        Button confirmButton = findViewById(R.id.set_password_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String name = extras.getString("username");
                textPassword1 = findViewById(R.id.textpassword1);
                String tp1 = textPassword1.getText().toString();
                textPassword2 = findViewById(R.id.textpassword2);
                String tp2 = textPassword2.getText().toString();
                String gp = displayGraphicalPassword.getText().toString();
                if (!tp1.isEmpty() && tp1.equals(tp2) || (tp1.isEmpty() && tp2.isEmpty() && !gp.isEmpty())) {
                    // Validation passed, send data to SecuritySetup.class
                    Intent securitySetupIntent = new Intent(CreatePassword.this, SecuritySetUp.class);

                    securitySetupIntent.putExtra("username", name);
                    securitySetupIntent.putExtra("textPassword", tp1.isEmpty() ? "null" : tp1);
                    securitySetupIntent.putExtra("graphicalPassword", gp.isEmpty() ? "null" : gp);

                    startActivity(securitySetupIntent);
                } else {
                    if (!textPassword1.equals(textPassword2)) {
                        // Text passwords do not match
                        Toast.makeText(CreatePassword.this, "Text passwords do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        // Text passwords and graphical password are empty
                        Toast.makeText(CreatePassword.this, "Please provide a text password or graphical password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
