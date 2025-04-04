package com.luannt.demomvvm.respository;

import com.luannt.demomvvm.model.User;

public class UserRepository {
    public User getUser() {
        // In a real app, you'd fetch this from a database or network
        return new User("John Doe");
    }

    public void updateUser(User user) {
        // In a real app, you'd update this in a database or network
    }
}
