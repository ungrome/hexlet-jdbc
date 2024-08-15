package io.hexlet;

public class User {
    private String username;
    private String phone;
    private Long id;

    public User(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }
}
