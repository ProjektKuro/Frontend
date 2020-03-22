package com.wimatt.ux.drinkanddare.fragments;

import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.adapter.ShopsAdapter;
import com.wimatt.ux.drinkanddare.room.models.Shop;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ProductsViewModel;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ShopsViewModel;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReportStatusFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView shopsRecyclerView;
    private ShopsAdapter adapter;

    private ProductsViewModel productsViewModel;
    private ShopsViewModel shopsViewModel;

    public ReportStatusFragment() {
        // Required empty public constructor
    }

    public static ReportStatusFragment newInstance() {
        ReportStatusFragment fragment = new ReportStatusFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report_status, container, false);

        swipeRefreshLayout = view.findViewById( R.id.swipe_refresh );
        shopsRecyclerView = view.findViewById( R.id.shop_recyclerview );

        adapter = new ShopsAdapter();

        shopsRecyclerView.setAdapter(adapter);
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));

        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> adapter.updateProducts(products));
        shopsViewModel.getShops().observe(getViewLifecycleOwner(), shops -> {
            adapter.updateShops(shops);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    public void setViewModels(ShopsViewModel shopsViewModel, ProductsViewModel productsViewModel){
        this.productsViewModel = productsViewModel;
        this.shopsViewModel = shopsViewModel;
    }

}
