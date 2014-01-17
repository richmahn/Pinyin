/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ReferenceListFragment extends PinyinFragment {
	public final static int REFERENCE_MODE_CHART = 0;
	public final static int REFERENCE_MODE_LIST = 1;
	
	public int mReferenceMode = REFERENCE_MODE_CHART;
	public String mInitialSound = null;
	
	private View mView;
	private ListView mKanaListView;
	private ReferenceListArrayAdapter mReferenceListAdapter;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState ){
		mView = inflater.inflate( R.layout.reference_list, container, false );
		mKanaListView = (ListView)mView.findViewById(android.R.id.list);
	    updateFragment();
		
		return mView;
	}
	
	public void setInitialSound(String initialSound) {
		mInitialSound = initialSound;
	}

	@Override
	public void updateFragment(){
		ArrayList<ReferenceListItem> dataArrayList = new ArrayList<ReferenceListItem>();
		
		for(int i = 0; i < Configuration.PINYIN_TABLE_DATA.length; ++i){
			if( mInitialSound == null || mInitialSound.equals(Configuration.PINYIN_TABLE_DATA[i][0]) ){ 
				for(int j = 1; j < Configuration.PINYIN_TABLE_DATA[i].length; ++j){
					if( Configuration.PINYIN_TABLE_DATA[i][j] != null && ! Configuration.PINYIN_TABLE_DATA[i][j].isEmpty() ){
						dataArrayList.add(new ReferenceListItem(Configuration.PINYIN_TABLE_DATA[i][j]));
					}
				}
				break;
			}
		}
		
		mReferenceListAdapter = new ReferenceListArrayAdapter(getActivity(), dataArrayList);

        mKanaListView.setAdapter(mReferenceListAdapter);
        mKanaListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View v, int position, long id) {
				ReferenceListItem item = mReferenceListAdapter.getItem(position);
				
				if( item.mAudio != null && ! item.mAudio.isEmpty() ){
					String audioFileName = ((MainActivity)getActivity()).getAudioGender()+"_"+item.mAudio;
					int audioResourceId = getResources().getIdentifier(audioFileName, "raw", getActivity().getPackageName());
							
					if( audioResourceId < 1 ){
						return;
					}
								
					try {
					   	MediaPlayer mp = new MediaPlayer();
						AudioManager am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
						int streamVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
					    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
					    mp.setVolume(streamVolume, streamVolume);
						    	
					   	AssetFileDescriptor file = getActivity().getResources().openRawResourceFd(audioResourceId);
					   	mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
					    mp.setOnCompletionListener(new OnCompletionListener() {
					    	@Override
					    	public void onCompletion(MediaPlayer mp) {
					    		mp.release();
					    	}
					    });
				        mp.prepare();
				        mp.start();
					} catch (Exception e) {
				   		e.printStackTrace();
				   	}
				}
			};
		});
	}
}
