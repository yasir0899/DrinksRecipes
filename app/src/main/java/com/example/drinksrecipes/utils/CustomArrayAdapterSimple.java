package com.example.providerportal.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.providerportal.R;
import com.example.providerportal.models.DateRangeDataModel;

import java.util.List;

public class CustomArrayAdapterSimple extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<DateRangeDataModel> items;
    private final int mResource;

    public CustomArrayAdapterSimple(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, parent);
    }

    private View createItemView(int position, ViewGroup parent){
        final View view = mInflater.inflate(R.layout.spinner_single_textview, parent, false);

        TextView offTypeTv = view.findViewById(R.id.tvSpinnerTitle);


        DateRangeDataModel model = items.get(position);

        offTypeTv.setText(model.getTitle());


        return view;
    }
}