package com.chat.inomma.services.impl;

import com.chat.inomma.AppConstants;
import com.chat.inomma.entity.ChatMessage;
import com.chat.inomma.entity.User;
import com.chat.inomma.services.StorageService;
import com.firebase.client.Firebase;

/**
 * Created by Vahe on 12/26/2017.
 */

public class StorageServiceImpl implements StorageService {

    private Firebase mDatabase;
    public static StorageService sInstance;
    static {
        if (sInstance == null){
            sInstance = new StorageServiceImpl();
        }
    }

    public static StorageService getInstance(){
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private static class SingletonHolder {
        static final StorageServiceImpl HOLDER_INSTANCE = new StorageServiceImpl();
    }

    public StorageServiceImpl() {
        mDatabase = new Firebase(AppConstants.DATABASE_LINK + AppConstants.USERS_TABLE);
    }

    @Override
    public void saveUser(User user) {
        mDatabase.child(user.getUid()).setValue(user);
    }

    @Override
    public void saveMessage(String convUid, ChatMessage chatMessage) {
//        mDatabase.child(AppConstants.CONVERSATIONS_TABLE).child(convUid).setValue(chatMessage);
    }

    @Override
    public void getAllUsers() {

    }
}
