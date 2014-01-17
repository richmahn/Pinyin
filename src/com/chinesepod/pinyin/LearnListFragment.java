/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * LearnListFragment is a Fragment class for listing the different groups of slides
 */
package com.chinesepod.pinyin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LearnListFragment extends PinyinFragment {
	private View mView;
	private ListView mLearnListView;
	private LearnListArrayAdapter mLearnListAdapter;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState ){
		// inflate the main view and get the List view within it, then update the Fragment
		mView = inflater.inflate( R.layout.reference_list, container, false );
		mLearnListView = (ListView)mView.findViewById(android.R.id.list);
	    updateFragment();
		
		return mView;
	}
	
	
	/*
	 * Method for updating the fragment
	 */
	@Override
	public void updateFragment(){
		ArrayList<Map<String, String>> dataArrayList = new ArrayList<Map<String, String>>();
		
		// From the SLIDE_DATA array of slide info, for each slide(i = slide index) get the first slide(0) 
		// and the first item of the slide(0) as the title, and if there is a 2nd item of the first slide, get
		// the description(1)
		for(int i = 0; i < Configuration.SLIDE_DATA.length; ++i){
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", Configuration.SLIDE_DATA[i][0][0]);
			if( Configuration.SLIDE_DATA[i][0].length > 1){
				map.put("description", Configuration.SLIDE_DATA[i][0][1]);
			}
			else {
				map.put("description", "");
			}
			dataArrayList.add(map);
		}
		
		// Make a new list adapter
		mLearnListAdapter = new LearnListArrayAdapter(getActivity(), dataArrayList);

        mLearnListView.setAdapter(mLearnListAdapter);
        // When item is clicked in the list adapter, set a LearnSlidesFragment corrisponding to the item selected as the current fragment 
        mLearnListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View v, int position, long id) {
				Configuration.mCurrentSlideIndex = position; // set the current slide index in the Configuration class to save the state
				LearnSlidesFragment fragment = (LearnSlidesFragment)Fragment.instantiate(getActivity(), LearnSlidesFragment.class.getName());
				((MainActivity)getActivity()).setFragment(fragment);
			}
		});
	}
}
