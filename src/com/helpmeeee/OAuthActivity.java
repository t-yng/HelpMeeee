package com.helpmeeee;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.helpmeeee.AsyncGetAccessTokenTask.AsyncTaskCallback;
import com.helpmeeee.AsyncGetRequestTokenTask.AsyncGetRequestTokenTaskCallback;

public class OAuthActivity extends Activity {
	
	private final String CONSUMER_KEY = "LhOAFIpwG4fgvZQNnMdGMcO6V";
	private final String CONSUMER_SECRET = "bqDSco06bZgFhbEO50sIdSYDPijhDpCx8w7PwrKqWSuES5A6Qn";

	private RequestToken mRequestToken;
	private OAuthAuthorization mOauth;	
	private String mCallbackURL;
	
//	private Twitter mTwitter;
//	private RequestToken mRequestToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_oauth);
		
		mCallbackURL = "yng://twitter";
		try {
			startAuthorize();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
//			Intent intent = new Intent(this, OAuthActivity.class);
//			startActivity(intent);
//			finish();
		
//		Button oauthBtn = (Button) findViewById(R.id.button1);		
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
	public void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		if(intent == null || intent.getData() == null){
			return;
		}
		
//		Uri uri = intent.getData();
//		
//		AccessToken accessToken = null;
//		if(uri != null){
//			String verifier = uri.getQueryParameter("oauth_verifier");		
//			try {
//				accessToken = mTwitter.getOAuthAccessToken(mRequestToken, verifier);
//				TwitterUtils.storeAccessToken(this, accessToken);
//			} catch (TwitterException e) {
//				// TODO 自動生成された catch ブロック
//				e.printStackTrace();
//			}
//		}
		
		AsyncGetAccessTokenTask task = new AsyncGetAccessTokenTask(intent);
		task.setCallback(new AsyncTaskCallback(){
			@Override
			public void onPostExecute(AccessToken accessToken) {
				Log.d("tag", "token : "+accessToken.getToken());
				storeAccessToken(accessToken);
//				flag = true;
//				Toast.makeText(this, "token : "+accessToken.getToken(), Toast.LENGTH_SHORT).show();
			}
			
		});
		task.execute();
				
	}
	
	private void storeAccessToken(AccessToken accessToken){
		TwitterUtils.storeAccessToken(this, accessToken);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
