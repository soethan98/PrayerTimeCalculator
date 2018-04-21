package com.example.soe_than.prayertimecalculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {

    private TextView prayerTimeConvent, manualCorrection, juristicMethod, latitiudeAdjustment,
            summaryLatitudeAdjustment, summaryJuristicMethod, summaryPrayerTimeConvent;
    String[] listItems;
    AppSettings appSettings;
    private int latitudePref, juristicPref, timeConvetPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        appSettings = AppSettings.getInstance(this);

        prayerTimeConvent = findViewById(R.id.prayer_time_convent);
        manualCorrection = findViewById(R.id.manual_correction);
        juristicMethod = findViewById(R.id.juristic_method);
        latitiudeAdjustment = findViewById(R.id.latitude_adjustment);
        prayerTimeConvent = findViewById(R.id.prayer_time_convent);

        summaryLatitudeAdjustment = findViewById(R.id.summ_lat_adj);
        summaryJuristicMethod = findViewById(R.id.juristic_method_summ);
        summaryPrayerTimeConvent = findViewById(R.id.prayer_time_convent_summ);

        latitudePref = appSettings.getInt("latitudeAdjustment", 0);
        juristicPref = appSettings.getInt("juristicSetting", 0);
        timeConvetPref = appSettings.getInt("timeConvent", 0);

//        int a = appSettings.getInt("latitudeAdjustment",0);
//        int b = appSettings.getInt("juristicSetting",0);
//        int c = appSettings.getInt("timeConvent", 0);
//
//
//        Log.i("sharePref", " " + c);
        if (latitudePref != 0) {
            summaryLatitudeAdjustment.setText(latitudePref + "");
        }
        if (juristicPref != 0) {
            summaryJuristicMethod.setText(juristicPref + "");
        }
        if (timeConvetPref != 0) {
            summaryPrayerTimeConvent.setText(timeConvetPref + "");
        }


        latitiudeAdjustment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioButtonDialog();
            }
        });
        juristicMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJuristicMethod();
            }
        });

        prayerTimeConvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeConvent();
            }
        });
        manualCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openManualCorrectionActivity();
            }
        });


    }

    private void showTimeConvent() {
        String[] items = getResources().getStringArray(R.array.convention_settings);

        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setTitle("Select");

        RadioGroup r1 = (RadioGroup) dialog.findViewById(R.id.radio_reg_group);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);


        for (int i = 0; i < items.length; i++) {
            RadioButton r11 = new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            r11.setText(items[i]);
            r1.addView(r11);
        }
        dialog.show();

//
        if (timeConvetPref != 0) {
            for (int i = 0; i < items.length; i++) {

                RadioButton rd = (RadioButton) r1.getChildAt(i);

                if (rd.getId() == timeConvetPref) {
                    rd.setChecked(true);
                    break;
                }

            }
        }


        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //
//            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                Log.i("TAG", group.getChildCount() + "");

                for (int j = 0; j < childCount; j++) {
                    RadioButton btn = (RadioButton) group.getChildAt(j);

                    if (btn.getId() == checkedId) {
                        Toast.makeText(getApplicationContext(), btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        summaryPrayerTimeConvent.setText(btn.getId() + "");
                        appSettings.set("timeConvent", j + 1);
                        Log.i("sh", btn.getId() + "");
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showJuristicMethod() {
        String[] list = getResources().getStringArray(R.array.juristic_setting);

        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setTitle("Select");

        RadioGroup r2 = (RadioGroup) dialog.findViewById(R.id.radio_reg_group);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);


        for (int i = 0; i < list.length; i++) {
            RadioButton r22 = new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            r22.setText(list[i]);
            r2.addView(r22);

        }


        if (juristicPref != 0) {
            for (int i = 0; i < list.length; i++) {
                RadioButton rd = (RadioButton) r2.getChildAt(i);
                if (rd.getId() == juristicPref) {
                    rd.setChecked(true);
                    break;
                }

            }
        }

        dialog.show();

        r2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //
//            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                Log.i("TAG", group.getChildCount() + "");

                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);

                    if (btn.getId() == checkedId) {
                        Log.i(btn.getId() + "", checkedId + "");
                        Toast.makeText(getApplicationContext(), btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        summaryJuristicMethod.setText(btn.getId() + "");
                        appSettings.set("juristicSetting", x + 1);

                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }


    private void showRadioButtonDialog() {

        String[] listItems = getResources().getStringArray(R.array.latitude_setting);

        // custom dialog
        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setTitle("Select");

        RadioGroup r3 = (RadioGroup) dialog.findViewById(R.id.radio_reg_group);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);


        for (int i = 0; i < listItems.length; i++) {
            RadioButton r33 = new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            r33.setText(listItems[i]);
            r3.addView(r33);
        }


        if (latitudePref != 0) {
            for (int i = 0; i < listItems.length; i++) {
                RadioButton rd = (RadioButton) r3.getChildAt(i);
                if (latitudePref == rd.getId()) {
                    rd.setChecked(true);
                    break;
                }

            }
        }

        dialog.show();

        r3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //
//            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                Log.i("TAG", group.getChildCount() + "");
                for (int i = 0; i < childCount; i++) {
                    RadioButton btn = (RadioButton) group.getChildAt(i);

                    if (btn.getId() == checkedId) {
                        Toast.makeText(getApplicationContext(), btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        summaryLatitudeAdjustment.setText(btn.getId() + "");
                        appSettings.set("latitudeAdjustment", i + 1);


                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    private void openManualCorrectionActivity() {
        startActivity(new Intent(Setting.this, ManualCorrection.class));
    }


}
