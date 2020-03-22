package com.wimatt.ux.drinkanddare.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.adapter.ShopsAdapter;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ShopsViewModel;
import com.wimatt.ux.drinkanddare.view.RoundedBottomSheetDialogFragment;

public class ShowMarketBottomSheet extends RoundedBottomSheetDialogFragment {

    private ShopsViewModel shopsViewModel;
    private RecyclerView marketRecyclerView;
    private ShopsAdapter adapter;

    public ShowMarketBottomSheet() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_show_market_bottom_sheet, container, false);

        marketRecyclerView = view.findViewById( R.id.show_market_recyclerview );
        marketRecyclerView.setLayoutManager( new LinearLayoutManager( getContext()));

        adapter = new ShopsAdapter(  );
        marketRecyclerView.setAdapter( adapter );

        shopsViewModel.getShops().observe( getViewLifecycleOwner(),shops -> {
            adapter.updateShops( shops );
            adapter.notifyDataSetChanged();
        } );

        return view;
    }

    public void setShopsViewModel(ShopsViewModel shopsViewModel){
        this.shopsViewModel = shopsViewModel;
    }
}
