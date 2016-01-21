package com.tpjad.clienttpjad.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.adapters.ProductAdapter;
import com.tpjad.clienttpjad.interfaces.CategoryCallbackInterface;
import com.tpjad.clienttpjad.interfaces.ProductCallbackInterface;
import com.tpjad.clienttpjad.interfaces.RequestCallbackInterface;
import com.tpjad.clienttpjad.models.Category;
import com.tpjad.clienttpjad.models.Product;
import com.tpjad.clienttpjad.utils.CategoryHelper;
import com.tpjad.clienttpjad.utils.ProductHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductTab extends Fragment {

    private View mView;
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private ProductHelper mProductHelper;
    private CategoryHelper mCategoryHelper;
    private List<Category> mCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.product_tab, container, false);
        mCategoryHelper = CategoryHelper.getInstance();
        mProductHelper = ProductHelper.getInstance();
        mCategories = new ArrayList<>();
        displayLoadingStatus();
        refreshCategoriesSpinner();
        refreshProductsList();
        hideLoadingStatus();

        Button addButton = (Button)mView.findViewById(R.id.addProductButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText productNameInput = (EditText)mView.findViewById(R.id.productName);
                final EditText productDescriptionInput = (EditText)mView.findViewById(R.id.productDescription);
                final EditText productStockInput = (EditText)mView.findViewById(R.id.productStock);
                final Spinner productCategorySpinner = (Spinner)mView.findViewById(R.id.productCategory);

                String productName = productNameInput.getText().toString();
                String productDescription = productDescriptionInput.getText().toString();
                int productStock;
                try {
                    productStock = Integer.parseInt(productStockInput.getText().toString());
                } catch (NumberFormatException ex) {
                    productStock = 0;
                }
                Category productCategory = (Category)productCategorySpinner.getSelectedItem();

                if (productName.length() == 0) {
                    productNameInput.setError(getString(R.string.productNameError));
                    return;
                }
                if (productDescription.length() == 0) {
                    productDescriptionInput.setError(getString(R.string.productDescriptionError));
                    return;
                }
                if (productStock <= 0) {
                    productStockInput.setError(getString(R.string.productStockError));
                    return;
                }
                if (productCategory == null) {
                    return;
                }

                mProductHelper.addProduct(
                        getContext(),
                        new Product(productStock, productName, productDescription, productCategory),
                        new RequestCallbackInterface() {
                            @Override
                            public void onRequestComplete(boolean success) {
                                if (success) {
                                    refreshProductsList();
                                    productNameInput.setText("");
                                    productDescriptionInput.setText("");
                                    productStockInput.setText("");
                                    productCategorySpinner.setSelection(0);
                                }
                                else {
                                    Toast.makeText(getContext(), getString(R.string.request_error), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
            }
        });

        return mView;
    }

    public void refreshCategoriesSpinner() {
        mCategoryHelper.loadCategories(
                getContext(),
                new CategoryCallbackInterface() {
                    @Override
                    public void onLoadComplete(List<Category> categories) {
                        mCategories = new ArrayList<>(categories);
                        Spinner categorySpinner = (Spinner) mView.findViewById(R.id.productCategory);
                        ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
                        categorySpinner.setAdapter(adapter);
                    }
                }
        );
    }

    public void refreshProductsList() {
        mProductHelper.loadProducts(
                getContext(),
                new ProductCallbackInterface() {
                    @Override
                    public void onLoadComplete(List<Product> products) {
                        final ProductAdapter adapter = new ProductAdapter(getContext(), products);
                        mListView = (ListView) mView.findViewById(R.id.productsList);
                        mListView.setAdapter(adapter);

                        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                Product selectedProduct = (Product) adapter.getItem(position);
                                if (selectedProduct != null) {
                                    displayProductPopup(selectedProduct);

                                    return true;
                                }

                                return false;
                            }
                        });
                    }
                }
        );
    }

    private void displayLoadingStatus() {
        mProgressDialog = ProgressDialog.show(getContext(), getString(R.string.loading_title), getString(R.string.loading_message), true);
    }

    private void hideLoadingStatus() {
        mProgressDialog.dismiss();
    }

    private void displayProductPopup(final Product product) {
        final Dialog productDialog = new Dialog(getContext());
        productDialog.setContentView(R.layout.product_form);

        final EditText productName = (EditText)productDialog.findViewById(R.id.formProductNameText);
        final EditText productDescription = (EditText)productDialog.findViewById(R.id.formProductDescriptionText);
        final EditText productStock = (EditText)productDialog.findViewById(R.id.formProductStockText);
        final Spinner productCategory = (Spinner)productDialog.findViewById(R.id.formProductCategorySpinner);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productStock.setText(String.valueOf(product.getStock()));

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCategories);
        productCategory.setAdapter(adapter);
        for (int i = 0; i < mCategories.size(); i++) {
            if (product.getCategory().getId() == mCategories.get(i).getId()) {
                productCategory.setSelection(i);
            }
        }

        Button updateButton = (Button)productDialog.findViewById(R.id.updateProductButton);
        Button removeButton = (Button)productDialog.findViewById(R.id.removeProductButton);
        Button cancelButton = (Button)productDialog.findViewById(R.id.closeFormButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productNameText = productName.getText().toString();
                String productDescriptionText = productDescription.getText().toString();
                int productStockText;
                try {
                    productStockText = Integer.parseInt(productStock.getText().toString());
                } catch (NumberFormatException ex) {
                    productStockText = 0;
                }
                Category productSelectedCategory = (Category)productCategory.getSelectedItem();

                if (productNameText.length() == 0) {
                    productName.setError(getString(R.string.productNameError));
                    return;
                }
                if (productDescriptionText.length() == 0) {
                    productDescription.setError(getString(R.string.productDescriptionError));
                    return;
                }
                if (productStockText <= 0) {
                    productStock.setError(getString(R.string.productStockError));
                    return;
                }
                if (productSelectedCategory == null) {
                    return;
                }

                product.setName(productNameText);
                product.setDescription(productDescriptionText);
                product.setStock(productStockText);
                product.setCategory(productSelectedCategory);

                mProductHelper.updateProduct(
                        getContext(),
                        product,
                        new RequestCallbackInterface() {
                            @Override
                            public void onRequestComplete(boolean success) {
                                if (success) {
                                    productDialog.dismiss();
                                    displayLoadingStatus();
                                    refreshProductsList();
                                    hideLoadingStatus();
                                } else {
                                    Toast.makeText(getContext(), getString(R.string.request_error), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductHelper.deleteProduct(
                        getContext(),
                        product.getId(),
                        new RequestCallbackInterface() {
                            @Override
                            public void onRequestComplete(boolean success) {
                                if (success) {
                                    productDialog.dismiss();
                                    displayLoadingStatus();
                                    refreshProductsList();
                                    hideLoadingStatus();
                                } else {
                                    Toast.makeText(getContext(), getString(R.string.request_error), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDialog.dismiss();
            }
        });

        productDialog.show();
    }
}
