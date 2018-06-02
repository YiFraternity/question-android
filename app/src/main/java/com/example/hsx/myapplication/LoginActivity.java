package com.example.hsx.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username;
    private TextView contry_sn;
    private Button bt_username_clear;
    private FrameLayout username_layout;
    private EditText password;
    private Button bt_pwd_clear;
    private FrameLayout usercode_layout;
    private Button login;
    private Button forgive_pwd;
    private Button register;
    private RelativeLayout login_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        contry_sn = (TextView) findViewById(R.id.contry_sn);
        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        username_layout = (FrameLayout) findViewById(R.id.username_layout);
        password = (EditText) findViewById(R.id.password);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        usercode_layout = (FrameLayout) findViewById(R.id.usercode_layout);
        login = (Button) findViewById(R.id.login);
        forgive_pwd = (Button) findViewById(R.id.forgive_pwd);
        register = (Button) findViewById(R.id.register);
        login_layout = (RelativeLayout) findViewById(R.id.login_layout);

        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        login.setOnClickListener(this);
        forgive_pwd.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_username_clear:

                break;
            case R.id.bt_pwd_clear:

                break;
            case R.id.login:
                login();
                break;
            case R.id.forgive_pwd:
                forgetpwd();
                break;
            case R.id.register:
                register();
                break;
        }
    }

    private void submit() {
        // validate
        String usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    private void login(){
        final String name = username.getText().toString();
        final String passwd = password.getText().toString();

        if(name.length()>0) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            String gender = jsonObject.getString("gender");
                            String grade = jsonObject.getString("grade");     // 年级
                            String email = jsonObject.getString("email");
                            // Test
                            Intent intent = new Intent(LoginActivity.this, PersonalCenterActivity.class);
                            intent.putExtra("username", name);
                            intent.putExtra("gender", gender);
                            intent.putExtra("email", email);
                            intent.putExtra("grade", grade);
                            startActivity(intent);

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("登陆失败")
                                    .setNegativeButton("重试", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(name, passwd, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
        else {
            submit();
        }
    }

    private void register(){
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void forgetpwd(){
        Intent intent = new Intent(LoginActivity.this,ForgetPwdActivity.class);
        startActivity(intent);
    }
}
