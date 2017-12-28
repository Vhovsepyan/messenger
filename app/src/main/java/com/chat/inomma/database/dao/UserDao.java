package com.chat.inomma.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.chat.inomma.entity.User;

import java.util.List;

/**
 * Created by Vahe on 12/28/2017.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE uid LIKE :uId")
    User findByUid(String uId);

    @Insert
    void insertAll(User... users);

    @Insert
    void inser(User user);

    @Delete
    void delete(User user);
}
