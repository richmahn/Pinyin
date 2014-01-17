/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReferenceChartFragment extends PinyinFragment {
	private View mView;
	private int mColumnWidth;
	private int mColumnHeight;
	private static TableLayout mPinyinTableLayout;
	private TableRow mChartHeaderRow;
	private ProgressDialog pd;
	private ScrollView mInnerLayout;
	public int increment = 0;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState ){
		mView = inflater.inflate( R.layout.reference_chart, container, false );
		
		mChartHeaderRow = (TableRow)mView.findViewById(R.id.chart_header_row);
		mInnerLayout = (ScrollView)mView.findViewById(R.id.innerLayout);

	    mColumnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.chart_column_width), getResources().getDisplayMetrics());
	    mColumnHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.chart_column_height), getResources().getDisplayMetrics());

	    updateFragment();
			
		return mView;
	}
	
	@Override
	public void updateFragment(){
		mChartHeaderRow.removeAllViews();
		mInnerLayout.removeAllViews();

		for(int i = 0; i < Configuration.PINYIN_TABLE_DATA[0].length; ++i){
			mChartHeaderRow.addView(prepareChartHeaderCell(Configuration.PINYIN_TABLE_DATA[0][i]));
		}

        if( mPinyinTableLayout == null ){
    		pd = new ProgressDialog(getActivity());
            pd.setMessage("Building Pinyin Chart");
            pd.setTitle("Please wait....");
            pd.setIndeterminate(true);
            pd.setMax(10);
            pd.show();

            mPinyinTableLayout = new TableLayout(getActivity());
        	ThreadRender thread = new ThreadRender();
        	thread.start();
        }
        else {
        	if( mPinyinTableLayout.getParent() != null ){
        		((ScrollView)mPinyinTableLayout.getParent()).removeView(mPinyinTableLayout);
        	}
        	mInnerLayout.addView(mPinyinTableLayout);
        }
	}
	
	private class ThreadRender extends Thread {
	
		public void run() {
			Looper.prepare();

			for(int i = 1; i < Configuration.PINYIN_TABLE_DATA.length; ++i){
				TableRow tableRow = new TableRow(getActivity());
				for(int j = 0; j < Configuration.PINYIN_TABLE_DATA[i].length; ++j){
					TextView textView = prepareChartCell(Configuration.PINYIN_TABLE_DATA[i][j], (j > 0)); 
					tableRow.addView(textView);
				}
				mPinyinTableLayout.addView(tableRow);
				increment ++;
                handler.sendEmptyMessage(101);
			}
			handler.sendEmptyMessage(102);
		}
	}
	
	private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 102:
                mInnerLayout.addView(mPinyinTableLayout);
                pd.dismiss();
                break;
            case 101:
                Log.i("Test", " " + increment);
                pd.setProgress(increment);
                break;
            }
        }
	};
	
	private TextView prepareChartHeaderCell(String text) {
	    TextView textView = new TextView(getActivity());
    	textView.setLayoutParams(new TableRow.LayoutParams(mColumnWidth, mColumnHeight));
		textView.setBackgroundResource(R.drawable.chart_cell_normal);
		
	    textView.setLayoutParams(new TableRow.LayoutParams(mColumnWidth, mColumnHeight));
	    textView.setGravity(Gravity.CENTER);
	    textView.setTextSize(getResources().getDimension(R.dimen.chart_column_textsize));
	    textView.setText(text);
	    textView.setTypeface(null, Typeface.BOLD);
	    
	    return textView;
	}

	private TextView prepareChartCell(final String text, boolean notSideHeader) {
	    TextView textView = new TextView(getActivity());
    	textView.setLayoutParams(new TableRow.LayoutParams(mColumnWidth, mColumnHeight));
		textView.setBackgroundResource(R.drawable.chart_cell_normal);
		
	    textView.setLayoutParams(new TableRow.LayoutParams(mColumnWidth, mColumnHeight));
	    textView.setGravity(Gravity.CENTER);
	    textView.setTextSize(getResources().getDimension(R.dimen.chart_column_textsize));
	    textView.setText(text);
	    
	    final String audio = text.replace("ü", "v");
		if( notSideHeader ){
			if( audio != null && ! audio.isEmpty() ){
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						Activity activity = getActivity();
						int tone = ((MainActivity)getActivity()).getPinyinTone();
						String gender = ((MainActivity)getActivity()).getAudioGender();
						
						if( tone > 0 && tone <= 5 ){
							String audioFileName = gender+"_"+audio+tone;
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
//						   		file.close();
								mp.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										v.setBackgroundResource(R.drawable.chart_cell_normal);
										mp.release();
									}
								});
								mp.prepare();
								mp.start();
								v.setBackgroundResource(R.drawable.chart_cell_pressed);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else {
							try {
								int audioResourceId1 = getResources().getIdentifier(gender+"_"+audio+"1", "raw", getActivity().getPackageName());
								int audioResourceId2 = getResources().getIdentifier(gender+"_"+audio+"2", "raw", getActivity().getPackageName());
								int audioResourceId3 = getResources().getIdentifier(gender+"_"+audio+"3", "raw", getActivity().getPackageName());
								int audioResourceId4 = getResources().getIdentifier(gender+"_"+audio+"4", "raw", getActivity().getPackageName());
								
								if( audioResourceId1 < 1 || audioResourceId2 < 1 || audioResourceId3 < 1 || audioResourceId4 < 1){
									return;
								}
								
								final MediaPlayer mp1 = new MediaPlayer();
								final MediaPlayer mp2 = new MediaPlayer();
								final MediaPlayer mp3 = new MediaPlayer();
								final MediaPlayer mp4 = new MediaPlayer();
								
								AudioManager am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
								int streamVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

								mp1.setAudioStreamType(AudioManager.STREAM_MUSIC);
								mp1.setVolume(streamVolume, streamVolume);
								mp2.setAudioStreamType(AudioManager.STREAM_MUSIC);
								mp2.setVolume(streamVolume, streamVolume);
								mp3.setAudioStreamType(AudioManager.STREAM_MUSIC);
								mp3.setVolume(streamVolume, streamVolume);
								mp4.setAudioStreamType(AudioManager.STREAM_MUSIC);
								mp4.setVolume(streamVolume, streamVolume);
							    	
								AssetFileDescriptor file1 = getActivity().getResources().openRawResourceFd(audioResourceId1);
								mp1.setDataSource(file1.getFileDescriptor(), file1.getStartOffset(), file1.getLength());

								AssetFileDescriptor file2 = getActivity().getResources().openRawResourceFd(audioResourceId2);
								mp2.setDataSource(file2.getFileDescriptor(), file2.getStartOffset(), file2.getLength());

								AssetFileDescriptor file3 = getActivity().getResources().openRawResourceFd(audioResourceId3);
								mp3.setDataSource(file3.getFileDescriptor(), file3.getStartOffset(), file3.getLength());

								AssetFileDescriptor file4 = getActivity().getResources().openRawResourceFd(audioResourceId4);
								mp4.setDataSource(file4.getFileDescriptor(), file4.getStartOffset(), file4.getLength());
								
								mp1.prepare();
								mp2.prepare();
								mp3.prepare();
								mp4.prepare();

								mp1.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										mp1.release();
										mp2.start();
									}
								});
								mp2.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										mp2.release();
										mp3.start();
									}
								});
								mp3.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										mp3.release();
										mp4.start();
									}
								});
								mp4.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										v.setBackgroundResource(R.drawable.chart_cell_normal);
										mp4.release();
									}
								});
								
								mp1.start();
								v.setBackgroundResource(R.drawable.chart_cell_pressed);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		}
	    else {
	    	textView.setTypeface(null, Typeface.BOLD);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					ReferenceListFragment fragment = (ReferenceListFragment)Fragment.instantiate(getActivity(), ReferenceListFragment.class.getName());
					fragment.setInitialSound(text);
					((MainActivity)getActivity()).setFragment(fragment);
				}
			});
	    }
	    
	    return textView;
	}
}
