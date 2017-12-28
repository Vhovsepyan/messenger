package com.chat.inomma.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.chat.inomma.AppConstants;


import java.util.HashMap;

/**
 * Created by Vahe on 12/26/2017.
 */
@Entity
public class User {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "uid")
    private String uid;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "photo_url")
    private String photoUrl;

    public User() {
    }

    @Ignore
    public User(String uId, String displayName, String photoUrl) {
        this.uid = uId;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
    }

    @Ignore
    public User(HashMap<String,String> userData){
        this.uid = userData.get(AppConstants.USER_UID);
        this.displayName = userData.get(AppConstants.USERS_DISPLEY_NAME);
        this.photoUrl = userData.get(AppConstants.USERS_PHOTO_URL);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
