package com.example.hsx.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ResetpwdRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://120.79.138.185/Resetpwd.php";
    private Map<String,String> params;

    // 旧密码， 新密码， 用户名
    public ResetpwdRequest(String oldpassword, String newpassword, String username, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("oldpassword", oldpassword);
        params.put("newpassword",newpassword);
        params.put("username",username);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
