package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    private Button btnSignUp, btnLogin;
    private EditText edtNameSignUp, edtPasswordSignUp, edtNameLogin, edtPasswordLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtNameSignUp = findViewById(R.id.xedtNameSignUp);
        edtPasswordSignUp = findViewById(R.id.xedtPasswordSignUp);
        edtNameLogin = findViewById(R.id.xedtNameLogin);
        edtPasswordLogin = findViewById(R.id.xedtPasswordLogin);

        btnLogin = findViewById(R.id.xbtnLogin);
        btnSignUp = findViewById(R.id.xbtnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(SignUpLoginActivity.this,  appUser.getUsername() + " is Signed up successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        }else {
                            FancyToast.makeText(SignUpLoginActivity.this," not Saved" + e.getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });

            }

        }); //end of btnSignUp.setOnClickListener

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtNameLogin.getText().toString(),
                        edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (e == null) {
                            FancyToast.makeText(SignUpLoginActivity.this,  user.getUsername() + " is LoggedIn successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        }else {
                            FancyToast.makeText(SignUpLoginActivity.this,"" + e.getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }

                    }
                });

            }
        }); //end of btnLogin.setOnClickListener


    } // end of onCreate
}
