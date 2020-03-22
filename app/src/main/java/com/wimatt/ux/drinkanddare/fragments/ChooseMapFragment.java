package com.wimatt.ux.drinkanddare.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.ui.viewmodels.StateViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChooseMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;


    private LatLng location;

    private FloatingActionButton floatingActionButton;

    private String mStreetText = "";
    private String mCityText = "";
    private StateViewModel mStateViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_location, container, false);
        mStateViewModel = new ViewModelProvider(getActivity()).get(StateViewModel.class);
        floatingActionButton = view.findViewById(R.id.map_fab);
        floatingActionButton.setEnabled(false);
        floatingActionButton.setOnClickListener(this::popBackStack);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getContext(), Locale.getDefault());


    }

    /**
     * Manipulates the map once available. This callback is triggered when the map is ready to be
     * used. This is where we can add markers or lines, add listeners or move the camera. In this
     * case, we just add a marker near Sydney, Australia. If Google Play services is not installed
     * on the device, the user will be prompted to install it inside the SupportMapFragment. This
     * method will only be triggered once the user has installed Google Play services and returned
     * to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mStateViewModel.getChoosenLocation().observe(getViewLifecycleOwner(), choosenLocation -> {
            if (choosenLocation != null) {
                location = choosenLocation.getLatLong();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(location).title("Dein Standort").icon(
                        BitmapDescriptorFactory.defaultMarker()));
            }
        });

        mMap.setOnMapClickListener(latLng -> {

            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addresses.get(0);

            if (address != null) {
                StringBuilder sb = new StringBuilder();

                mCityText = address.getLocality();
                if (address.getThoroughfare() == null || address.getThoroughfare().equals("Unnamed Road")) {
                    mStreetText = latLng.latitude + ", " + latLng.longitude;
                } else {
                    mStreetText = address.getThoroughfare();
                    if (address.getSubThoroughfare() != null) {
                        mStreetText += " " + address.getSubThoroughfare();
                    }
                }
            }
            mStateViewModel.setChoosenLocation(new StateViewModel.Companion.ChoosenLocation(
                    latLng, mCityText, mStreetText
            ));

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Dein Standort").icon(
                    BitmapDescriptorFactory.defaultMarker()));
            location = latLng;

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
            floatingActionButton.setEnabled(true);
        });
    }

    public void popBackStack(View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(this);
        trans.commit();
        manager.popBackStack();
    }

}
