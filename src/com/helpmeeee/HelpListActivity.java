package com.helpmeeee;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.helpmeeee.AsyncGetAccessTokenTask.AsyncTaskCallback;

public class HelpListActivity extends ListActivity{
	
	private Twitter mTwitter;
	private AsyncUpdateStatusTask mAsyncUpdateStatusTask; 
	private TweetAdapter mAdapter;
	
//	private ArrayAdapter<String> stringAdapter;
	private Handler mHandler;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
//		this.setContentView(R.layout.activity_help_list);
		
		if(TwitterUtils.getRequestToken() != null){
			onNewIntent(getIntent());
		}
		
		/* AccessTokenを非同期に取得するので、アクセストークンが無い時にはエラーになる  */
		mTwitter = TwitterUtils.getTwitterInstance();
		mTwitter.setOAuthAccessToken(TwitterUtils.loadAccessToken(this));
		
		mAdapter = new TweetAdapter(this, R.layout.help_list_item);
		this.setListAdapter(mAdapter);
//		this.setAdapter(mAdapter);
//		this.setListAdapter(mAdapter);
		
//		stringAdapter = new ArrayAdapter<String>(this, R.layout.list_item);
//		this.setListAdapter(stringAdapter);
		
//		updateStatus("テストツイートです");
		
		mAdapter.clear();
//		mAdapter.add(new User("test"));
		getListView().setSelection(0);
				
		startStream();
	}
	
//	private void addItem(final User user){
//		Handler handler = new Handler();
//		handler.post(new Runnable(){
//			@Override
//			public void run() {
//				mAdapter.add(user);
//			}			
//		});
////		mAdapter.add(user);
////		mAdapter.notifyDataSetChanged();
////		this.setListAdapter(mAdapter);
////		ListView list = (ListView)findViewById(android.R.id.list);
////		list.invalidateViews();
//	}
	
	private void startStream(){
		TwitterStream stream = TwitterUtils.getTwitterStreamInstance(this);
		stream.addListener(new StatusAdapter(){
			@Override
			public void onStatus(final Status status){
				mHandler.post(new Runnable(){
					@Override
					public void run() {
						Log.d("tweet", status.getText());
						mAdapter.add(status);
						mAdapter.notifyDataSetChanged();
					}
				});
			}			
		});
		
		/* ハッシュタグでフィルタリング */
		String[] track= {"#yng_test_tag"};
		FilterQuery query = new FilterQuery();
		query.track(track);
		
		stream.filter(query);
	}
	
    public void updateStatus(String text){
    	if(mAsyncUpdateStatusTask == null){
    		mAsyncUpdateStatusTask = new AsyncUpdateStatusTask(mTwitter);
    	}
    	mAsyncUpdateStatusTask.execute(text);
    }


	@Override
	public void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		if(intent == null || intent.getData() == null){
			return;
		}

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

}
