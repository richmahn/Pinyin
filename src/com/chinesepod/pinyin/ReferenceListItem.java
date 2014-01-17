/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 */
package com.chinesepod.pinyin;

public class ReferenceListItem {
    public String mText;
    public String mAudio;
    
    public ReferenceListItem(String text){
    	this(text, null);
    }
    
	public ReferenceListItem(String text, String audio) {
		if( text == null || text.isEmpty() ){
			return;
		}
		mText = text;
		if( audio != null && ! audio.isEmpty() ){
			mAudio = audio;
		}
		else {
			mAudio = mText;
		}
	}
}
