/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
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

public class ViewSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private String[] mViewNames;  

    public ViewSpinnerAdapter(Context context, String[] viewNames){
        mContext = context;
        mViewNames = viewNames;
    }

    @Override
    public int getCount() {
        return mViewNames.length;
    }

    @Override
    public Object getItem(int position) {
        return mViewNames[position];
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
            spinView = inflater.inflate(R.layout.view_spinner, null);
        } else {
             spinView = convertView;
        }
        
        TextView textView = (TextView) spinView.findViewById(R.id.text);
        textView.setText(mViewNames[position]);
        return spinView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
        {
    		ImageView imageView = new ImageView(mContext);
    		imageView.setImageResource(R.drawable.eye_thin);
    		return imageView;
        }
 }
