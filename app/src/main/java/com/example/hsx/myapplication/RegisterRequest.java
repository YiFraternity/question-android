package com.example.hsx.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://120.79.138.185/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String username, String password, String email, String grade, String gender, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("email",email);
        params.put("grade",grade);
        params.put("gender",gender);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
