package com.tpjad.clienttpjad.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tpjad.clienttpjad.constants.RequestsConstants;
import com.tpjad.clienttpjad.interfaces.CategoryCallbackInterface;
import com.tpjad.clienttpjad.interfaces.RequestCallbackInterface;
import com.tpjad.clienttpjad.mappers.CategoryMapper;
import com.tpjad.clienttpjad.models.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryHelper {

    private static CategoryHelper mInstance;

    private CategoryHelper() {

    }

    public static CategoryHelper getInstance() {
        if (mInstance == null) {
            mInstance = new CategoryHelper();
        }

        return mInstance;
    }

    public void loadCategories(final Context context, final CategoryCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                RequestsConstants.CATEGORIES_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Category> categories = new ArrayList<>(CategoryMapper.convertToModels(response));
                        callback.onLoadComplete(categories);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authToken", LoginHelper.getInstance().getToken());

                return params;
            }
        };

        queue.add(request);
    }

    public void addCategory(final Context context, final Category category, final RequestCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                RequestsConstants.CATEGORY_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onRequestComplete(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onRequestComplete(false);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authToken", LoginHelper.getInstance().getToken());

                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return String.format("{\"name\":\"%1$s\"}", category.getName()).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(request);
    }

    public void deleteCategory(final Context context, final int categoryId, final RequestCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                RequestsConstants.CATEGORY_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onRequestComplete(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        callback.onRequestComplete(false);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authToken", LoginHelper.getInstance().getToken());

                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return String.format("{\"id\":\"%1$s\"}", categoryId).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(request);
    }

    public void updateCategory(final Context context, final Category category, final RequestCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                RequestsConstants.CATEGORY_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onRequestComplete(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onRequestComplete(false);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authToken", LoginHelper.getInstance().getToken());

                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return String.format(
                        "{\"id\":\"%1$s\", \"name\":\"%2$s\"}}",
                        category.getId(),
                        category.getName()
                ).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(request);
    }
}
