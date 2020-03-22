package com.wimatt.ux.drinkanddare.fragments;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.adapter.ProductAdapter;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ProductsViewModel;
import com.wimatt.ux.drinkanddare.view.RoundedBottomSheetDialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductStateBottomSheet extends RoundedBottomSheetDialogFragment {

    private ProductsViewModel productsViewModel;

    private RecyclerView productsRecyclerView;
    private ProductAdapter adapter;

    public ShowProductStateBottomSheet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater
            .inflate(R.layout.fragment_show_product_state_bottom_sheet, container, false);

        productsRecyclerView = view.findViewById(R.id.product_state_recyclerview);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductAdapter(true);

        productsRecyclerView.setAdapter(adapter);

        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.updateProducts(products);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void onProductClicked(int i) {
    }

    public void setViewModel(ProductsViewModel productsViewModel){
        this.productsViewModel = productsViewModel;
    }

}
