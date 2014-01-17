/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SoundRecorderDialogFragment extends DialogFragment {

	private String mText;
	private String mAudioFileName;
	public MediaRecorder mRecorder;
	public boolean isRecording;
	private ImageButton mRecordButton;
	private ImageButton mPlaybackButton;
	private ImageButton mAudioButton;
	public Thread mThread;
	protected MediaPlayer mOriginalAudioMediaPlayer = null;
	protected boolean isPlayingOriginalAudio = false;
	protected boolean isPlayingPlayback = false;
	protected MediaPlayer mPlaybackMediaPlayer = null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    View v = inflater.inflate(R.layout.sound_recorder, null);
	    
	    TextView textView = (TextView)v.findViewById(R.id.text);
	    textView.setText(mText);
	    
	    mAudioButton = (ImageButton)v.findViewById(R.id.orig_audio_button);
	    mRecordButton = (ImageButton)v.findViewById(R.id.record_button);
	    mPlaybackButton = (ImageButton)v.findViewById(R.id.playback_button);
	    
	    mAudioButton.setOnClickListener(new AudioButtonOnClickListener());
	    
	    mRecordButton.setOnClickListener(new RecordButtonOnClickListener());
	    
	    mPlaybackButton.setOnClickListener(new PlaybackButtonOnClickListener());
	    
	    builder.setView(v);
	    return builder.create();
	}

	public void setText(String text) {
		mText = text;
	}
	
	public void setAudioFile(String audioFileName) {
		mAudioFileName = audioFileName;
	}
	
	@Override
	public void onDestroyView(){
		if( isRecording ){
			mRecordButton.performClick();
		}
		if( isPlayingOriginalAudio && mOriginalAudioMediaPlayer != null ){
			mOriginalAudioMediaPlayer.stop();
			mOriginalAudioMediaPlayer.release();
		}
		if( isPlayingPlayback && mPlaybackMediaPlayer != null ){
			mPlaybackMediaPlayer.stop();
			mPlaybackMediaPlayer.release();
		}
		super.onDestroyView();
	}
	

	class AudioButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			if( isPlayingOriginalAudio && mOriginalAudioMediaPlayer != null ){
				try {
					mOriginalAudioMediaPlayer.stop();
					mOriginalAudioMediaPlayer.release();
					((ImageButton)v).setImageResource(R.drawable.sound_recorder_audio_button);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				mOriginalAudioMediaPlayer = null;
				isPlayingOriginalAudio = false;
			}
			else {
				try {
					int audioResourceId = getActivity().getResources().getIdentifier(mAudioFileName, "raw", getActivity().getPackageName());
					
					if( audioResourceId < 1 ){
						return;
					}
					
					((ImageButton)v).setImageResource(R.drawable.sound_recorder_audio_playing_button);
					
				   	mOriginalAudioMediaPlayer = new MediaPlayer();
					AudioManager am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
					int streamVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
				    mOriginalAudioMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				    mOriginalAudioMediaPlayer.setVolume(streamVolume, streamVolume);

				   	AssetFileDescriptor file = getActivity().getResources().openRawResourceFd(audioResourceId);
				   	mOriginalAudioMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				    mOriginalAudioMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				    	@Override
				    	public void onCompletion(MediaPlayer mp) {
				    		mOriginalAudioMediaPlayer.release();
				    		mOriginalAudioMediaPlayer = null;
							isPlayingOriginalAudio = false;
							((ImageButton)v).setImageResource(R.drawable.sound_recorder_audio_button);
				    	}
				    });
			        mOriginalAudioMediaPlayer.prepare();
			        mOriginalAudioMediaPlayer.start();
			        isPlayingOriginalAudio = true;
				} catch (Exception e) {
			   		e.printStackTrace();
			   	}
			}
		}
	}

	class RecordButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			
			if( ! isRecording ){
				((ImageButton)v).setImageResource(R.drawable.sound_recorder_recording_button);
				try {
					String filePath = PinyinApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()+File.separator+mAudioFileName+".mp3";
					
					mRecorder = new MediaRecorder();
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile(filePath);
					
					final Handler handler = new Handler();
					mThread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(3000);
								handler.post(new Runnable() {
									@Override
									public void run() {
										v.performClick();
									}
								});
							} catch (InterruptedException e) {
							}
						}
					});
					
					mThread.start();
					mRecorder.prepare();
					mRecorder.start();
					isRecording = true;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else {
				isRecording = false;
				
				mRecorder.stop();
				mRecorder.release();
				mThread.interrupt();
				((ImageButton)v).setImageResource(R.drawable.sound_recorder_record_button);
			}
		}
	}
	
	class PlaybackButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			if( isPlayingPlayback && mPlaybackMediaPlayer != null ){
				try {
					mPlaybackMediaPlayer.stop();
		    		mPlaybackMediaPlayer.release();
		    		mPlaybackMediaPlayer = null;
		    		isPlayingPlayback = false;
					((ImageButton)v).setImageResource(R.drawable.sound_recorder_playback_button);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else {
				String filePath = PinyinApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()+File.separator+mAudioFileName+".mp3";

				File file = new File(filePath);
				
				if( ! file.exists() ){
					Toast.makeText(getActivity(), "You have not yet made a recording of this sound.", Toast.LENGTH_SHORT).show();
					return;
				}
				
				try {
					((ImageButton)v).setImageResource(R.drawable.sound_recorder_playback_playing_button);

					mPlaybackMediaPlayer = new MediaPlayer();
					AudioManager am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
					int streamVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
					mPlaybackMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mPlaybackMediaPlayer.setVolume(streamVolume, streamVolume);

					mPlaybackMediaPlayer.setDataSource(file.getAbsolutePath());
					mPlaybackMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				    	@Override
				    	public void onCompletion(MediaPlayer mp) {
				    		mPlaybackMediaPlayer.release();
				    		mPlaybackMediaPlayer = null;
				    		isPlayingPlayback = false;
							((ImageButton)v).setImageResource(R.drawable.sound_recorder_playback_button);
				    	}
				    });
					mPlaybackMediaPlayer.prepare();
					mPlaybackMediaPlayer.start();
					isPlayingPlayback = true;
				} catch (Exception e) {
			   		e.printStackTrace();
			   	}
			}
		}
	}
}
