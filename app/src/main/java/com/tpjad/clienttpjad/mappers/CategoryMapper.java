package com.tpjad.clienttpjad.mappers;

import com.tpjad.clienttpjad.models.Category;
import com.tpjad.clienttpjad.constants.ModelsConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static List<Category> convertToModels(String response) {
        List<Category> categories = new ArrayList<>();

        try {
            JSONObject resp = new JSONObject(response);
            JSONArray jsonCategories = resp.getJSONArray(ModelsConstants.MODEL);

            for (int i = 0; i < jsonCategories.length(); i++) {
                JSONObject jsonCategory = jsonCategories.getJSONObject(i);
                Category category = new Category(jsonCategory.getInt(ModelsConstants.CATEGORY_ID), jsonCategory.getString(ModelsConstants.CATEGORY_NAME));
                categories.add(category);
            }
        } catch (JSONException ex) {

        }

        return categories;
    }
}
