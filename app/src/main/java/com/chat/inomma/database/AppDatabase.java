package com.chat.inomma.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.chat.inomma.database.dao.UserDao;
import com.chat.inomma.entity.User;

/**
 * Created by Vahe on 12/28/2017.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}