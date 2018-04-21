package com.example.soe_than.prayertimecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ManualCorrection extends AppCompatActivity {

    private String[] prayerLabelList={"Fajr","Sunrise","Dhuhr","Asr","Maghrib","Isha'a"};

    private RecyclerView recyclerView;
    private ManualCorrectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_correction);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter=new ManualCorrectionAdapter(this,prayerLabelList);

        recyclerView.setAdapter(adapter);


    }
}
