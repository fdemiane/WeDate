package com.example.fredd.wedate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fredd.wedate.R;
import com.example.fredd.wedate.database.DatabaseQuerier;
import com.example.fredd.wedate.encryption.AffineKey;
import com.example.fredd.wedate.encryption.DoubleEncryption;
import com.example.fredd.wedate.encryption.HillKey;
import com.example.fredd.wedate.encryption.MD5;
import com.example.fredd.wedate.encryption.PasswordChecker;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView usernameTextField;
    private TextView oldPasswordTextField;
    private TextView newPasswordTextField;
    private TextView wrongTextField;
    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        usernameTextField = (TextView) findViewById(R.id.cUsernameID);
        oldPasswordTextField = (TextView) findViewById(R.id.cOldPasswordID);
        newPasswordTextField = (TextView) findViewById(R.id.cNewPasswordID);
        wrongTextField = (TextView) findViewById(R.id.wrongID);
        validateButton = (Button) findViewById(R.id.cValidateButtonID);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateStep1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void validateStep1() throws Exception {

        String userName = usernameTextField.getText().toString();
        DatabaseQuerier.checkUserExistsChangePasswordActivity(userName, this);


    }

    public void validateStep2() throws Exception {
        String oldPassword = oldPasswordTextField.getText().toString()+usernameTextField.getText();
        DatabaseQuerier.checkPasswordCorrectChangePasswordActivity(oldPassword, this);


    }

    public void validateStep3() throws Exception {
        String newPassword = newPasswordTextField.getText().toString();

        if (PasswordChecker.checkPasswordValidity(newPassword)) {
            newPassword+= usernameTextField.getText();
            DatabaseQuerier.updatePasswordChangePasswordActivity(newPassword, this);

        } else {
            setWrongTextField("Password needs to be of a minimum length of 8 character, needs to contain at least one special character and one capital letter");

        }


    }

    public void onSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setWrongTextField(String text) {
        Log.d("TAG", text);
        wrongTextField.setText(text);
    }


}
