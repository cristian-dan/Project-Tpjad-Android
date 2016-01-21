package com.tpjad.clienttpjad.interfaces;

import com.tpjad.clienttpjad.models.Category;

import java.util.List;

public interface CategoryCallbackInterface {
    void onLoadComplete(List<Category> categories);
}
