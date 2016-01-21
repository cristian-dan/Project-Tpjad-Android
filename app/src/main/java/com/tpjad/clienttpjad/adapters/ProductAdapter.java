package com.tpjad.clienttpjad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.models.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context mContext;
    private static LayoutInflater mInflater;
    private List<Product> mProducts;

    public ProductAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.mProducts = products;
        mInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mProducts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.product_item, null);
        }

        Product product = (Product)getItem(position);
        if (product != null && view != null) {
            TextView idText = (TextView)view.findViewById(R.id.productIdText);
            TextView nameText = (TextView)view.findViewById(R.id.productNameText);
            TextView descriptionText = (TextView)view.findViewById(R.id.productDescriptionText);
            TextView categoryText = (TextView)view.findViewById(R.id.productCategoryText);
            TextView stockText = (TextView)view.findViewById(R.id.productStockText);

            idText.setText(this.mContext.getString(R.string.productIdText, product.getId()));
            nameText.setText(product.getName());
            descriptionText.setText(product.getDescription());
            categoryText.setText(product.getCategory().getName());
            stockText.setText(String.valueOf(product.getStock()));
        }

        return view;
    }
}
