package com.example.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;




public class SignUp extends AppCompatActivity {
    //Variables
    TextInputLayout regFullName, regUserName, regEmail, regPhoneNo, regPassword;
    Button signUpBtn , haveAccBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        //Hooks

        regFullName = findViewById(R.id.fullName);
        regUserName = findViewById(R.id.username);
        regEmail = findViewById(R.id.Email);
        regPhoneNo = findViewById(R.id.PhoneNumber);
        regPassword = findViewById(R.id.password);
        signUpBtn = findViewById(R.id.signUpButton);
        haveAccBtn = findViewById(R.id.haveAccountButton);
        //save data in firebase

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String fullName = regFullName.getEditText().getText().toString();
                String userName = regUserName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(fullName)){
                    regFullName.setError("FullName is Required.");
                    return;
                }
                if(TextUtils.isEmpty(userName)){
                    regUserName.setError("UserName is Required.");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    regEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPhoneNo.setError("phone Number is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPassword.setError("Password is Required.");
                    return;
                }

                User user = new User(fullName, userName, email, phoneNo, password);
                String id = UUID.randomUUID().toString();
                // Add a new document with a generated ID
                saveToFireStore(id,user);


            }
        });
    }
    private void saveToFireStore(String id, User user){
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("id",id);
        newUser.put("fullName",user.fullName);
        newUser.put("userName",user.userName);
        newUser.put("email",user.email);
        newUser.put("phoneNo",user.phoneNo);
        newUser.put("password",user.password);
        newUser.put("type","user");
        db.collection("users").document(id).set(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this,"Sign Up Successfully",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(SignUp.this,"Sign Up Failed",Toast.LENGTH_SHORT).show();

                }
        });

    }
}