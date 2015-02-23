package com.helpmeeee;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class AsyncGetAccessTokenTask extends AsyncTask<Void, Void, AccessToken> {
	
	public interface AsyncTaskCallback {
		public void onPostExecute(AccessToken accessToken);
	}
	
	private Intent mIntent;
//	private RequestToken mRequestToken;
//	private OAuthAuthorization mOauth;
	
	public AsyncGetAccessTokenTask(Intent intent){
		mIntent = intent;
//		mRequestToken = requestToken;
//		mOauth = oauth;
	}
	
	private AsyncTaskCallback mCallback;
	
	public void setCallback(AsyncTaskCallback callback){
		mCallback = callback;
	}

	@Override
	protected AccessToken doInBackground(Void... arg0) {
		
		Uri uri = mIntent.getData();
		
		AccessToken accessToken = null;
		if(uri != null){
			String verifier = uri.getQueryParameter("oauth_verifier");		
			try {
				accessToken = TwitterUtils.getTwitterInstance().getOAuthAccessToken(TwitterUtils.getRequestToken(), verifier);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}

		return accessToken;
	}

	@Override
	protected void onPostExecute(AccessToken accessToken){
		if(mCallback != null){
			mCallback.onPostExecute(accessToken);			
		}
	}
}
