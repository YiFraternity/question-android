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

public class ForgetPwdActivity extends Activity implements View.OnClickListener {

    private EditText forgivepwd_edit_name;
    private Button forgivepwd_btn_cancel;
    private EditText forgivepwd_edit_pwd_new;
    private Button forgivepwd_btn_sure;
    private EditText forgivepwd_edit_pwd_check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgivepwd);
        initView();
    }


    private void initView() {
        forgivepwd_edit_name = (EditText) findViewById(R.id.forgivepwd_edit_name);
        forgivepwd_btn_cancel = (Button) findViewById(R.id.forgivepwd_btn_cancel);
        forgivepwd_edit_pwd_new = (EditText) findViewById(R.id.forgivepwd_edit_pwd_new);
        forgivepwd_btn_sure = (Button) findViewById(R.id.forgivepwd_btn_sure);
        forgivepwd_edit_pwd_check = (EditText) findViewById(R.id.forgivepwd_edit_pwd_check);

        forgivepwd_btn_cancel.setOnClickListener(this);
        forgivepwd_btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgivepwd_btn_cancel:
                cancel();
                break;
            case R.id.forgivepwd_btn_sure:
                forgetpwd();
                break;
        }
    }

    private void submit() {
        // validate
        String name = forgivepwd_edit_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String newpassword = forgivepwd_edit_pwd_new.getText().toString().trim();
        if (TextUtils.isEmpty(newpassword)) {
            Toast.makeText(this, "请确认您的新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String check = forgivepwd_edit_pwd_check.getText().toString().trim();
        if (TextUtils.isEmpty(check)) {
            Toast.makeText(this, "请输入您的新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

    }

    private void forgetpwd(){
        submit();
        if(!(forgivepwd_edit_pwd_new.getText().toString().trim())
                .equals(forgivepwd_edit_pwd_check.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            final String username = forgivepwd_edit_name.getText().toString();
            final String password = forgivepwd_edit_pwd_check.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            Intent intent = new Intent(ForgetPwdActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPwdActivity.this);
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
            ForgetpwdRequest forgetpwdRequest = new ForgetpwdRequest(password,username,responseListener);
            RequestQueue queue = Volley.newRequestQueue(ForgetPwdActivity.this);
            queue.add(forgetpwdRequest);
        }
    }

    private void cancel(){
        Intent intent = new Intent(ForgetPwdActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
