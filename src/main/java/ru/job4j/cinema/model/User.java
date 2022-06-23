package ru.job4j.cinema.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class User  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int userId;
    private String userName;
    private String email;
    private String phone;

    public User(int userId) {
        this.userId = userId;
    }

    public User() {
    }

    public User(int userId, String userName, String email, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public User(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone);
    }

    @Override
    public String toString() {
        return "User{"
                + "userId=" + userId
                + ", userName='" + userName + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + '}';
    }
}
