//package com.phoenixhell.summoresource.service.impl;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import com.phoenixhell.summoresource.entity.NotificationEntity;
//import com.phoenixhell.summoresource.service.FirebaseMessagingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {
//
//    //private final FirebaseMessaging firebaseMessaging;
//    //
//    //public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
//    //    this.firebaseMessaging = firebaseMessaging;
//    //}
//    @Autowired
//    private  FirebaseMessaging firebaseMessaging;
//
//
//    /**
//     *
//     * token 就是  SHA 证书指纹 向单个设备发送消息
//     * @param topic
//     *
//     */
//    @Override
//    public String sendNotification(NotificationEntity notificationEntity,String topic) throws FirebaseMessagingException {
//
//        Notification notification = Notification
//                .builder()
//                .setTitle(notificationEntity.getTitle())
//                .setBody(notificationEntity.getBody())
//                .setImage(notificationEntity.getImage())
//                .build();
//
//        Message message = Message
//                .builder()
//                .setNotification(notification)
//                //.setTopic(topic)
//                .setToken(topic)
//                .putAllData(notificationEntity.getData())
//                .build();
//
//        String send = firebaseMessaging.send(message);
//        return  send;
//    }
//}