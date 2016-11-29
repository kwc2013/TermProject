package com.example.user.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent Map = new Intent(this, MapActivity.class);
        final Intent Total = new Intent(this, TotalActivity.class);
        final Intent Add = new Intent(this, AddActivity.class);

        Button MapBtn = (Button) findViewById(R.id.mapbtn);
        Button LogBtn = (Button) findViewById(R.id.logbtn);
        Button AddBtn = (Button) findViewById(R.id.mapaddlogbtn);

        MapBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(Map);
            }
        });

        LogBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(Total);
            }
        });

        AddBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(Add);
            }
        });


    }
}
