/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;
 
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class ReferenceListArrayAdapter extends ArrayAdapter<ReferenceListItem> {
 
    Context mContext;
    int mLayoutResourceId;
    ArrayList<ReferenceListItem> mDataArray = null;
 
    public ReferenceListArrayAdapter(Context context, ArrayList<ReferenceListItem> dataArray) {
        super(context, R.layout.reference_list_row_item, dataArray);
 
        mLayoutResourceId = R.layout.reference_list_row_item;
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
 
        ReferenceListItem item = mDataArray.get(position);
 
        TextView pinyinItem = (TextView) convertView.findViewById(R.id.pinyin);

        pinyinItem.setText(item.mText);
 
        return convertView;
    }
 
}
