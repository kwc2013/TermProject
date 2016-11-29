package com.example.user.termproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface Data {
    ArrayList<Double> m_lat_Array = new ArrayList<Double>();
    ArrayList<Double> m_lon_Array = new ArrayList<Double>();
    ArrayList<LatLng> m_loc_Array = new ArrayList<LatLng>();
}
