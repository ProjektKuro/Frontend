package com.wimatt.ux.drinkanddare.retrofit.geocode_model;

public class GeocodeResponse {

    private float latitude;
    private float longitude;
    private String localityLanguageRequested;
    private String continent;
    private String continentCode;
    private String countryName;
    private String countryCode;
    private String principalSubdivision;
    private String locality;
    private String postcode;

    public GeocodeResponse(float latitude, float longitude, String localityLanguageRequested,
        String continent, String continentCode, String countryName, String countryCode,
        String principalSubdivision, String locality, String postcode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.localityLanguageRequested = localityLanguageRequested;
        this.continent = continent;
        this.continentCode = continentCode;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.principalSubdivision = principalSubdivision;
        this.locality = locality;
        this.postcode = postcode;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getLocalityLanguageRequested() {
        return localityLanguageRequested;
    }

    public void setLocalityLanguageRequested(String localityLanguageRequested) {
        this.localityLanguageRequested = localityLanguageRequested;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPrincipalSubdivision() {
        return principalSubdivision;
    }

    public void setPrincipalSubdivision(String principalSubdivision) {
        this.principalSubdivision = principalSubdivision;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
