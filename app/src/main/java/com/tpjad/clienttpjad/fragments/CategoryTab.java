package com.tpjad.clienttpjad.fragments;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.adapters.CategoryAdapter;
import com.tpjad.clienttpjad.interfaces.CategoryCallbackInterface;
import com.tpjad.clienttpjad.interfaces.RequestCallbackInterface;
import com.tpjad.clienttpjad.models.Category;
import com.tpjad.clienttpjad.models.Product;
import com.tpjad.clienttpjad.utils.CategoryHelper;

import java.util.List;

public class CategoryTab extends Fragment {

    private View mView;
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private CategoryHelper mCategoryHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.category_tab, container, false);
        mCategoryHelper = CategoryHelper.getInstance();
        displayLoadingStatus();
        refreshCategoriesList();
        hideLoadingStatus();

        Button addButton = (Button)mView.findViewById(R.id.addCategoryButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText categoryNameInput = (EditText)mView.findViewById(R.id.categoryName);
                String categoryNameText = categoryNameInput.getText().toString();

                if (categoryNameText.length() == 0) {
                    categoryNameInput.setError(getString(R.string.categoryNameError));
                    return;
                }

                mCategoryHelper.addCategory(
                        getContext(),
                        new Category(categoryNameText),
                        new RequestCallbackInterface() {
                            @Override
                            public void onRequestComplete(boolean success) {
                                if (success) {
                                    refreshCategoriesList();
                                    categoryNameInput.setText("");
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

    private void refreshCategoriesList() {
        mCategoryHelper.loadCategories(
                getContext(),
                new CategoryCallbackInterface() {
                    @Override
                    public void onLoadComplete(List<Category> categories) {
                        final CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
                        mListView = (ListView)mView.findViewById(R.id.categoryList);
                        mListView.setAdapter(adapter);

                        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                Category selectedCategory = (Category)adapter.getItem(position);
                                if (selectedCategory != null) {
                                    displayCategoryPopup(selectedCategory);

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

    private void displayCategoryPopup(final Category category) {
        final Dialog categoryDialog = new Dialog(getContext());
        categoryDialog.setContentView(R.layout.category_form);

        final EditText categoryName = (EditText)categoryDialog.findViewById(R.id.formCategoryNameText);
        categoryName.setText(category.getName());

        Button updateButton = (Button)categoryDialog.findViewById(R.id.updateCategoryButton);
        Button removeButton = (Button)categoryDialog.findViewById(R.id.removeCategoryButton);
        Button cancelButton = (Button)categoryDialog.findViewById(R.id.closeFormButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryNameText = categoryName.getText().toString();

                if (categoryNameText.length() == 0) {
                    categoryName.setError(getString(R.string.categoryNameError));
                    return;
                }

                category.setName(categoryNameText);

                mCategoryHelper.updateCategory(
                        getContext(),
                        category,
                        new RequestCallbackInterface() {
                            @Override
                            public void onRequestComplete(boolean success) {
                                if (success) {
                                    categoryDialog.dismiss();
                                    displayLoadingStatus();
                                    refreshCategoriesList();
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
                mCategoryHelper.deleteCategory(
                        getContext(),
                        category.getId(),
                        new RequestCallbackInterface() {
                            @Override
                            public void onRequestComplete(boolean success) {
                                if (success) {
                                    categoryDialog.dismiss();
                                    displayLoadingStatus();
                                    refreshCategoriesList();
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
                categoryDialog.dismiss();
            }
        });

        categoryDialog.show();
    }
}
