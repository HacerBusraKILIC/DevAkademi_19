package com.example.dell.devakademi;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Ilan> implements Filterable {
    ArrayList<Ilan> ilanlar;
    Context context;
    int resource;

    ArrayList<Ilan> filterList;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ilan> ilanlar) {
        super(context, resource, ilanlar);
        this.ilanlar = ilanlar;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null, true);
        }

        Ilan ilan = getItem(position);

        TextView ilanAdi = convertView.findViewById(R.id.ilanAdi);
        ilanAdi.setText(ilan.getTitle());

        TextView ilanCityTown = convertView.findViewById(R.id.ilanCityTown);
        ilanCityTown.setText(ilan.getCity() +"/"+ ilan.getTown());

        return convertView;
    }


}