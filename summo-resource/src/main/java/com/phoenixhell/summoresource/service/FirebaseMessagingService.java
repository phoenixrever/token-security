package com.phoenixhell.summoresource.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.phoenixhell.summoresource.entity.NotificationEntity;

public interface FirebaseMessagingService {
     String sendNotification(NotificationEntity notificationEntity,String topic) throws FirebaseMessagingException;
}
