package com.example.hsx.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalCenterActivity extends Activity implements View.OnClickListener {
    private TextView personalCenter;
    private TextView PCusername;
    private TextView PCemail;
    private TextView PCmobile;
    private TextView PCgrade;
    private TextView PCgender;
    private Button PCresetpwd;
    private Button PCexit;
    private LinearLayout container;
    private TextView PCquestions;
    private TextView PCanswer;
    private TextView PCuser;
    private LinearLayout PCmenu;

    private UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        initView();

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");
        String grade = intent.getStringExtra("grade");

        String mobile = "xxx";
        if (email.equals(""))
            email = "xxx";

        userInfo = new UserInfo(username,email,gender,grade,mobile);

        PCusername.setText(username);
        PCemail.setText(email);
        PCgrade.setText(grade);
        PCgender.setText(gender);
        PCmobile.setText(mobile);
    }

    private void initView() {
        personalCenter = (TextView) findViewById(R.id.personalCenter);
        PCusername = (TextView) findViewById(R.id.PCusername);
        PCemail = (TextView) findViewById(R.id.PCemail);
        PCmobile = (TextView) findViewById(R.id.PCmobile);
        PCgrade = (TextView) findViewById(R.id.PCgrade);
        PCgender = (TextView) findViewById(R.id.PCgender);
        PCresetpwd = (Button) findViewById(R.id.PCresetpwd);
        PCexit = (Button) findViewById(R.id.PCexit);
        container = (LinearLayout) findViewById(R.id.container);

        PCresetpwd.setOnClickListener(this);
        PCexit.setOnClickListener(this);
        PCquestions = (TextView) findViewById(R.id.PCquestions);
        PCquestions.setOnClickListener(this);
        PCanswer = (TextView) findViewById(R.id.PCanswer);
        PCanswer.setOnClickListener(this);
        PCuser = (TextView) findViewById(R.id.PCuser);
        PCuser.setOnClickListener(this);
        PCmenu = (LinearLayout) findViewById(R.id.PCmenu);
        PCmenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PCresetpwd:
                resetpwd();
                break;
            case R.id.PCexit:
                exit();
                break;
            case R.id.PCquestions:
                questions();
                break;
            case R.id.PCanswer:
                answer();
                break;
        }
    }

    private void exit() {
        Intent intent = new Intent(PersonalCenterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void resetpwd() {
        Intent intent = new Intent(PersonalCenterActivity.this, ResetpwdActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }

    private void questions(){
        Intent intent = new Intent(PersonalCenterActivity.this, QuestionlistActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }

    private void answer(){
        Intent intent = new Intent(PersonalCenterActivity.this,mainActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        System.out.println("拉萨解放的拉萨解放狄拉克的时间flea可免费"+userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }
}
