package com.helpmeeee;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TwitterUtils {

    private static final String TOKEN = "token";
    private static final String TOKEN_SECRET = "token_secret";
    private static final String PREF_NAME = "twitter_access_token";
    
//    private static String TWITTER_CONSUMER_KEY;
//    private static String TWITTER_CONSUMER_SECRET;
    
    private static String TWITTER_CONSUMER_KEY = "LhOAFIpwG4fgvZQNnMdGMcO6V";
    private static String TWITTER_CONSUMER_SECRET = "bqDSco06bZgFhbEO50sIdSYDPijhDpCx8w7PwrKqWSuES5A6Qn";

	private static AsyncUpdateStatusTask mAsyncUpdateStatusTask; 

    private static Twitter mTwitter;
    private static TwitterStream mTwitterStream;
    private static RequestToken mRequetToken;
    

    /**
     * Twitter�C���X�^���X���擾���܂��B�A�N�Z�X�g�[�N�����ۑ�����Ă���Ύ����I�ɃZ�b�g���܂��B
     * 
     * @param context
     * @return
     */
    public static Twitter getTwitterInstance() {
//        String consumerKey = TWITTER_CONSUMER_KEY;
//        String consumerSecret = TWITTER_CONSUMER_SECRET;
    	
    	if(mTwitter != null){
    		return mTwitter;
    	}

        TwitterFactory factory = new TwitterFactory();
        mTwitter = factory.getInstance();
        mTwitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);

//        if (hasAccessToken(context)) {
//            mTwitter.setOAuthAccessToken(loadAccessToken(context));
//        }
        return mTwitter;
    }

    /**
     * TwitterStream�̃C���X�^���X�𐶐�
     * @return
     */
    public static TwitterStream getTwitterStreamInstance(Context context){
    	if(mTwitterStream == null){
    		ConfigurationBuilder builder = new ConfigurationBuilder();
    		builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
    		builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
    		
    		AccessToken token = loadAccessToken(context);
    		
    		builder.setOAuthAccessToken(token.getToken());
    		builder.setOAuthAccessTokenSecret(token.getTokenSecret());
    		
    		Configuration conf = builder.build();
    		
        	mTwitterStream = new TwitterStreamFactory(conf).getInstance();    		
    	}
    	
    	return mTwitterStream;
    }
        
    /**
     * �A�N�Z�X�g�[�N�����v���t�@�����X�ɕۑ����܂��B
     * 
     * @param context
     * @param accessToken
     */
    public static void storeAccessToken(Context context, AccessToken accessToken) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(TOKEN, accessToken.getToken());
        editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
        editor.commit();
    }

    /**
     * �[�����ɕۑ������A�N�Z�X�g�[�N�����폜
     * @param context
     */
    public static void removeAccessToken(Context context){

    	/* �[�����̃A�N�Z�X�g�[�N�����擾 */
    	AccessToken token = loadAccessToken(context);
    	
    	/* �A�N�Z�X�g�[�N�����ۑ�����Ă���Ȃ�폜 */
    	if(token != null){
        	SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);    	
        	preferences.edit().clear().commit();
    	}
    }

    /**
     * �A�N�Z�X�g�[�N�����v���t�@�����X����ǂݍ��݂܂��B
     * 
     * @param context
     * @return
     */
    public static AccessToken loadAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, null);
        String tokenSecret = preferences.getString(TOKEN_SECRET, null);
        if (token != null && tokenSecret != null) {
            return new AccessToken(token, tokenSecret);
        } else {
            return null;
        }
    }
       
//    public static void updateStatus(String text){
//    	if(mAsyncUpdateStatusTask == null){
//    		mAsyncUpdateStatusTask = new AsyncUpdateStatusTask();
//    	}
//    	mAsyncUpdateStatusTask.execute(text);
//    }
    
    /**
     * �A�N�Z�X�g�[�N�������݂���ꍇ��true��Ԃ��܂��B
     * 
     * @return
     */
    public static boolean hasAccessToken(Context context) {
        return loadAccessToken(context) != null;
    }

    public static void setRequestToken(RequestToken requestToken){
    	mRequetToken = requestToken;
    }
    
    public static RequestToken getRequestToken(){
    	return mRequetToken;
    }
    
}
