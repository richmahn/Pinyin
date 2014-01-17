/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Class to be inherited for all Fragment classes of the app.
 **/

package com.chinesepod.pinyin;

import android.app.Fragment;
import android.os.Bundle;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public abstract class PinyinFragment extends Fragment {
	protected EasyTracker mTracker;

	public abstract void updateFragment();
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mTracker = EasyTracker.getInstance(this.getActivity());
    }

    @Override
    public void onResume() {

        super.onResume();

        mTracker.set(Fields.SCREEN_NAME, getClass().getSimpleName());
        mTracker.send( MapBuilder.createAppView().build() );
    }
}
