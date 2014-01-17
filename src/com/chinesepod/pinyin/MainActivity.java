/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinesepod.pinyin.util.SystemUiHider;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends PinyinActivity {
	private ActionBar mActionBar;
	private Fragment mCurrentFragment;

	private int mPinyinTone = 1; // Tone set to 1st Tone by default, Tone can be 1 - 4
	private String mAudioGender = Configuration.AUDIO_GENDER_FEMALE; // Set default audio gender to Female
	private MenuItem mGenderSpinnerItem;
	public String mCurrentTab = Configuration.TAB_LEARN; // Default to starting with the learn tab selected
	protected int mViewType = Configuration.VIEW_TYPE_CHART; // For the reference tab, set the default view to chart view

    private List<Map<String, Object>> mBackStack = new ArrayList<Map<String, Object>>(); // The history for the back button is kept as a stack with this ArrayList of a Map objects
	private MenuItem mReferenceViewItem; // Menu item at the top
	private MenuItem mPinyinToneSpinnerItem; // Menu itme at the top
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int mode;

		// Mode of the navigation bar of the Action Bar can change, get current mode from SavedInstanceState or from SharedPreferences
		if (savedInstanceState != null) {
			mode = savedInstanceState.getInt( "mode", ActionBar.NAVIGATION_MODE_TABS );
		}
		else {
			mode = getSharedPreferences(getApplication().getPackageName(), MODE_PRIVATE).getInt(Configuration.PREFERENCES_NAVIGATION_MODE, ActionBar.NAVIGATION_MODE_TABS);
		}
	    
		// Call the parent's constructor
	    super.onCreate(savedInstanceState);

	    // Set up the action bar at the top
		mActionBar = getActionBar();
		if( mActionBar != null){
			mActionBar.setTitle(R.string.app_name);
			mActionBar.setHomeButtonEnabled(false); // We are only one Activity for this app, so no Home/back button
			mActionBar.setDisplayHomeAsUpEnabled(false);
			
			// Based on the Navigation Mode, we call the function to set up the widgets
			if (mode == ActionBar.NAVIGATION_MODE_TABS)
			{
				// Use tabs
				setTabNavigation( mActionBar );
			} else
			{
				// Use lists
				setListNavigation( mActionBar );
			}
		}
		
		// Ad for ChinesePo app at the bottom
		findViewById(R.id.ad_banner).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Configuration.CPOD_APP_URL));
				try {
					startActivity(i);
				} catch (ActivityNotFoundException x) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Configuration.CPOD_SITE_URL)));
				}
			}
		});
	}

	/*
	 * Setup the action bar menu at the top
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);

	    // Find the spinner for the Tone and then call method to set it up
		mPinyinToneSpinnerItem = menu.findItem( R.id.menu_pinyin_tone_spinner );
		setupPinyinToneSpinner( mPinyinToneSpinnerItem );

		// Find the spinner for the Audio Gender and then call method to set it up
		mGenderSpinnerItem = menu.findItem( R.id.menu_audio_gender_spinner );
		setupGenderSpinner( mGenderSpinnerItem );
		
		// Find the Reference View item and set it to invisible if the current tab isn't "Reference"
		mReferenceViewItem = menu.findItem(R.id.menu_reference_view);
	    if( ! mCurrentTab.equals(Configuration.TAB_REFERENCE) ){
	    	mReferenceViewItem.setVisible(false);
	    }
		
	    return true;
	}
	
	/*
	 * Called when item in action bar is selected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_toggle:
			// Menu_toggle toggles the mode of the navigation bar, lists or tabs
			ActionBar ab = getActionBar();
			String tabBeforeSwitch = mCurrentTab;
			if (ab.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS)
			{
				// Mode has been toggled, previous was tabs, so now we set it to lists
				setListNavigation( ab );
			} else
			{
				// Mode has been toggled, previous was lists, so now we set it to tabs
				setTabNavigation( ab );
			}
			
			if( tabBeforeSwitch != null && tabBeforeSwitch.equals(Configuration.TAB_REFERENCE) ){
				// If the tab before the switch was reference (showing the reference fragment), then make the selected navigation item 2nd item (1 = Reference, base 0)
				mActionBar.setSelectedNavigationItem(1);
			}
			else {
				// If the tab before the switch was not reference, then make the selected navigation item the 1st item (0 = Learn, base 0)
				mActionBar.setSelectedNavigationItem(0);
			}
			return true;
		case R.id.menu_reference_view:
			// Case for when the reference view changes (Chart or List)
			
			if( mViewType == Configuration.VIEW_TYPE_CHART ){
				// If the type was chart, then make the type list
				mViewType = Configuration.VIEW_TYPE_LIST;
				mReferenceViewItem.setTitle("Chart View");
			}
			else {
				// If the type was list, then make the type chart
				mViewType = Configuration.VIEW_TYPE_CHART;
				mReferenceViewItem.setTitle("List View");
			}
			
			// Now handle the history stack
			if( mViewType == Configuration.VIEW_TYPE_LIST && mCurrentFragment instanceof ReferenceChartFragment ){
				mBackStack.remove(mBackStack.size()-1);
				PinyinFragment fragment = (PinyinFragment)Fragment.instantiate(MainActivity.this, ReferenceListFragment.class.getName());
				setFragment(fragment);
			}
			if( mViewType == Configuration.VIEW_TYPE_CHART && mCurrentFragment instanceof ReferenceListFragment ){
				mBackStack.remove(mBackStack.size()-1);
				PinyinFragment fragment = (PinyinFragment)Fragment.instantiate(MainActivity.this, ReferenceChartFragment.class.getName());
				setFragment(fragment);
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * Method sets up the Pinyin Tone Spinner, setting the adapter and OnItemSelectedListener
	 */
	private void setupPinyinToneSpinner( MenuItem item )
	{
		View view = item.getActionView();
		if (view instanceof Spinner)
		{
			Spinner spinner = (Spinner) view;
			spinner.setAdapter( new PinyinTonesSpinnerAdapter(this) );
			spinner.setOnItemSelectedListener(new PinyinTonesOnItemSelectedListener());
		}
	}

	/*
	 * Method sets up the Pinyin Tone Spinner, setting the adapter and OnItemSelectedListener
	 */
	private void setupGenderSpinner( MenuItem item )
	{
		View view = item.getActionView();
		if (view instanceof Spinner)
		{
			Spinner spinner = (Spinner) view;
			spinner.setAdapter( new GenderSpinnerAdapter(this, getResources().getStringArray(R.array.genders)) );
			spinner.setOnItemSelectedListener(new GenderOnItemSelectedListener());
		}
	}

	// Listener for the Tabs
	private class MyTabListener implements ActionBar.TabListener
	{
		@Override
		public void onTabReselected( Tab tab, FragmentTransaction ft ){
			switchFragmentStack((String)tab.getText());
		}

		@Override
		public void onTabSelected( Tab tab, FragmentTransaction ft ){
			switchFragmentStack((String)tab.getText());
		}

		@Override
		public void onTabUnselected( Tab tab, FragmentTransaction ft ){
		}
	}

	/*
	 * Sets up the Tab Navigation when Tabs are to be shown
	 */
	private void setTabNavigation( ActionBar actionBar )
	{
		mActionBar.setDisplayUseLogoEnabled(true);
		
		actionBar.removeAllTabs();
		actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
		getSharedPreferences(getApplication().getPackageName(), MODE_PRIVATE).edit().putInt(Configuration.PREFERENCES_NAVIGATION_MODE, ActionBar.NAVIGATION_MODE_TABS).commit();
		actionBar.setTitle( R.string.app_name );

		Tab tab = actionBar
				.newTab()
				.setText( Configuration.TAB_LEARN )
				.setTabListener(new MyTabListener());
		actionBar.addTab( tab );

		tab = actionBar
				.newTab()
				.setText( Configuration.TAB_REFERENCE )
				.setTabListener(new MyTabListener());
		actionBar.addTab( tab );
	}

	/*
	 * Sets up the list navigation when lists are to be shown
	 */
	private void setListNavigation( ActionBar actionBar )
	{
		actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
		getSharedPreferences(getApplication().getPackageName(), MODE_PRIVATE).edit().putInt(Configuration.PREFERENCES_NAVIGATION_MODE, ActionBar.NAVIGATION_MODE_LIST).commit();
		mActionBar.setDisplayUseLogoEnabled(false);

		final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put( "title", Configuration.TAB_LEARN );
		data.add( map );
		map = new HashMap<String, String>();
		map.put( "title", Configuration.TAB_REFERENCE );
		data.add( map );
		final SimpleAdapter adapter = new SimpleAdapter( this, data, android.R.layout.simple_spinner_dropdown_item, new String[] { "title" }, new int[] { android.R.id.text1 } );
		actionBar.setListNavigationCallbacks( adapter, new OnNavigationListener() {
					@Override
					public boolean onNavigationItemSelected( int itemPosition, long itemId )
					{
						Map<String, String> map = (Map<String, String>)adapter.getItem(itemPosition);
						switchFragmentStack(map.get("title"));
						return true;
					}
				} );
	}

	@Override
	protected void onSaveInstanceState( Bundle outState )
	{
		outState.putInt( "mode", getActionBar().getNavigationMode() );
		super.onSaveInstanceState( outState );
	}

	public int getPinyinTone() {
		return mPinyinTone;
	}
	
	public String getAudioGender(){
		return mAudioGender;
	}
	
	@Override
	protected int getLayoutResource(){
		return R.layout.activity_frame;
	}
	
	public void switchFragmentStack(String tabName) {
		if( tabName == null || tabName.isEmpty() ){
			return;
		}
		
		mCurrentTab = tabName;

		PinyinFragment fragment = null;
		
		for(int i = mBackStack.size() - 1; i >= 0; --i){
			Map<String, Object> map = mBackStack.get(i);
			if( map.get("tab_name").equals(tabName) ){
				fragment = (PinyinFragment)map.get("fragment");
				mBackStack.remove(i);
				break;
			}
		}
		
		if( tabName.equals(Configuration.TAB_LEARN) ){
			if( fragment == null ){
				fragment = (PinyinFragment)Fragment.instantiate(this, LearnListFragment.class.getName());
			}
			if( mReferenceViewItem != null ){
		    	mReferenceViewItem.setVisible(false);
			}
		}
		else if( tabName.equals(Configuration.TAB_REFERENCE) ){
			if( fragment == null ){
				if( mViewType == Configuration.VIEW_TYPE_LIST ){
					fragment = (PinyinFragment)Fragment.instantiate(this, ReferenceListFragment.class.getName());
				}
				else {
					fragment = (PinyinFragment)Fragment.instantiate(this, ReferenceChartFragment.class.getName());
				}
			}
			if( mReferenceViewItem != null ){
		    	mReferenceViewItem.setVisible(true);
			}
		}
		
		setFragment(fragment);
	}
	public void setFragment(PinyinFragment fragment){
		if( fragment == null ){
			return;
		}
		
		mCurrentFragment = fragment;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tab_name", mCurrentTab);
		map.put("fragment", fragment);
		mBackStack.add(map);
		
		FragmentManager fm = getFragmentManager();
		fm.executePendingTransactions();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.frame_layout, fragment, fragment.getClass().getName());
		ft.commit();
	}

	/*
	 * Override the onBackPressed to do our own History stack to change fragment data or change the whole fragment between Learn slides and Reference chart/list
	 */
	@Override
	public void onBackPressed(){
		if( mBackStack.size() > 1 ){
			Map<String, Object> map = mBackStack.get(mBackStack.size()-1);
			mBackStack.remove(mBackStack.size()-1);
			
			map = mBackStack.get(mBackStack.size()-1);
			String tabName = (String)map.get("tab_name");
			
			if( mCurrentTab.equals(tabName) ){
				mBackStack.remove(mBackStack.size()-1);
				setFragment((PinyinFragment)map.get("fragment"));
			}
			else {
				if( tabName != null && tabName.equals(Configuration.TAB_REFERENCE) ){
					mActionBar.setSelectedNavigationItem(1);
				}
				else {
					mActionBar.setSelectedNavigationItem(0);
				}
			}
		}
		else {
			super.onBackPressed();
		}
	}

	class PinyinTonesOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position, long arg3) {
			try {
				mPinyinTone = Integer.parseInt(getResources().getStringArray(R.array.pinyin_tones_ids)[position]);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	class GenderOnItemSelectedListener implements OnItemSelectedListener {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View v, int position, long arg3) {
				switch(position){
				case 0:
					mAudioGender = Configuration.AUDIO_GENDER_FEMALE;
					break;
				case 1:
					mAudioGender = Configuration.AUDIO_GENDER_MALE;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	}

}
