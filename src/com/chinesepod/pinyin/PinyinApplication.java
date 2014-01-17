/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Class for the application
 **/

package com.chinesepod.pinyin;

import android.app.Application;
import android.content.Context;

public class PinyinApplication extends Application {
	private static PinyinApplication instance;
	
	/**
	 * Constructor
	 */
	public PinyinApplication() {
		instance = this;
	}
	
	/**
	 * @return PinyinApplication
	 */
	public static PinyinApplication getInstance(){
		return instance;
	}
	
	/**
	 * @return Context
	 */
	public static Context getContext() {
		return instance;
	}
}
