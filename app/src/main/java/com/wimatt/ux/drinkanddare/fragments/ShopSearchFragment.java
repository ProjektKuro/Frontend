package com.wimatt.ux.drinkanddare.fragments;

import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.adapter.ShopsAdapter;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ProductsViewModel;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ShopsViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class ShopSearchFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView shopsRecyclerView;
    private ShopsAdapter adapter;
    private ShopsViewModel shopsViewModel;
    private ProductsViewModel productsViewModel;

    public ShopSearchFragment() {
        // Required empty public constructor
    }

    public static ShopSearchFragment newInstance() {
        ShopSearchFragment fragment = new ShopSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_market_search, container, false);

        swipeRefreshLayout = view.findViewById(R.id.market_swipe);
        shopsRecyclerView = view.findViewById(R.id.market_recyclerview);

        adapter = new ShopsAdapter(this::onShopClicked);

        shopsRecyclerView.setAdapter(adapter);

        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
            RecyclerView.VERTICAL, false));

        shopsViewModel.getShops().observe(getViewLifecycleOwner(), shops -> {
            adapter.updateShops(shops);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void onShopClicked(int position){
        ShowProductStateBottomSheet fragment = new ShowProductStateBottomSheet();
        fragment.setViewModel(productsViewModel);
        fragment.show(getChildFragmentManager(), "");
    }

    public void setViewModels(ShopsViewModel shopsViewModel, ProductsViewModel productsViewModel){
        this.productsViewModel = productsViewModel;
        this.shopsViewModel = shopsViewModel;
    }

}

