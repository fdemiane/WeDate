package com.example.fredd.wedate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fredd.wedate.database.Authentication;
import com.example.fredd.wedate.database.DatabaseQuerier;
import com.example.fredd.wedate.database.UsersAddition;
import com.example.fredd.wedate.encryption.MD5;
import com.example.fredd.wedate.monitor.ReferenceMonitor;
import com.example.fredd.wedate.monitor.User;

public class MainActivity extends AppCompatActivity {



    private ReferenceMonitor referenceMonitor = new ReferenceMonitor();
    private Button validateButton;
    private TextView usernameField;
    private TextView passwordField;
    private TextView noteField;

    public MainActivity() throws Exception {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validateButton = (Button) findViewById(R.id.validateBtnID);
        usernameField = (TextView) findViewById(R.id.usernameID);
        passwordField = (TextView) findViewById(R.id.passwordID);
        noteField = (TextView) findViewById(R.id.noteId);
        final MainActivity ref = this;
       
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameField.getText().toString();

                try {
                    DatabaseQuerier.getUserByUsername(username , ref);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



    }

    public void authenticate(User user) {

        MD5 md5 = new MD5();
        String password = passwordField.getText().toString();
        password = md5.encrypt(password);
        if (user.getUsername() == null) {
            noteField.setText("Incorrect username");
            return;
        }
        String encrypted = user.getPassword();

        if (password.equals(encrypted)) {
            noteField.setText("All is good!");
            referenceMonitor.setAttributes(user);
            return;

        }

        noteField.setText("Incorrect username or password");
    }


}
