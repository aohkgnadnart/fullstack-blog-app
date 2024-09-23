package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Message;
import com.springboot.blog.entity.User;
import com.springboot.blog.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    // Example method to check if user is online and notify them
    public void notifyUserAboutNewMessage(User recipient, Message message) {
        if (!isUserOnline(recipient)) {
            // Logic to store notification in the database or send an email
            saveNotification(recipient, message);
        }
    }

    public boolean isUserOnline(User recipient) {
        // Check if the user is online (this is just an example)
        return false;
    }

    public void saveNotification(User recipient, Message message) {
        // Logic to save the notification in the database
    }
}
