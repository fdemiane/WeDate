package com.example.fredd.wedate.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fredd.wedate.R;
import com.example.fredd.wedate.database.DatabaseQuerier;
import com.example.fredd.wedate.monitor.User;
import com.example.fredd.wedate.shared.DataFlow;

import java.util.Set;
import java.util.TreeSet;

public class MainScreenActivity extends AppCompatActivity {

    private Set<User> allUsers = new TreeSet<User>();
    private Set<User> likeCanBeSeen = new TreeSet<>();
    private Set<User> canComment = new TreeSet<>();
    private boolean isLast = false;
    private Set<User> currentRef = allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        try {
            DatabaseQuerier.getAllUsers( this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUsers(Set<User> users) {
        this.allUsers.addAll(users);
        filterUsersByTag(likeCanBeSeen , DataFlow.attributes.getCanSeeWhoLiked());
        filterUsersByTag(canComment , DataFlow.attributes.getCanComment());
    }

    private void filterUsersByTag(Set<User> target, Set<String> tags) {
        for (User user : allUsers) {
            if (tags.contains(user.getTag()))
                target.add(user);
        }
    }

    public void updateUsersIndexed(Set<User> users, int index) {
        switch (index) {
            case 0:
                likeCanBeSeen.addAll(users);
                break;
            case 1:
                canComment.addAll(users);
                break;
        }
    }


}
