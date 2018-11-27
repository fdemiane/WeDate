package com.example.fredd.wedate.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredd.wedate.R;
import com.example.fredd.wedate.database.DatabaseQuerier;
import com.example.fredd.wedate.monitor.User;
import com.example.fredd.wedate.shared.DataFlow;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class MainScreenActivity extends AppCompatActivity {

    private ArrayList<User> allUsers = new ArrayList<>();
    private Set<User> likeCanBeSeen = new TreeSet<>();
    private Set<User> canComment = new TreeSet<>();
    private boolean isLast = false;
    private User currentRef = null;

    private ImageView imageView;
    private Button nextButton;
    private Button previousButton;
    private Button likeButton;
    private Button commentButton;
    private TextView nameText;

    private TextView matchText;
    private TextView spyText;
    private TextView seeText;

    private int index = 0;
    private boolean canWork = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        imageView = (ImageView) findViewById(R.id.imageView);
        nextButton = findViewById(R.id.nextID);
        previousButton = findViewById(R.id.superID);
        likeButton = findViewById(R.id.likeID);
        commentButton = findViewById(R.id.commentID);
        nameText = findViewById(R.id.nameID);
        matchText = findViewById(R.id.matchID);
        spyText = findViewById(R.id.spyID);
        seeText = findViewById(R.id.seeID);
        seeText.setText("see: "+DataFlow.attributes.isCanSuperLike());
        spyText.setText("spy: "+DataFlow.attributes.isCanSpy());
        matchText.setText("matchmake: "+DataFlow.attributes.isCanMath());

        try {
            DatabaseQuerier.getAllUsers(this);


        } catch (Exception e) {
            e.printStackTrace();
        }



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canWork) {
                    index = (index + 1) % allUsers.size();
                    currentRef = allUsers.get(index);
                    updateInfo();
                }

            }
        });


    }

    private void updateInfo() {
        nameText.setText(currentRef.getFirstName() + " " + currentRef.getLastName());
        commentButton.setEnabled(likeCanBeSeen.contains(currentRef));
        previousButton.setEnabled(likeCanBeSeen.contains(currentRef));

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(currentRef.getUrl());


    }

    public void updateUsers(Set<User> users) {
        this.allUsers.addAll(users);
        filterUsersByTag(likeCanBeSeen, DataFlow.attributes.getCanSeeWhoLiked());
        filterUsersByTag(canComment, DataFlow.attributes.getCanComment());
        canWork = true;
        currentRef = allUsers.get(index);
        updateInfo();
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
