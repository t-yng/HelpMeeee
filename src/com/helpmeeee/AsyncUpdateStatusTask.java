package com.helpmeeee;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.os.AsyncTask;

public class AsyncUpdateStatusTask extends AsyncTask<String, Void, Void> {

	private Twitter mTwitter;
	
	public AsyncUpdateStatusTask(Twitter twitter){
		mTwitter = twitter;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		String text = params[0];
		
//		Twitter twitter = TwitterUtils.getTwitterInstance();
		
		try {
			mTwitter.updateStatus(text);
		} catch (TwitterException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
		
//		 TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

}
