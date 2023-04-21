package com.example.graphicalpassword;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LoginInfo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserDataBaseHelper dbHelper;
    private List<LogInfo> loginInfoList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_info);
        String username = getIntent().getStringExtra("username");

        recyclerView = findViewById(R.id.login_info_recycle);
        dbHelper = new UserDataBaseHelper(this);

        try {
            loginInfoList = dbHelper.getAllLoginInfo(username);
        } catch (Exception e) {
            Log.e("LoginInfoActivity", "Error fetching login information: " + e.getMessage());
        }

        // Set up the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        LoginInfoAdapter adapter = new LoginInfoAdapter(this, loginInfoList);
        recyclerView.setAdapter(adapter);
    }
}
