package org.aston.learning.stage1.sort;

import org.aston.learning.stage1.model.User;

import java.util.Comparator;

public class UserNameComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        return user1.getName().compareToIgnoreCase(user2.getName());
    }
}
