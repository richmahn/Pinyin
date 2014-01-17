/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Adapter for the Tone spinner
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

public class PinyinTonesSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
	private String[] mPinyinTonesDisplayArray;
	private String[] mPinyinTonesIdsArray;  

    public PinyinTonesSpinnerAdapter(Context context){
        mContext = context;
		mPinyinTonesDisplayArray = mContext.getResources().getStringArray(R.array.pinyin_tones_display);
		mPinyinTonesIdsArray = mContext.getResources().getStringArray(R.array.pinyin_tones_ids);
    }

    @Override
    public int getCount() {
        return mPinyinTonesDisplayArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mPinyinTonesDisplayArray[position];
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
            spinView = inflater.inflate(R.layout.pinyin_tones_spinner, null);
        } else {
             spinView = convertView;
        }
        
        ImageView imageView = (ImageView) spinView.findViewById(R.id.icon);
        TextView textView = (TextView) spinView.findViewById(R.id.text);

        imageView.setImageResource(mContext.getResources().getIdentifier("tone"+mPinyinTonesIdsArray[position], "drawable", mContext.getPackageName()));
        textView.setText(mPinyinTonesDisplayArray[position]);
        
        return spinView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
        {
    		ImageView imageView = new ImageView(mContext);
    		imageView.setImageResource(mContext.getResources().getIdentifier("tone"+mPinyinTonesIdsArray[position], "drawable", mContext.getPackageName()));
    		return imageView;
        }
 }
