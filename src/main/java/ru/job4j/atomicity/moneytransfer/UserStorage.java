package ru.job4j.atomicity.moneytransfer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        int id = user.getId();
        User old = users.get(id);
        return users.replace(id, user) == old;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) == user;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User sender = users.get(fromId);
        User recipient = users.get(toId);
        int amountSender = users.get(fromId).getAmount();
        int amountRecipient = users.get(toId).getAmount();

        if (sender != null && recipient != null && amountSender > amount) {
            sender.setAmount(amountSender - amount);
            recipient.setAmount(amountRecipient + amount);
        }
    }
}
