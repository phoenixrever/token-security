package com.phoenixhell.summoresource.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.phoenixhell.common.utils.R;
import com.phoenixhell.summoresource.entity.NotificationEntity;
import com.phoenixhell.summoresource.service.FirebaseMessagingService;
import org.apache.ibatis.ognl.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("summoresource/house")
public class NotificationController {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @GetMapping("/sendMessage")
    public R sendMessage(){
        NotificationEntity notificationEntity = new NotificationEntity();
        //写了此部分 会触发默认消息 再加上我们设置的本地消息会出现2次
        // 我们不用notification 的title body  用 map 里面加数据替代
        //notificationEntity.setTitle("spring boot");
        //notificationEntity.setBody("this is a body");
        HashMap<String, String> map = new HashMap<>();
        map.put("route","tab1");
        map.put("title","title");
        map.put("body","body");
        notificationEntity.setData(map);
        String send="";
        String token = "fPmGFPU8TLKiUTPxAUniJ3:APA91bG3jwTAORBvnloz6w452h0_zRz2s2zQCJKLVm7ZipaMbjf13SN7JAO_PZArxHcZLTJ-Dy--h674ras68i7CjZkSf8q8kbXZFcQC_c9NYhyccwEWLZ5K_Zla9NKQlv57husWUdRa";
        try {
             send = firebaseMessagingService.sendNotification(notificationEntity, token);
            System.out.println(send);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return R.ok(send);
    }
}
