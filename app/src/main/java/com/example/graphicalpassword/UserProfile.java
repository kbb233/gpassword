package com.example.graphicalpassword;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        initLoginInfobtn();

        // Record login information
        String ipAddress = getIPAddress(true);
        String dateTime = getCurrentDateTime();
        String device = Build.MANUFACTURER + " " + Build.MODEL;
        String os = "Android " + Build.VERSION.RELEASE;
        dbHelper.addLoginInfo(username, ipAddress, dateTime, device, os);
    }

    private void initLoginInfobtn() {
        Button loginInfoButton = findViewById(R.id.Login_Info);
        loginInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginInfoIntent = new Intent(UserProfile.this, LoginInfo.class);
                loginInfoIntent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(loginInfoIntent);
            }
        });
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
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

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

}


