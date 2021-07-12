package com.example.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    //Variables
    Button signUpBtn,loginBtn;
    ImageView image;
    TextView welcomeText , welcomeText2;
    TextInputLayout logEmail, logPassword;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        image = findViewById(R.id.logoImage);
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText2 = findViewById(R.id.welcomeText2);
        logEmail = findViewById(R.id.email);
        logPassword = findViewById(R.id.password);
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
                pairs[3] = new Pair<View,String>(logEmail,"emailTran");
                pairs[4] = new Pair<View,String>(logPassword,"passwordTran");
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

        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = logEmail.getEditText().getText().toString();
                String password = logPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(username)){
                    logEmail.setError("username is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    logPassword.setError("password is Required.");
                    return;
                }
                fAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Sign In Successfully",Toast.LENGTH_SHORT).show();
                            String userID =fAuth.getCurrentUser().getUid();
                            DocumentReference reference = fStore.collection("users").document(userID);
                            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                                    String userType = task.getResult().getString("type");
                                    if(userType.equals("admin")){
                                        Intent intent = new Intent(Login.this,adminDashboard.class);
                                        startActivity(intent);

                                    }
                                }
                            });


                        }
                        else{
                            Toast.makeText(Login.this,"Sign In fail"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}