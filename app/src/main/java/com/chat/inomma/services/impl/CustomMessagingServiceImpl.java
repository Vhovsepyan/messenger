package com.chat.inomma.services.impl;

import android.content.Context;
import android.content.Intent;

import com.chat.inomma.AppConstants;
import com.chat.inomma.services.CustomMessagingService;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by vahe on 12/28/17.
 */

public class CustomMessagingServiceImpl implements CustomMessagingService {
    Context context;
    Firebase senderStorage;
    Firebase getterStorage;

    public CustomMessagingServiceImpl(Context context, String senderStorageLink, String getterStorageLink) {
        this.context = context;
        senderStorage = new Firebase(senderStorageLink);
        getterStorage = new Firebase(getterStorageLink);
    }

    public void initListeners(){
        senderStorage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                sendMessageRecivedBroadCast(map);
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
    }

    public void sendMessage(Map map){
        senderStorage.push().setValue(map);
        getterStorage.push().setValue(map);
    }


    private void sendMessageRecivedBroadCast(Map map){
        String message = map.get("message").toString();
        String userName = map.get("user").toString();
        Intent intent = new Intent(AppConstants.MESSAGE_RECEIVED);
        intent.putExtra(AppConstants.MESSAGE_TEXT, message);
        intent.putExtra(AppConstants.MESSAGE_SENDER,userName);
        context.sendBroadcast(intent);

    }


}
