package com.tpjad.clienttpjad.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.adapters.CategoryAdapter;
import com.tpjad.clienttpjad.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryTab extends Fragment {
    ListView mListView;
    ProgressDialog mProgressDialog;
    CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_tab, container, false);
        displayLoadingStatus();
        loadCategoryList(view);
        hideLoadingStatus();
        return view;
    }

    private void loadCategoryList(View view) {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category(1, "A"));
        categories.add(new Category(2, "B"));
        categories.add(new Category(3, "C"));

        mListView = (ListView)view.findViewById(R.id.categoryList);
        if (mListView == null) {
            return;
        }

        adapter = new CategoryAdapter(getContext(), R.layout.category_item, categories);
        mListView.setAdapter(adapter);
    }

    private void displayLoadingStatus() {
        mProgressDialog = ProgressDialog.show(getContext(), getString(R.string.loading_title), getString(R.string.loading_message), true);
    }

    private void hideLoadingStatus() {
        mProgressDialog.dismiss();
    }

}
