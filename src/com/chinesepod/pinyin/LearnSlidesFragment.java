/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Fragment for the Learn section of the MainActivity, showing the slide that has been selected from LearnListFragment
 */
package com.chinesepod.pinyin;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class LearnSlidesFragment extends PinyinFragment implements OnPageChangeListener {
	private View mView;
	private ViewPager mViewPager;
	private LearnSlidesPagerAdapter mLearnSlidesPagerAdapter;

	/*
	 * Create the view for the fragment and then call the method to update the Fragment with the slide ID stored
	 * in the Configuration class
	 */
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState )
	{
		mView = inflater.inflate( R.layout.learn, container, false );		
		updateFragment();
		return mView;
	}

	/*
	 * Do nothing on Page Scroll State Changed
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	/*
	 * Do nothing on Page Scrolled
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/*
	 * onPageSelected for the ViewPager
	 */
	@Override
	public void onPageSelected(int position) {
        String slideTitle = Configuration.SLIDE_DATA[Configuration.mCurrentSlideIndex][0][0];
        SharedPreferences prefs = getActivity().getSharedPreferences("Kana", Context.MODE_PRIVATE);
        
        mTracker.set(Fields.SCREEN_NAME, slideTitle+", slide "+(position+1));
        mTracker.send( MapBuilder.createAppView().build() );

        if( position == 0 ){
	        if( slideTitle != null && ! slideTitle.isEmpty() && prefs.getInt("SlideStatus-"+slideTitle, Configuration.SLIDE_STATUS_NEW) != Configuration.SLIDE_STATUS_COMPLETE ){
	        	prefs.edit().putInt("SlideStatus-"+slideTitle, Configuration.SLIDE_STATUS_STUDYING).commit();
	        }
		}
		if( position == (Configuration.SLIDE_DATA[Configuration.mCurrentSlideIndex].length - 2)){
	        if( slideTitle != null && ! slideTitle.isEmpty() ){
	        	prefs.edit().putInt("SlideStatus-"+slideTitle, Configuration.SLIDE_STATUS_COMPLETE).commit();
		        Toast.makeText(getActivity(), "You have completed this lesson.", Toast.LENGTH_SHORT).show();
	        }
		}
	}

	/*
	 * Method called whenever the Fragment needs to be updated.
	 * CirclePageIndicator will be used in the "indicator" view for showing which slide the user is viewing
	 * A new LearnSlidesPagerAdapter is instantiated, set for the ViewPager, and first page is set to 0, the first slide
	 */
	@Override
	public void updateFragment() {
        mViewPager = (ViewPager)mView.findViewById(R.id.pager);
        CirclePageIndicator indicator = (CirclePageIndicator)mView.findViewById(R.id.indicator);

        mLearnSlidesPagerAdapter = new LearnSlidesPagerAdapter(getActivity(), Configuration.SLIDE_DATA[Configuration.mCurrentSlideIndex]);
        mViewPager.setAdapter(mLearnSlidesPagerAdapter);

        indicator.setViewPager(mViewPager);
        indicator.setOnPageChangeListener(this);
        
        onPageSelected(0);
	}
}