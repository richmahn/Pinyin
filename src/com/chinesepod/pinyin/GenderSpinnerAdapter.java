/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Adapter for the Gender spinner
 * 
 **/

package com.chinesepod.pinyin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class GenderSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private String[] mGenders;  

    public GenderSpinnerAdapter(Context context, String[] genders){
        mContext = context;
        mGenders = genders;
    }

    @Override
    public int getCount() {
        return mGenders.length;
    }

    @Override
    public Object getItem(int position) {
        return mGenders[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
        View spinView;
        if( convertView == null ){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            spinView = inflater.inflate(R.layout.gender_spinner, null);
        } else {
             spinView = convertView;
        }
        
        ImageView imageView = (ImageView) spinView.findViewById(R.id.icon);
        TextView textView = (TextView) spinView.findViewById(R.id.text);

        imageView.setImageResource(mContext.getResources().getIdentifier(mGenders[position].toLowerCase(), "drawable", mContext.getPackageName()));
        textView.setText(mGenders[position]);
        
        return spinView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
        {
    		ImageView imageView = new ImageView(mContext);
    		imageView.setImageResource(mContext.getResources().getIdentifier(mGenders[position].toLowerCase(), "drawable", mContext.getPackageName()));
    		return imageView;
        }
 }
