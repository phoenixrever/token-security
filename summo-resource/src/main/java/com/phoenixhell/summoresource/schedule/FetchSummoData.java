package com.phoenixhell.summoresource.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author phoenixhell
 * @since 2021/12/24 0024-上午 10:59
 */

@EnableScheduling
@EnableAsync
@Component
public class FetchSummoData {

    /**
     * 秒 分 时 日 月 周
     * <p>
     * 日和周的位置随便谁是? 都行
     */
    //希望异步执行的方法 定时任务不阻塞
    @Async
    @Scheduled(cron = "* * */1 * * ?")
    public void fetchData() {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python C:\\Users\\Administrator\\Desktop\\test.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
