package com.wimatt.ux.drinkanddare.retrofit;

import com.wimatt.ux.drinkanddare.retrofit.geocode_model.GeocodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReverseGeocodeService {

    @GET("/data/reverse-geocode-client?&localityLanguage=de")
    Call<GeocodeResponse> getLocationFromGeocode(@Query("latitude") double latitude,
        @Query("longitude") double longitude);

}
