package com.wimatt.ux.drinkanddare;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wimatt.ux.drinkanddare.adapter.ViewPagerAdapter;
import com.wimatt.ux.drinkanddare.fragments.ChooseMapFragment;
import com.wimatt.ux.drinkanddare.fragments.ProductSearchFragment;
import com.wimatt.ux.drinkanddare.fragments.ReportStatusFragment;
import com.wimatt.ux.drinkanddare.fragments.ShopSearchFragment;
import com.wimatt.ux.drinkanddare.retrofit.ReverseGeocodeService;
import com.wimatt.ux.drinkanddare.retrofit.geocode_model.GeocodeResponse;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ProductsViewModel;
import com.wimatt.ux.drinkanddare.ui.viewmodels.ShopsViewModel;
import com.wimatt.ux.drinkanddare.ui.viewmodels.StateViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int POS_MARKET = 0;
    private static final int POS_PRODUCT = 1;
    private static final int POS_REPORT = 2;

    private static final int REQUEST_CHOOSE_LOCATION = 300;

    ViewPager mainPager;

    BottomNavigationView bottomNavigationView;

    private ProductSearchFragment productSearchFragment;
    private ShopSearchFragment shopSearchFragment;
    private ReportStatusFragment reportStatusFragment;

    private StateViewModel mStateViewModel;
    private MenuItem prevMenuItem;

    private ShopsViewModel shopsViewModel;
    private ProductsViewModel productsViewModel;

    private Geocoder geocoder;

    private LocationManager locationManager;
    private LatLng location;
    private ConstraintLayout mLocationBar;
    private TextView mCity;
    private TextView mStreet;
    private static final int PERMISSION_REQUEST = 32;

    private static final String[] PERMISSIONS_STORAGE = {
        permission.ACCESS_FINE_LOCATION,
        permission.ACCESS_COARSE_LOCATION,
        permission.WRITE_EXTERNAL_STORAGE,
        permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateViewModel = new ViewModelProvider(this).get(StateViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainPager = findViewById(R.id.dashboard_viewpager);
        bottomNavigationView = findViewById(R.id.dashboard_bottom_navigation);
        mLocationBar = findViewById(R.id.main_location_container);
        mCity = findViewById(R.id.main_city_text);
        mStreet = findViewById(R.id.main_city_street);
        verifyPermissions();

        shopsViewModel = new ViewModelProvider(this).get(ShopsViewModel.class);
        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        productsViewModel.loadProducts();

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        initPager();
        initListeners();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    private void doMySearch(String query) {
        mStateViewModel.setSearchQuery(query);
    }

    private void verifyPermissions() {
        if (!isLocationPermissionGranted()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, PERMISSION_REQUEST);
        } else {
            checkIfLocationEnabled();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults.length == 2 && grantResults[0] == PERMISSION_GRANTED
                    && grantResults[1] == PERMISSION_GRANTED) {
                    checkIfLocationEnabled();
                } else {
                    startChooseLocationActivity();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean isLocationPermissionGranted() {
        int fineLocationPermission = ActivityCompat
            .checkSelfPermission(this, PERMISSIONS_STORAGE[0]);
        int coarseLocationPermission = ActivityCompat
            .checkSelfPermission(this, PERMISSIONS_STORAGE[1]);
        return fineLocationPermission == PERMISSION_GRANTED
            && coarseLocationPermission == PERMISSION_GRANTED;
    }

    private void initPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        productSearchFragment = ProductSearchFragment.newInstance();
        shopSearchFragment = ShopSearchFragment.newInstance();
        reportStatusFragment = ReportStatusFragment.newInstance();

        shopSearchFragment.setViewModels(shopsViewModel, productsViewModel);
        productSearchFragment.setViewModels(shopsViewModel, productsViewModel);
        reportStatusFragment.setViewModels(shopsViewModel, productsViewModel);

        adapter.addFragments(shopSearchFragment,
            productSearchFragment,
            reportStatusFragment);

        mainPager.setAdapter(adapter);
        mainPager.setOffscreenPageLimit(3);
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.dashboard_navigation_market:
                    mainPager.setCurrentItem(POS_MARKET, true);
                    break;
                case R.id.dashboard_navigation_products:
                    mainPager.setCurrentItem(POS_PRODUCT, true);
                    break;
                case R.id.dashboard_navigation_report:
                    mainPager.setCurrentItem(POS_REPORT, true);
                    break;
            }
            return false;
        });
        mainPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mLocationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChooseLocationActivity();
            }
        });

        mStateViewModel.getChoosenLocation().observe(this, choosenLocation -> {
            Log.d("MYTAg", "Location update: " + choosenLocation);
            if (choosenLocation != null) {
                mCity.setText(choosenLocation.getCityName());
                mStreet.setText(choosenLocation.getStreetName());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.action_loacation:
    //
    //                return true;
    //            default:
    //                return super.onContextItemSelected(item);
    //        }
    //    }

    public void onLocationChanged(Location location) {

        updateLatLng(new LatLng(location.getLatitude(), location.getLongitude()));

        locationManager.removeUpdates(this);
    }

    private void updateLatLng(LatLng latLng) {

        this.location = latLng;

        shopsViewModel.loadShops(latLng.latitude, latLng.longitude);

        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address address = addresses.size() > 0 ? addresses.get(0) : null;

        if (address != null) {
            StringBuilder sb = new StringBuilder();

            mCity.setText(address.getLocality());
            if (address.getThoroughfare() == null || address.getThoroughfare()
                .equals("Unnamed Road")) {
                mStreet.setText("" + latLng.latitude + ", " + latLng.longitude);
            } else {
                String streetText = address.getThoroughfare();
                if (address.getSubThoroughfare() != null) {
                    streetText += " " + address.getSubThoroughfare();
                }
                mStreet.setText(streetText);
            }
        }

        //        Retrofit retrofit = new Builder()
        //                .baseUrl("https://api.bigdatacloud.net")
        //                .addConverterFactory(GsonConverterFactory.create())
        //                .build();
        //
        //        ReverseGeocodeService service = retrofit.create(ReverseGeocodeService.class);
        //
        //        service.getLocationFromGeocode(latLng.latitude, latLng.longitude)
        //                .enqueue(new Callback<GeocodeResponse>() {
        //                    @Override
        //                    public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
        //                        getSupportActionBar().setTitle(response.body().getLocality());
        //                    }
        //
        //                    @Override
        //                    public void onFailure(Call<GeocodeResponse> call, Throwable t) {
        //                        //TODO error handling?
        //                    }
        //                });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressLint("MissingPermission")
    public void checkIfLocationEnabled() {
        LocationManager lm = (LocationManager) getApplicationContext()
            .getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gpsEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bitte schalte dein GPS an.")
                .setTitle("GPS Deaktiviert")
                .setCancelable(true)
                .setPositiveButton("Einstellungen",
                    (dialog, which) ->
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            42))
                .setNegativeButton("Manuell",
                    (dialog, which) -> startChooseLocationActivity());

            builder.create().show();
        } else {
            getLocation();
        }
    }

    private void startChooseLocationActivity() {
        getSupportFragmentManager().beginTransaction()
            .add(R.id.main_container, new ChooseMapFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 42:
                getLocation();
                break;
            case REQUEST_CHOOSE_LOCATION:
                //                if (resultCode == RESULT_OK) {
                //                    updateLatLng(data.getParcelableExtra(ChooseLocationActivity.EXTRA_CHOOSED_LOCATION));
                //                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates("gps", 0, 0, this);
    }
}
