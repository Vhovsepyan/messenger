package com.chat.inomma;

/**
 * Created by Vahe on 12/25/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.inomma.entity.User;
import com.chat.inomma.services.impl.StorageServiceImpl;
import com.chat.inomma.utils.AppLog;
import com.chat.inomma.utils.LoginUtils;
import com.chat.inomma.utils.SharedPrefUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class TwitterLoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "TwitterLogin";
    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private TwitterLoginButton mLoginButton;
    protected String myUid;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myUid = SharedPrefUtils.loadText(this,AppConstants.REGISTERED_USER_UID);

        isLoggedIn = !myUid.isEmpty();
        if (isLoggedIn){
            Intent intent = new Intent(this, AppMain.class);
            startActivity(intent);
            finish();
        }
        AppLog.i(TAG, myUid);

        LoginUtils.initializeTwitter(this);

        // Inflate layout (must be done after Twitter is configured)
        setContentView(R.layout.activity_twitter);

        // Views
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        findViewById(R.id.button_twitter_signout).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_twitter_login]
        mLoginButton = findViewById(R.id.button_twitter_login);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
                updateUI(null);
            }
        });
        // [END initialize_twitter_login]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_twitter]
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(TwitterLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_twitter]

    private void signOut() {
        mAuth.signOut();
        TwitterCore.getInstance().getSessionManager().clearActiveSession();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            SharedPrefUtils.saveText(this, AppConstants.REGISTERED_USER_UID, user.getUid());
            StorageServiceImpl.getInstance().saveUser(new User(user.getUid(),user.getDisplayName(),String.valueOf(user.getPhotoUrl())));
            mStatusTextView.setText(getString(R.string.twitter_status_fmt, user.getDisplayName()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.button_twitter_login).setVisibility(View.GONE);
            findViewById(R.id.button_twitter_signout).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.button_twitter_login).setVisibility(View.VISIBLE);
            findViewById(R.id.button_twitter_signout).setVisibility(View.GONE);
//            SharedPrefUtils.saveText(this, AppConstants.REGISTERED_USER_UID, "");
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_twitter_signout) {
            Intent intent = new Intent(this,AppMain.class);
            startActivity(intent);
            finish();
//            signOut();
        }

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}