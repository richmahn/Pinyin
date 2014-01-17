/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Class to inherit for all Activity classes, extends FragmentActivity
 **/

package com.chinesepod.pinyin;

import java.io.File;

import com.chinesepod.pinyin.util.SystemUiHider;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class PinyinActivity extends FragmentActivity {
	private static final int REQUEST_RECORD_AUDIO = 0;
	private String mRecordAudioFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Specs request that this only be in portrait mode.
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

		setContentView(getLayoutResource()); // calls the function getLayoutResource() which can be overriden by subclasses
	}

	protected int getLayoutResource() {
		return R.layout.activity_frame;
	}

	/*
	 * Method for an activity to record audio
	 */
	public String recordAudio(String text, String audioFile, View v) {
		try{
			String filePath = PinyinApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()+File.separator+audioFile+".mp3";
			
			final MediaRecorder recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setOutputFile(filePath);
			recorder.prepare();
			final ProgressDialog mProgressDialog = new RecorderProgressDialog(this);
			final Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						sleep(10000);
						recorder.stop();
						recorder.release();
						mProgressDialog.dismiss();
					} catch (InterruptedException e) {
					}
				}
			};
			mProgressDialog.setTitle(text);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setMessage(text);
			mProgressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "Stop", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					thread.interrupt();
					mProgressDialog.dismiss();
					recorder.stop();
					recorder.release();
				}
			});

			mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface p1) {
					thread.interrupt();
					recorder.stop();
					recorder.release();
				}
			});
			recorder.start();

			thread.start();
			mProgressDialog.show();
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, R.string.problem_recording, Toast.LENGTH_SHORT).show();
		}
		return null;
	}

	
	/*
	 * Handle result from recording audio
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_RECORD_AUDIO) {
				String sourcePath = getRealPathFromURI(intent.getData());
				File sourceF = new File(sourcePath);
				String destPath = PinyinApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()+File.separator+mRecordAudioFile+".mp3";
				try {
					sourceF.renameTo(new File(destPath));
				} catch (Exception e) {
					Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	public String getRealPathFromURI(Uri contentUri) {
		String returnVal = "";
		
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		if( cursor != null && cursor.moveToFirst() ){
			returnVal = cursor.getString(column_index);
			cursor.close();
		}
		
		return returnVal;
	}

	class RecorderProgressDialog extends ProgressDialog {

		public RecorderProgressDialog(Context context) {
			super(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

		}
	}

	@Override
	protected void onStart() {
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this); // Add this method
	}

	@Override
	protected void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this); // Add this method
	}
}
