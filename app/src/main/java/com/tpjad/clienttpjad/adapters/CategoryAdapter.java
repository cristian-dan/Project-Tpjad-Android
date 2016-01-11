package com.tpjad.clienttpjad.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.models.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);
        if (category != null && convertView != null) {
            TextView idText = (TextView)convertView.findViewById(R.id.categoryIdText);
            TextView nameText = (TextView)convertView.findViewById(R.id.categoryNameText);

            idText.setText(category.getId());
            nameText.setText(category.getName());
        }

        return convertView;
    }
}
