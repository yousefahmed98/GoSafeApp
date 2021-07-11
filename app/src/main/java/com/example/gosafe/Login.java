package com.example.gosafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    //Variables
    Button signUpBtn,loginBtn;
    ImageView image;
    TextView welcomeText , welcomeText2;
    TextInputLayout username , password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        image = findViewById(R.id.logoImage);
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText2 = findViewById(R.id.welcomeText2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUpBtn = findViewById(R.id.signUpButton);
        loginBtn = findViewById(R.id.signInButton);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logoImageTran");
                pairs[1] = new Pair<View,String>(welcomeText,"welcomeText");
                pairs[2] = new Pair<View,String>(welcomeText2,"welcomeText2");
                pairs[3] = new Pair<View,String>(username,"usernameTran");
                pairs[4] = new Pair<View,String>(password,"passwordTran");
                pairs[5] = new Pair<View,String>(loginBtn,"signTran");
                pairs[6] = new Pair<View,String>(signUpBtn,"signTran2");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this , pairs);
                    startActivity(intent,options.toBundle());
                }
                else {
                    startActivity(intent);
                }


            }
        });
    }
}