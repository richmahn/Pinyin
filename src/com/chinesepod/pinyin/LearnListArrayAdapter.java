/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;
 
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class LearnListArrayAdapter extends ArrayAdapter<Map<String, String>> {
 
    Context mContext;
    int mLayoutResourceId;
    ArrayList<Map<String, String>> mDataArray = null;
 
    public LearnListArrayAdapter(Context context, ArrayList<Map<String, String>> dataArray) {
    	super(context, R.layout.learn_list_row_item, dataArray);

    	mLayoutResourceId = R.layout.learn_list_row_item;
    	mContext = context;
        mDataArray = dataArray;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
        }
 
        Map<String, String> map = mDataArray.get(position);
        String title = map.get("title");
        String description = map.get("description");
        
        TextView slideTitle = (TextView) convertView.findViewById(R.id.slide_title);
        TextView slideDescription = (TextView) convertView.findViewById(R.id.slide_description);
        TextView slideGroupStatus = (TextView) convertView.findViewById(R.id.slide_group_status);
        
        slideTitle.setText(title);
        slideDescription.setText(description);
        
        int status = mContext.getSharedPreferences("Kana", Context.MODE_PRIVATE).getInt("SlideStatus-"+title, Configuration.SLIDE_STATUS_NEW);
        
        switch(status){
        case Configuration.SLIDE_STATUS_COMPLETE:
        	slideGroupStatus.setVisibility(View.VISIBLE);
        	slideGroupStatus.setText(mContext.getResources().getString(R.string.slide_status_complete));
        	slideGroupStatus.setBackgroundColor(mContext.getResources().getColor(R.color.slide_group_status_complete_backgroundcolor));
        	slideGroupStatus.setTextColor(mContext.getResources().getColor(R.color.slide_group_status_complete_textcolor));
        	break;
        case Configuration.SLIDE_STATUS_STUDYING:
        	slideGroupStatus.setVisibility(View.INVISIBLE);
        	break;
        case Configuration.SLIDE_STATUS_NEW:
        default:
        	slideGroupStatus.setVisibility(View.VISIBLE);
        	slideGroupStatus.setText(mContext.getResources().getString(R.string.slide_status_new));
        	slideGroupStatus.setBackgroundColor(mContext.getResources().getColor(R.color.slide_group_status_new_backgroundcolor));
        	slideGroupStatus.setTextColor(mContext.getResources().getColor(R.color.slide_group_status_new_textcolor));
        	break;
        }
        
        return convertView;
    }
}
