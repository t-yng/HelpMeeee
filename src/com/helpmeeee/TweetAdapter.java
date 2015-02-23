package com.helpmeeee;

import twitter4j.Status;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Status> {

	private LayoutInflater mInflater;
	
	public TweetAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.help_list_item, null);
		}
		
		Status item = getItem(position);
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(item.getUser().getName());
		
		TextView screenName = (TextView)convertView.findViewById(R.id.screen_name);
		screenName.setText(item.getUser().getScreenName());
		
		TextView text = (TextView) convertView.findViewById(R.id.text);
		text.setText(item.getText());
		
//		String item = getItem(position);
//		TextView textView = (TextView) convertView.findViewById(R.id.list_item_text);
//		textView.setText(item);
		
		return convertView;
	}
}

