package com.phoenixhell.summoresource.schedule;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.phoenixhell.summoresource.entity.NotificationEntity;
import com.phoenixhell.summoresource.service.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author phoenixhell
 * @since 2021/12/24 0024-上午 10:59
 */

@EnableScheduling
@EnableAsync
@Component
public class FetchSummoData {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    /**
     * 秒 分 时 日 月 周
     * <p>
     * 日和周的位置随便谁是? 都行
     */
    //希望异步执行的方法 定时任务不阻塞
    @Async
    @Scheduled(cron = "0 0 */1 * * ?")
    public void fetchData() {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python3 /root/summo.py");
            //BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            //String line = null;
            //while ((line = in.readLine()) != null) {
            //    System.out.println(line);
            //}
            //in.close();
            System.out.println("python ok");
            proc.waitFor();

            NotificationEntity notificationEntity = new NotificationEntity();
            //写了此部分 会触发默认消息 再加上我们设置的本地消息会出现2次
            // 我们不用notification 的title body  用 map 里面加数据替代
            //notificationEntity.setTitle("spring boot");
            //notificationEntity.setBody("this is a body");
            HashMap<String, String> map = new HashMap<>();
            map.put("route", "home");
            map.put("title", "新的房间信息更新了");
            map.put("body", "更新了新的信息 快来点击我查看把");
            notificationEntity.setData(map);
            String send = "";
            //String token = "fPmGFPU8TLKiUTPxAUniJ3:APA91bG3jwTAORBvnloz6w452h0_zRz2s2zQCJKLVm7ZipaMbjf13SN7JAO_PZArxHcZLTJ-Dy--h674ras68i7CjZkSf8q8kbXZFcQC_c9NYhyccwEWLZ5K_Zla9NKQlv57husWUdRa";
            send = firebaseMessagingService.sendNotification(notificationEntity, "fakesummo");
            System.out.println(send);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
