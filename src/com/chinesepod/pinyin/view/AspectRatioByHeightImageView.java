/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * View for showing an image in aspect ratio by the given height to display the image
 */
package com.chinesepod.pinyin.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectRatioByHeightImageView extends ImageView {

	public AspectRatioByHeightImageView(Context context) {
		super(context);
	}

	public AspectRatioByHeightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AspectRatioByHeightImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Drawable drawable = getDrawable();
		if (drawable != null) {
			int height =  MeasureSpec.getSize(widthMeasureSpec);
			int dih = drawable.getIntrinsicHeight();
			if (dih > 0) {
				int width = height * drawable.getIntrinsicWidth() / dih;
				setMeasuredDimension(width, height);
			} else
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}