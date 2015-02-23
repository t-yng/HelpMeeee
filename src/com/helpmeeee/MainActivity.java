package com.helpmeeee;

import twitter4j.AsyncTwitter;
import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.helpmeeee.AsyncGetRequestTokenTask.AsyncGetRequestTokenTaskCallback;

public class MainActivity extends Activity {

	private RequestToken mRequestToken;
	private OAuthAuthorization mOauth;
	private String mCallbackURL;
	private AsyncTwitter mTwitter;
	private Context mContext;
	
	private boolean flag = false;
	
    private static final String CONSUMER_KEY = "LhOAFIpwG4fgvZQNnMdGMcO6V";
    private static final String CONSUMER_SECRET = "bqDSco06bZgFhbEO50sIdSYDPijhDpCx8w7PwrKqWSuES5A6Qn";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		startIntent();
		
//	TwitterUtils.removeAccessToken(this);
	}
	
	private void startIntent(){
		/* アクセストークンが保存されていなければOAuth認証を実行 */
		if(!TwitterUtils.hasAccessToken(this)){
			try {
				startAuthorize();
				Log.d("log", "start");
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		Intent intent = new Intent(this, HelpListActivity.class);
		startActivity(intent);
		finish();		
	}
	
	private void startAuthorize() throws TwitterException{
//		AsyncTwitterFactory factory = new AsyncTwitterFactory();
//		mTwitter = factory.getInstance();
//		mTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		
//		RequestToken requestToken = mTwitter.getOAuthRequestToken("yng://titter");
//		mRequestToken = mTwitter.getOAuthRequestToken("yng://titter");
		
		/* Twitter4jの設定の読み込み */
		Configuration conf = ConfigurationContext.getInstance();
		
		/* Oauth認証オブジェクトの生成 */
		mOauth = new OAuthAuthorization(conf);
		
		/* Oauth認証オブジェクトにconsumerKeyとconsumerSecretを設定 */
		mOauth.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		
		/* アプリケーションの認証オブジェクトを生成 */
		AsyncGetRequestTokenTaskCallback callback = new AsyncGetRequestTokenTaskCallback(){
			@Override
			public void onPostExecute(RequestToken requestToken){
				TwitterUtils.setRequestToken(requestToken);
//				mRequestToken = requestToken;
				String uri = requestToken.getAuthorizationURL();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
//				finish();
			}
		};
		
		AsyncGetRequestTokenTask task = new AsyncGetRequestTokenTask(callback, mCallbackURL);
		task.execute(mOauth);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
