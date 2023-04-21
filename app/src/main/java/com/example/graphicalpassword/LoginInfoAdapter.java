package com.example.graphicalpassword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LoginInfoAdapter extends RecyclerView.Adapter<LoginInfoAdapter.LoginInfoViewHolder> {

    private List<LogInfo> loginInfoList;
    private Context context;

    public LoginInfoAdapter(Context context, List<LogInfo> loginInfoList) {
        this.context = context;
        this.loginInfoList = loginInfoList;
    }

    @NonNull
    @Override
    public LoginInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.login_info_item, parent, false);
        return new LoginInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoginInfoViewHolder holder, int position) {
        LogInfo loginInfo = loginInfoList.get(position);
        holder.usernameTextView.setText(loginInfo.getUsername());
        holder.ipAddressTextView.setText(loginInfo.getIpAddress());
        holder.dateTimeTextView.setText(loginInfo.getLoginDate());
        holder.deviceTextView.setText(loginInfo.getDevice());
        holder.osTextView.setText(loginInfo.getOperatingSystem());
    }

    @Override
    public int getItemCount() {
        return loginInfoList.size();
    }

    public static class LoginInfoViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTextView;
        TextView ipAddressTextView;
        TextView dateTimeTextView;
        TextView deviceTextView;
        TextView osTextView;

        public LoginInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username_item);
            ipAddressTextView = itemView.findViewById(R.id.ip_address_item);
            dateTimeTextView = itemView.findViewById(R.id.date_item);
            deviceTextView = itemView.findViewById(R.id.device_item);
            osTextView = itemView.findViewById(R.id.os_item);
        }
    }
}
