/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * View for showing an image in aspect ratio by the given width to display the image
 */
package com.chinesepod.pinyin.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectRatioByWidthImageView extends ImageView {

	public AspectRatioByWidthImageView(Context context) {
		super(context);
	}

	public AspectRatioByWidthImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AspectRatioByWidthImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Drawable drawable = getDrawable();
		if (drawable != null) {
			int width =  MeasureSpec.getSize(widthMeasureSpec);
			int diw = drawable.getIntrinsicWidth();
			if (diw > 0) {
				int height = width * drawable.getIntrinsicHeight() / diw;
				setMeasuredDimension(width, height);
			} else
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}