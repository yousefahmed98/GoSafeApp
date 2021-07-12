package com.example.gosafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminDashboard extends AppCompatActivity {
    Button issuesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        issuesBtn=findViewById(R.id.issuesBtn);

        issuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminDashboard.this,adminDashboard.class);
                startActivity(intent);
            }
        });
    }

}