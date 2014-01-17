/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * PagerAdapter for the Learn section, for the LearnSlidesFragment's ViewPager
 */
package com.chinesepod.pinyin;

import java.util.ArrayList;

import com.chinesepod.pinyin.view.AspectRatioByWidthImageView;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LearnSlidesPagerAdapter extends PagerAdapter {
    Context mContext;
    String[][] mSlides;
    LayoutInflater mInflater;
	private TextView mSlideGroupStatusTextView;
	private int mSlideGroupStatus;
	private String mTitle;
 
    public LearnSlidesPagerAdapter(Context context, String[][] slides) {
        mContext = context;
        mSlides = slides; // Array of slides to be displayed by this pager
    }
 
    @Override
    public int getCount() {
        return mSlides.length - 1; // length minus the header, which is index 0, so we always display from index 1 to the last item
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView txtTitle;
        LinearLayout layoutBody;
        
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mInflater.inflate(R.layout.learn_slide, container, false);
 
        // On the first slide, we display a little status indicator, in the top left corner. If it is not the first slide, we hide it.
        if( position == 0 ){
            mTitle = mSlides[0][0]; // Title of the slide is always the first item in the first slide, which is never displayed as a slide
            mSlideGroupStatus = mContext.getSharedPreferences("Pinyin", Context.MODE_PRIVATE).getInt("SlideStatus-"+mTitle, Configuration.SLIDE_STATUS_NEW);
            mSlideGroupStatusTextView = (TextView)itemView.findViewById(R.id.slide_group_status);
            
            // Set up the onClickListener for the little status indicator so it toggles this group of slides' status (New => Studying => Completed => New)
        	mSlideGroupStatusTextView.setOnClickListener(new OnClickListener() {
        		@Override
        		public void onClick(View v) {
        			switch(mSlideGroupStatus){
        			case Configuration.SLIDE_STATUS_NEW:
        				mSlideGroupStatus = Configuration.SLIDE_STATUS_STUDYING;
        				break;
        			case Configuration.SLIDE_STATUS_STUDYING:
        				mSlideGroupStatus = Configuration.SLIDE_STATUS_COMPLETE;
        				break;
        			case Configuration.SLIDE_STATUS_COMPLETE:
        				mSlideGroupStatus = Configuration.SLIDE_STATUS_NEW;
        				break;
        			}
				
        			mContext.getSharedPreferences("Pinyin", Context.MODE_PRIVATE).edit().putInt("SlideStatus-"+mTitle, mSlideGroupStatus).commit();
				
        			showSlideGroupStatus();
        		}
        	});
        	
        	showSlideGroupStatus();
        }
        else {
        	itemView.findViewById(R.id.slide_group_status).setVisibility(View.GONE);
        }
        
        txtTitle = (TextView) itemView.findViewById(R.id.slide_title);
        layoutBody = (LinearLayout) itemView.findViewById(R.id.slide_body);
 
        // We get the array of strings for the given slide (position) that we are to show, adding 1 because
        // the first slide is not shown, only for listing purposes.
        String[] slideContent = mSlides[position+1];
        
        // The first String in slide's content is the Title, we set the TextView to this title.
       	txtTitle.setText(slideContent[0]);

       	// If there is only one item (only the title) then we just center the title in the parent,
       	// otherwise we need to position the Title at the top and display the content below
       	if( slideContent.length < 2 ){
       		// We only have a title for this slide, so position it in the very center of the view
        	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        	layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
        	txtTitle.setLayoutParams(layoutParams);
        	layoutBody.setVisibility(ViewPager.GONE);
        }
        else {
        	// Displaying remaining strings in slideContent String array
        	for(int i = 1; i < slideContent.length; ++i){
        		String text = slideContent[i];
        		
        		// Look to see if it is a <grid> tag for showing sounds and the media player
        		String gridPattern = "\\s*<grid\\s+([^>]+?)\\s*>\\s*";
        		if( text.matches(gridPattern) ){
        			if( txtTitle.getText() == null || txtTitle.getText().toString().isEmpty() ){
        				txtTitle.setVisibility(View.GONE);
        			}
        			TableLayout tableLayout = new TableLayout(mContext);
        			tableLayout.setStretchAllColumns(true);
        			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        			layoutParams.setMargins(1, 1, 0, 0);
					tableLayout.setLayoutParams(layoutParams);
        			        			
        			String gridAttributes = text.replaceAll(gridPattern, "$1"); 
        			
        			ArrayList<String> validInitials = new ArrayList<String>();
        			ArrayList<String> validFinals = new ArrayList<String>();
        			
        			// Show the finals for the given exercise
        			String finalsPattern = ".*finals=[\"']{0,1}([^ \"']+).*";
        			if( gridAttributes.matches(finalsPattern) ){
        				String finalsValue = gridAttributes.replaceAll(finalsPattern, "$1");
        				String[] initalValuesArray = finalsValue.split(",");
        				String rangePattern = "([^-]+)-(.*)";
        				for(String value : initalValuesArray){
        					if( value.matches(rangePattern) ){
        						String finalFirstValue = value.replaceAll(rangePattern, "$1");
        						String finalLastValue = value.replaceAll(rangePattern, "$2");
        						boolean inRange = false;
        						for(int colIndex = 1; colIndex < Configuration.PINYIN_TABLE_DATA[0].length; ++colIndex){
        							if( ! inRange && Configuration.PINYIN_TABLE_DATA[0][colIndex].equals(finalFirstValue) ){
        								inRange = true;
        							}
        							if( inRange ){
        								validFinals.add(Configuration.PINYIN_TABLE_DATA[0][colIndex]);
        							}
        							if( Configuration.PINYIN_TABLE_DATA[0][colIndex].equals(finalLastValue) ){
        								break;
        							}
        						}
        					}
        					else {
        						if( value.toLowerCase().equals("none") ){
            						validFinals.add("");
        						}
        						else {
        							validFinals.add(value);
        						}
        					}
        				}
        			}

        			// show the initials for the given exercise
        			String initialsPattern = ".*initials=[\"']{0,1}([^ \"']+).*";
        			if( gridAttributes.matches(initialsPattern) ){
        				String initialsValue = gridAttributes.replaceAll(initialsPattern, "$1");
        				String[] initalValuesArray = initialsValue.split(",");
        				String rangePattern = "([^-]+)-(.*)";
        				for(String value : initalValuesArray){
        					if( value.matches(rangePattern) ){
        						String initialFirstValue = value.replaceAll(rangePattern, "$1");
        						String initialLastValue = value.replaceAll(rangePattern, "$2");
        						boolean inRange = false;
        						for(int rowIndex = 1; rowIndex < Configuration.PINYIN_TABLE_DATA.length; ++rowIndex){
        							if( ! inRange && Configuration.PINYIN_TABLE_DATA[rowIndex][0].equals(initialFirstValue) ){
        								inRange = true;
        							}
        							if( inRange ){
        								validInitials.add(Configuration.PINYIN_TABLE_DATA[rowIndex][0]);
        							}
        							if( Configuration.PINYIN_TABLE_DATA[rowIndex][0].equals(initialLastValue) ){
        								break;
        							}
        						}
        					}
        					else {
        						if( value.toLowerCase().equals("none") ){
            						validInitials.add("");
        						}
        						else {
        							validInitials.add(value);
        						}
        					}
        				}
        			}
        			
        			// We will get the data from the PINYIN_TABLE_DATA. 
        			// Will transpose the data if the Finals array is larger than the Initials array (because we display this app in portrait mode)
        			String[][] pinyinTable = Configuration.PINYIN_TABLE_DATA;
        			if( validFinals.size() > validInitials.size() ){
        				pinyinTable = Configuration.PINYIN_TABLE_TRANSPOSED_DATA;
        				ArrayList<String> validFinalsTemp = new ArrayList<String>(validFinals);
        				validFinals = validInitials;
        				validInitials = validFinalsTemp;
        			}

        			// Set up the views for the grid's cell contents
        			for(int rowIndex = 0; rowIndex < pinyinTable.length; ++rowIndex){
        				if( rowIndex == 0 || validInitials.contains(pinyinTable[rowIndex][0]) ){
        					TableRow tableRow = new TableRow(mContext);
                			TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                			tableRow.setLayoutParams(rowLayoutParams);
        					for(int colIndex = 0; colIndex < pinyinTable[0].length; ++colIndex){
        						if(colIndex == 0 || validFinals.contains(pinyinTable[0][colIndex])){
        							LinearLayout linearLayout = new LinearLayout(mContext);
        							linearLayout.setOrientation(LinearLayout.VERTICAL);
        							linearLayout.setGravity(Gravity.CENTER);
        							linearLayout.setMinimumHeight(mContext.getResources().getDimensionPixelSize(R.dimen.slide_grid_cell_height));
        							
        							linearLayout.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT));

        							linearLayout.setBackgroundResource(R.drawable.slide_grid_cell);
        							
        							TextView textView = new TextView(mContext);
        	               			textView.setGravity(Gravity.CENTER);
        	               		 	textView.setText(pinyinTable[rowIndex][colIndex]);
        							
        							linearLayout.addView(textView);
        							
        							if( ! pinyinTable[rowIndex][colIndex].isEmpty() && colIndex > 0 && rowIndex > 0 ){
            							ImageView playButton = new ImageView(mContext);
            							playButton.setImageResource(R.drawable.play_button);
            							linearLayout.addView(playButton);
            		        			PopupSoundRecorderOnClickListener popupSoundRecorderOnClickListener = new PopupSoundRecorderOnClickListener(pinyinTable[rowIndex][colIndex]);
            		        			linearLayout.setOnClickListener(popupSoundRecorderOnClickListener);
        							}
        							
        							tableRow.addView(linearLayout);
        						}
        					}
        					tableLayout.addView(tableRow);
        				}
        			}
        			layoutBody.addView(tableLayout);
        		}
        		else {
            		String imagePattern = "\\s*<img\\s+src=([^>]+?)\\s*>\\s*";
            		if( text.matches(imagePattern) ){
            			if( txtTitle.getText() == null || txtTitle.getText().toString().isEmpty() ){
            				txtTitle.setVisibility(View.GONE);
            			}
            			String imageFile = text.replaceAll(imagePattern, "$1");
                		ImageView imageView = new AspectRatioByWidthImageView(mContext);
                    	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    	layoutParams.setMargins(0, 0, 0, 0);
                    	imageView.setLayoutParams(layoutParams);
                    	imageView.setPadding(0, 0, 0, 0);
                		imageView.setImageResource(mContext.getResources().getIdentifier(imageFile, "drawable", mContext.getPackageName()));
                		
                		layoutBody.addView(imageView);
            			
            		}
            		else {
                		TextView textView = new TextView(mContext);
                    	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    	textView.setLayoutParams(layoutParams);
                		textView.setGravity(Gravity.CENTER_HORIZONTAL);
                		textView.setText(text);
                		textView.setTextSize(mContext.getResources().getDimension(R.dimen.slide_body_text));
                		
                		layoutBody.addView(textView);
            		}
        		}

        	}
        }
        
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);
        
    }

    // Class for maing a Sound Recorder pop up
	class PopupSoundRecorderOnClickListener implements OnClickListener {
		String mText;
		
		public PopupSoundRecorderOnClickListener(String text){
			mText = text;
		}
		
		public void onClick(View v) {
			int tone = 1;
			if( mContext instanceof MainActivity ){
				tone = ((MainActivity)mContext).getPinyinTone();
			}
			
			SoundRecorderDialogFragment dialog = new SoundRecorderDialogFragment();
			String audioFile = mText.replace("ü", "v");
			audioFile = ((MainActivity)mContext).getAudioGender()+"_"+audioFile.toLowerCase()+tone;
			dialog.setText(mText);
			dialog.setAudioFile(audioFile); // 1 = 1st tone
	        dialog.show(((Activity)mContext).getFragmentManager(), "SoundRecorderDialogFragment");
		}
	}

	public void showSlideGroupStatus(){
        if( mSlideGroupStatus == Configuration.SLIDE_STATUS_COMPLETE  ){
        	mSlideGroupStatusTextView.setVisibility(View.VISIBLE);
        	mSlideGroupStatusTextView.setText(mContext.getResources().getString(R.string.slide_status_complete));
        	mSlideGroupStatusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.slide_group_status_complete_backgroundcolor));
        	mSlideGroupStatusTextView.setTextColor(mContext.getResources().getColor(R.color.slide_group_status_complete_textcolor));
        }
        else if( mSlideGroupStatus == Configuration.SLIDE_STATUS_STUDYING ){
        	mSlideGroupStatusTextView.setVisibility(View.VISIBLE);
        	mSlideGroupStatusTextView.setText(mContext.getResources().getString(R.string.slide_status_studying));
        	mSlideGroupStatusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.slide_group_status_studying_backgroundcolor));
        	mSlideGroupStatusTextView.setTextColor(mContext.getResources().getColor(R.color.slide_group_status_studying_textcolor));
        }
        else {
        	mSlideGroupStatusTextView.setVisibility(View.VISIBLE);
        	mSlideGroupStatusTextView.setText(mContext.getResources().getString(R.string.slide_status_new));
        	mSlideGroupStatusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.slide_group_status_new_backgroundcolor));
        	mSlideGroupStatusTextView.setTextColor(mContext.getResources().getColor(R.color.slide_group_status_new_textcolor));
        }
	}
}
