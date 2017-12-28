package com.chat.inomma.utils;

import android.app.Activity;

import com.chat.inomma.AppConstants;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Vahe on 12/26/2017.
 */

public class LoginUtils {
    public static void initializeTwitter(Activity activity){
        // Configure Twitter SDK
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                AppConstants.TWITTER_CONSUMER_KEY,
                AppConstants.TWITTER_CONSUMER_SECRET);

        TwitterConfig twitterConfig = new TwitterConfig.Builder(activity)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);
    }
}
