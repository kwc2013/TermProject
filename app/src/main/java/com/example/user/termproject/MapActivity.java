package com.example.user.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapClickListener, Data {
    OpenHelper helper = new OpenHelper(this);
    SQLiteDatabase db;

    public void SQLexec(String name, String score, String Lat, String Lon, String locking, String tissue, String beedae, SQLiteDatabase db) {
        db.execSQL("insert into member(name, score, latitude, longitude, locking, tissue, beedae) values ("
                + name + ", "
                + score + ", "
                + Lat + ", "
                + Lon + ", "
                + locking + ", "
                + tissue + ", "
                + beedae +");"
        );
    }

    protected GoogleMap mGoogleMap;

    double currentlat, currentlon;
    LatLng currentloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapsInitializer.initialize(getApplicationContext());

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // 핸드폰의 GPS 사용 유무를 확인하여 GPS가 꺼져있을 시 켜게끔 유도
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("GPS를 켜야 어플이 작동됩니다. GPS를 켜고 어플을 재시작 해주시기 바랍니다.").setCancelable(false).setPositiveButton("GPS 켜기", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent gpsOptionIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(gpsOptionIntent);
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        init(); // 맵 초기화 및 현재 위치 저장


        final Intent AddInt = new Intent(this, AddActivity.class);

        Button MapAddLogBtn = (Button) findViewById(R.id.mapaddlogbtn);

        MapAddLogBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(AddInt);
            }
        });
    }

    /** Map 클릭시 터치 이벤트 */
    public void onMapClick(LatLng point) {

        // 현재 위도와 경도에서 화면 포인트를 알려준다
        Point screenPt = mGoogleMap.getProjection().toScreenLocation(point);

        // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
        LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(screenPt);

        Log.d("맵좌표", "좌표: 위도(" + String.valueOf(point.latitude) + "), 경도("
                + String.valueOf(point.longitude) + ")");
        Log.d("화면좌표", "화면좌표: X(" + String.valueOf(screenPt.x) + "), Y("
                + String.valueOf(screenPt.y) + ")");
    }

    public void init() {
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        GpsInfo gps = new GpsInfo(this);

        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            currentlat = gps.getLatitude();
            currentlon = gps.getLongitude();

            // LatLng 값 등록
            currentloc = new LatLng(currentlat, currentlon);

            // LatLng 값 등록된 것 기반으로 현재위치 출력
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentloc));

            // Map 을 zoom
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

            // 현재 위치 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(currentloc);// 위도 • 경도
            optFirst.title("Current Position");// 제목 미리보기
            optFirst.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)); // 아이콘
            mGoogleMap.addMarker(optFirst).showInfoWindow();
        }
    }

    public void moveCamera() {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(m_loc_Array.get(4)));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
    }
}

