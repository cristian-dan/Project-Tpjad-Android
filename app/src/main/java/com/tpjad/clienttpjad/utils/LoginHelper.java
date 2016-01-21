package com.tpjad.clienttpjad.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tpjad.clienttpjad.constants.ModelsConstants;
import com.tpjad.clienttpjad.constants.RequestsConstants;
import com.tpjad.clienttpjad.interfaces.LoginCallbackInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginHelper {

    private static LoginHelper mInstance;
    private int mId;
    private String mUsername;
    private String mToken;

    private LoginHelper() {

    }

    public static LoginHelper getInstance() {
        if (mInstance == null) {
            mInstance = new LoginHelper();
        }

        return mInstance;
    }

    public void loginRequest(final Context context, final String username, final String password, final LoginCallbackInterface
            callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                RequestsConstants.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            JSONObject modelJson = resp.getJSONObject(ModelsConstants.MODEL);
                            mId = modelJson.getInt(ModelsConstants.USER_ID);
                            mToken = modelJson.getString(ModelsConstants.USER_TOKEN);
                            mUsername = username;

                            callback.onLoginResponse(true);
                        } catch (JSONException ex) {
                            callback.onLoginResponse(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onLoginResponse(false);
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return String.format(
                        "{\"username\":\"%1$s\",\"password\":\"%2$s\"}",
                        username,
                        password
                ).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(request);
    }

    public int getId() { return this.mId; }

    public String getUsername() {
        return this.mUsername;
    }

    public String getToken() { return this.mToken; }
}
