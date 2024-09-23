package com.springboot.blog.service;

import com.springboot.blog.entity.Message;
import com.springboot.blog.entity.User;

public interface NotificationService {
    void notifyUserAboutNewMessage(User recipient, Message message);

    boolean isUserOnline(User recipient);

    void saveNotification(User recipient, Message message);
}
