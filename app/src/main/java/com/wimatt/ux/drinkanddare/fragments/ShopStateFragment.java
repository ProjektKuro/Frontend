package com.wimatt.ux.drinkanddare.fragments;


import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.view.RoundedBottomSheetDialogFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopStateFragment extends RoundedBottomSheetDialogFragment {


    public ShopStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_state, container, false);
    }

}
