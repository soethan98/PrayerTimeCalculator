package com.example.soe_than.prayertimecalculator;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by soe_than on 4/20/18.
 */

public class ManualCorrectionAdapter extends RecyclerView.Adapter<ManualCorrectionAdapter.MyViewHolder> {
    private String[] prayerList;
    private Context context;
    private AppSettings appSettings;


    public ManualCorrectionAdapter(Context context, String[] prayerList) {
        this.prayerList = prayerList;
        this.context = context;

        appSettings = AppSettings.getInstance(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manual_correction_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.manualCorrectionLabel.setText(prayerList[position]);

        switch (position) {
            case 0:
                if (appSettings.getInt("fajr_corr", 0) != 0) {
                    holder.manualCorrectionSummary.setText(appSettings.getInt("fajr_corr", 0) + "");
                } else {
                    holder.manualCorrectionSummary.setText(0 + "");
                }
                break;
            case 1:
                if (appSettings.getInt("sunrise_corr", 0) != 0) {
                    holder.manualCorrectionSummary.setText(appSettings.getInt("sunrise_corr", 0) + "");
                } else {
                    holder.manualCorrectionSummary.setText(0 + "");
                }
                break;
            case 2:
                if (appSettings.getInt("dhuhr_corr", 0) != 0) {
                    holder.manualCorrectionSummary.setText(appSettings.getInt("dhuhr_corr", 0) + "");
                } else {
                    holder.manualCorrectionSummary.setText(0 + "");
                }
                break;
            case 3:
                if (appSettings.getInt("asr_corr", 0) != 0) {
                    holder.manualCorrectionSummary.setText(appSettings.getInt("asr_corr", 0) + "");
                } else {
                    holder.manualCorrectionSummary.setText(0 + "");
                }
                break;
            case 4:
                if (appSettings.getInt("maghrib_corr", 0) != 0) {
                    holder.manualCorrectionSummary.setText(appSettings.getInt("maghrib_corr", 0) + "");
                } else {
                    holder.manualCorrectionSummary.setText(0 + "");
                }
                break;
            case 5:
                if (appSettings.getInt("isha'a_corr", 0) != 0) {
                    holder.manualCorrectionSummary.setText(appSettings.getInt("isha'a_corr", 0) + "");
                } else {
                    holder.manualCorrectionSummary.setText(0 + "");
                }

                break;


        }



    }

    @Override
    public int getItemCount() {
        return prayerList.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView manualCorrectionLabel, manualCorrectionSummary;

        public MyViewHolder(View itemView) {
            super(itemView);
            manualCorrectionLabel = itemView.findViewById(R.id.manual_label);
            manualCorrectionSummary = itemView.findViewById(R.id.manual_summ);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            final AlertDialog.Builder d = new AlertDialog.Builder(context);
            final View dialogview = LayoutInflater.from(context.getApplicationContext())
                    .inflate(R.layout.number_picker_dialog, null);
            final NumberPicker numberPicker = (NumberPicker) dialogview.findViewById(R.id.dialog_number_picker);
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(60);
            d.setTitle("Title");
            d.setView(dialogview);

            final int namazPosition = getAdapterPosition();
            switch (namazPosition) {
                case 0:

                    numberPicker.setValue(appSettings.getInt("fajr_corr", 0));
                    break;
                case 1:
                    numberPicker.setValue(appSettings.getInt("sunrise_corr", 0));
                    break;
                case 2:
                    numberPicker.setValue(appSettings.getInt("dhuhr_corr", 0));
                    break;
                case 3:
                    numberPicker.setValue(appSettings.getInt("asr_corr", 0));
                    break;
                case 4:
                    numberPicker.setValue(appSettings.getInt("maghrib_corr", 0));
                    break;
                case 5:
                    numberPicker.setValue(appSettings.getInt("isha'a_corr", 0));
                    break;


            }


            d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (namazPosition) {
                        case 0:
                            appSettings.set("fajr_corr", numberPicker.getValue());
                            break;
                        case 1:
                            appSettings.set("sunrise_corr", numberPicker.getValue());
                            break;
                        case 2:
                            appSettings.set("dhuhr_corr", numberPicker.getValue());
                            break;
                        case 3:
                            appSettings.set("asr_corr", numberPicker.getValue());
                            break;
                        case 4:
                            appSettings.set("maghrib_corr", numberPicker.getValue());
                            break;
                        case 5:
                            appSettings.set("isha'a_corr", numberPicker.getValue());
                            break;


                    }
                    Toast.makeText(context, numberPicker.getValue() + "", Toast.LENGTH_SHORT).show();

                }
            });
            d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            final AlertDialog alertDialog = d.create();
            alertDialog.show();


        }
    }
}
