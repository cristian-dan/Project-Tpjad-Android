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
import com.tpjad.clienttpjad.interfaces.ProductCallbackInterface;
import com.tpjad.clienttpjad.interfaces.RequestCallbackInterface;
import com.tpjad.clienttpjad.mappers.ProductMapper;
import com.tpjad.clienttpjad.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductHelper {

    private static ProductHelper mInstance;

    private ProductHelper() {

    }

    public static ProductHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ProductHelper();
        }

        return mInstance;
    }

    public void loadProducts(final Context context, final ProductCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                RequestsConstants.PRODUCTS_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Product> products = new ArrayList<>(ProductMapper.convertToModels(response));
                        callback.onLoadComplete(products);
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

    public void addProduct(final Context context, final Product product, final RequestCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                RequestsConstants.PRODUCT_ADD,
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
                        "{\"name\":\"%1$s\", \"description\":\"%2$s\", \"stock\":\"%3$d\", \"category\":{\"id\":\"%4$d\"}}",
                        product.getName(),
                        product.getDescription(),
                        product.getStock(),
                        product.getCategory().getId()
                ).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(request);
    }

    public void deleteProduct(final Context context, final int productId, final RequestCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                RequestsConstants.PRODUCT_DELETE,
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
                return String.format("{\"id\":\"%1$s\"}", productId).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(request);
    }

    public void updateProduct(final Context context, final Product product, final RequestCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                RequestsConstants.PRODUCT_UPDATE,
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
                        "{\"id\":\"%1$s\", \"name\":\"%2$s\", \"description\":\"%3$s\", \"stock\":\"%4$d\", \"category\":{\"id\":\"%5$d\"}}",
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStock(),
                        product.getCategory().getId()
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
