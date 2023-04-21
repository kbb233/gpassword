package com.example.graphicalpassword;

public class LogInfo {
    private int id;
    private String username;
    private String ipAddress;
    private String loginDate;

    private String device;
    private String operatingSystem;

    public LogInfo(int id, String username, String ipAddress, String loginDate, String device, String operatingSystem) {
        this.id = id;
        this.username = username;
        this.ipAddress = ipAddress;
        this.loginDate = loginDate;
        this.device = device;
        this.operatingSystem = operatingSystem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }


    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
