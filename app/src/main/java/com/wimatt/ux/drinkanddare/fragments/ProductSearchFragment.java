package com.wimatt.ux.drinkanddare.fragments;

import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.adapter.ProductAdapter;
import com.wimatt.ux.drinkanddare.adapter.ShopsAdapter;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ProductsViewModel;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ShopsViewModel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class ProductSearchFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView productsRecyclerView;
    private ProductAdapter adapter;

    private ProductsViewModel productsViewModel;
    private ShopsViewModel shopsViewModel;

    public ProductSearchFragment() {
        // Required empty public constructor
    }

    public static ProductSearchFragment newInstance() {
        ProductSearchFragment fragment = new ProductSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_search, container, false);
        // Inflate the layout for this fragment

        swipeRefreshLayout = view.findViewById(R.id.product_reload_view);
        productsRecyclerView = view.findViewById(R.id.product_recyclerview);

        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductAdapter(this::onProductClicked, false);

        productsRecyclerView.setAdapter(adapter);

        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.updateProducts(products);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void onProductClicked(int position){
        ShowMarketBottomSheet fragment = new ShowMarketBottomSheet();
        fragment.setShopsViewModel(shopsViewModel);
        fragment.show(getChildFragmentManager(), "");

    }

    public void setViewModels(ShopsViewModel shopsViewModel, ProductsViewModel productsViewModel){
        this.productsViewModel = productsViewModel;
        this.shopsViewModel = shopsViewModel;
    }

}
