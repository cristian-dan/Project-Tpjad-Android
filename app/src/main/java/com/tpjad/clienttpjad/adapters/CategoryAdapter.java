package com.tpjad.clienttpjad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.models.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private static LayoutInflater inflater;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.categories.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = inflater.inflate(R.layout.category_item, null);
        }

        Category category = (Category)getItem(position);
        if (category != null && view != null) {
            TextView idText = (TextView)view.findViewById(R.id.categoryIdText);
            TextView nameText = (TextView)view.findViewById(R.id.categoryNameText);

            idText.setText(this.context.getString(R.string.categoryIdText, category.getId()));
            nameText.setText(category.getName());
        }

        return view;
    }
}
