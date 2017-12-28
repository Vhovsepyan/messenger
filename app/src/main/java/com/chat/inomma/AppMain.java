package com.chat.inomma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chat.inomma.adapter.UserAdapter;
import com.chat.inomma.entity.User;
import com.chat.inomma.utils.AppLog;
import com.chat.inomma.utils.FileUtils;
import com.chat.inomma.utils.AvatarImageLoader;
import com.chat.inomma.utils.LoginUtils;
import com.chat.inomma.utils.SharedPrefUtils;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppMain extends AppCompatActivity implements AvatarImageLoader.Listener {
    private String TAG = AppMain.class.getCanonicalName();

    Firebase reference1;
    protected String myUid;
    List<User> users = new ArrayList<>();
    ListView usersListView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        usersListView = findViewById(R.id.users_list);
        reference1 = new Firebase(AppConstants.DATABASE_LINK + AppConstants.USERS_TABLE);
        myUid = SharedPrefUtils.loadText(this, AppConstants.REGISTERED_USER_UID);

        userAdapter = new UserAdapter( AppMain.this);
        usersListView.setAdapter(userAdapter);
        AppLog.i(TAG, reference1.toString());
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if (!myUid.equals(user.getUid())) {
                    userAdapter.addItem(user);
//                    Picasso.with(AppMain)
                    new AvatarImageLoader(AppMain.this, user).executeOnExecutor(MyApplicaton.getMainExcecutor(), user.getPhotoUrl());
//                    new DownloadImageTask()
                    AppLog.i(TAG, "users: " + user.getDisplayName() + " " + user.getUid());
                    userAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        usersListView.setOnItemClickListener(usersItemClickListener);
    }

    AdapterView.OnItemClickListener usersItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            User user = (User) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(AppMain.this, ConversationActivity.class);
            intent.putExtra(AppConstants.USER_UID_KEY, user.getUid());
            intent.putExtra(AppConstants.USER_NAME_KEY, user.getDisplayName());
            startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginUtils.initializeTwitter(this);
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        SharedPrefUtils.saveText(this, AppConstants.REGISTERED_USER_UID, "");
        Intent intent = new Intent(this, TwitterLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onImageLoaded(Bitmap bitmap, User user) {
        AppLog.i(TAG, " img Loaded ");
        String path = getApplicationContext().getFilesDir() + "/" + AppConstants.AVATAR_DIR + "/" + user.getUid();
        File newFile1 = new File(path);
        if (!newFile1.exists()) {
            AppLog.i(TAG, " write image to file ");
            FileUtils.writeFileToStorage(bitmap, path);
            userAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {

    }


}
