package ru.job4j.pool.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        int valuePool = Runtime.getRuntime().availableProcessors();
        this.pool = Executors.newFixedThreadPool(valuePool);
    }

    public void emailTo(User user) {
        pool.submit(
                () -> send(
                        "Notification" + user.getUserName() + "to email " +  user.geteMail(),
                        "Add a new event to" + user.getUserName(),
                        "qq@qq.ru")
        );
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
    }
}
