package ru.job4j.pool.email;

import java.util.Objects;

public class User {
    private final String userName;
    private final String eMail;

    public User(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public String geteMail() {
        return eMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userName.equals(user.userName) && eMail.equals(user.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, eMail);
    }
}
