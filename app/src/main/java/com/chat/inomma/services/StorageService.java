package com.chat.inomma.services;

import com.chat.inomma.entity.ChatMessage;
import com.chat.inomma.entity.User;

/**
 * Created by Vahe on 12/26/2017.
 */

public interface StorageService {
    void saveUser(User user);

    void saveMessage(String convUid, ChatMessage chatMessage);

    void getAllUsers();
}
