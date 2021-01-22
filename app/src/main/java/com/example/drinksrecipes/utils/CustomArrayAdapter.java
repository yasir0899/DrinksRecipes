package com.example.providerportal.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.providerportal.R;
import com.example.providerportal.models.DateRangeDataModel;
import com.example.providerportal.models.responseModel.GetClaimFilterSubmissionStatus;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<DateRangeDataModel> items;
    private static final int DEFAULT_LAYOUT_RESOURCE = R.layout.spinner_single_textview;
    private  final int layoutResource;
    private  final String hintResource;
    private final LayoutInflater layoutInflater;

    public CustomArrayAdapter(@NonNull Context context, ArrayList  items,
                              @NonNull int layoutResource,  String hintResource) {
        super(context, 0, items);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.layoutResource = layoutResource;
        this.hintResource = hintResource;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return getCustomView(position,convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "position: " + position + ", getCount: " + getCount());
        View view;
        if (position ==0) {
            view = getDefaultView(parent);
        } else {
            view = getCustomView(position, convertView, parent);
        }
        return view;
    }


    private View inflateDefaultLayout(ViewGroup parent) {
        return inflateLayout(DEFAULT_LAYOUT_RESOURCE, parent, false);
    }

    private View inflateLayout(int resource, android.view.ViewGroup root, boolean attachToRoot) {
        return layoutInflater.inflate(resource, root, attachToRoot);
    }

    protected View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflateDefaultLayout(parent);
        DateRangeDataModel model = items.get(position);
        TextView textView = view.findViewById(R.id.tvSpinnerTitle);
        if (model.getTitle()=="")textView.setVisibility(View.GONE);
        else textView.setText(model.getTitle());
        textView.setHint("");
        return view;
    }

    private View getDefaultView(ViewGroup parent) {
        View view = inflateDefaultLayout(parent);
        TextView textView = view.findViewById(R.id.tvSpinnerTitle);
        textView.setText("");
        textView.setHint(hintResource);
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return position!=0;
    }
}