package com.tpjad.clienttpjad.utils;

public class LoginHelper {
    private static LoginHelper mInstance;
    private String mUsername;

    private LoginHelper() {

    }

    public static LoginHelper getInstance() {
        if (mInstance == null) {
            mInstance = new LoginHelper();
        }

        return mInstance;
    }

    public boolean loginRequest(String username, String password) {
        this.mUsername = username;

        return true;
    }

    public String getUsername() {
        return this.mUsername;
    }
}
