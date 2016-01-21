package com.tpjad.clienttpjad.mappers;

import com.tpjad.clienttpjad.models.Category;
import com.tpjad.clienttpjad.models.Product;
import com.tpjad.clienttpjad.constants.ModelsConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static List<Product> convertToModels(String response) {
        List<Product> products = new ArrayList<>();

        try {
            JSONObject resp = new JSONObject(response);
            JSONArray jsonProducts = resp.getJSONArray(ModelsConstants.MODEL);

            for (int i = 0; i < jsonProducts.length(); i++) {
                JSONObject jsonProduct = jsonProducts.getJSONObject(i);
                JSONObject jsonCategory = jsonProduct.optJSONObject(ModelsConstants.PRODUCT_CATEGORY);
                Category category = new Category(jsonCategory.getInt(ModelsConstants.CATEGORY_ID), jsonCategory.getString(ModelsConstants.CATEGORY_NAME));
                Product product = new Product(
                        jsonProduct.getInt(ModelsConstants.PRODUCT_ID),
                        jsonProduct.getInt(ModelsConstants.PRODUCT_STOCK),
                        jsonProduct.getString(ModelsConstants.PRODUCT_NAME),
                        jsonProduct.getString(ModelsConstants.PRODUCT_DESCRIPTION),
                        category
                );
                products.add(product);
            }
        } catch (JSONException ex) {

        }

        return products;
    }
}
