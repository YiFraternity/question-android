package com.example.hsx.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetpwdActivity extends Activity implements View.OnClickListener {
    private EditText resetpwd_edit_name;
    private EditText resetpwd_edit_pwd_old;
    private Button resetpwd_btn_cancel;
    private EditText resetpwd_edit_pwd_new;
    private Button resetpwd_btn_sure;
    private EditText resetpwd_edit_pwd_check;

    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpwd);
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
    }

    private void initView() {
        resetpwd_edit_name = (EditText) findViewById(R.id.resetpwd_edit_name);
        resetpwd_edit_pwd_old = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        resetpwd_btn_cancel = (Button) findViewById(R.id.resetpwd_btn_cancel);
        resetpwd_edit_pwd_new = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
        resetpwd_btn_sure = (Button) findViewById(R.id.resetpwd_btn_sure);
        resetpwd_edit_pwd_check = (EditText) findViewById(R.id.resetpwd_edit_pwd_check);

        resetpwd_btn_cancel.setOnClickListener(this);
        resetpwd_btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetpwd_btn_cancel:
                cencel();
                break;
            case R.id.resetpwd_btn_sure:
                resetpwd();
                break;
        }
    }

    private void submit() {
        // validate
        String name = resetpwd_edit_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String oldpassword = resetpwd_edit_pwd_old.getText().toString().trim();
        if (TextUtils.isEmpty(oldpassword)) {
            Toast.makeText(this, "请输入您的旧密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newpassword = resetpwd_edit_pwd_new.getText().toString().trim();
        if (TextUtils.isEmpty(newpassword)) {
            Toast.makeText(this, "请确认您的新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String check = resetpwd_edit_pwd_check.getText().toString().trim();
        if (TextUtils.isEmpty(check)) {
            Toast.makeText(this, "请输入您的新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    private void resetpwd(){
        submit();
        if(!(resetpwd_edit_pwd_new.getText().toString().trim())
                .equals(resetpwd_edit_pwd_check.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            final String username = resetpwd_edit_name.getText().toString();
            final String newpassword = resetpwd_edit_pwd_check.getText().toString();
            final String oldpassword = resetpwd_edit_pwd_old.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            String gender = jsonObject.getString("gender");
                            String grade = jsonObject.getString("grade");     // 年级
                            String email = jsonObject.getString("email");

                            Intent intent = new Intent(ResetpwdActivity.this,PersonalCenterActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("gender",gender);
                            intent.putExtra("email",email);
                            intent.putExtra("grade",grade);
                            startActivity(intent);
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetpwdActivity.this);
                            builder.setMessage("更改密码失败")
                                    .setNegativeButton("重试",null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            // 旧密码， 新密码， 用户名
            ResetpwdRequest resetpwdRequest = new ResetpwdRequest(oldpassword,newpassword,username,responseListener);
            RequestQueue queue = Volley.newRequestQueue(ResetpwdActivity.this);
            queue.add(resetpwdRequest);
        }
    }

    private void cencel(){
        Intent intent = new Intent(ResetpwdActivity.this, PersonalCenterActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }
}
