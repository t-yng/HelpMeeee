package com.helpmeeee;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import android.os.AsyncTask;

public class AsyncGetRequestTokenTask extends AsyncTask<OAuthAuthorization, Void, RequestToken> {
	
	private final String CONSUMER_KEY = "LhOAFIpwG4fgvZQNnMdGMcO6V";
	private final String CONSUMER_SECRET = "bqDSco06bZgFhbEO50sIdSYDPijhDpCx8w7PwrKqWSuES5A6Qn";
	
	public interface AsyncGetRequestTokenTaskCallback  {
		void onPostExecute(RequestToken requestToken);
	}

	private AsyncGetRequestTokenTaskCallback mCallback;
//	private String mCallbackURL;
	
	public AsyncGetRequestTokenTask(AsyncGetRequestTokenTaskCallback callback, String callbackURL){
//		mCallbackURL = callbackURL;
		mCallback = callback;
	}

	@Override
	protected RequestToken doInBackground(OAuthAuthorization... params) {

		/* Twitter4jの設定の読み込み */
		Configuration conf = ConfigurationContext.getInstance();
		
		/* Oauth認証オブジェクトの生成 */
		OAuthAuthorization oauth = new OAuthAuthorization(conf);
		
		/* Oauth認証オブジェクトにconsumerKeyとconsumerSecretを設定 */
		oauth.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		
//		Twitter twitter = TwitterFactory.getSingleton();
//		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		
//		OAuthAuthorization oauth = params[0];
		RequestToken requestToken = null;
		try {
			requestToken = TwitterUtils.getTwitterInstance().getOAuthRequestToken("yng://twitter");
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return requestToken;
	}
	
	@Override
	protected void onPostExecute(RequestToken requestToken){
		super.onPostExecute(requestToken);
		mCallback.onPostExecute(requestToken);
	}

}
