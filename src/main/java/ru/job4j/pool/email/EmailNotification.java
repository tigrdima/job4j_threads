package ru.job4j.pool.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(
                () -> send(
                        "Notification" + user.getUserName() + "to email " +  user.geteMail(),
                        "Add a new event to" + user.getUserName(),
                        "qq@qq.ru")
        );
    }

    public void close() {
       while (!pool.isTerminated()) {
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    public void send(String subject, String body, String email) {
    }
}
