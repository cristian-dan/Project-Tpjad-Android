package com.tpjad.clienttpjad.interfaces;

import com.tpjad.clienttpjad.models.Product;

import java.util.List;

public interface ProductCallbackInterface {
    void onLoadComplete(List<Product> products);
}
