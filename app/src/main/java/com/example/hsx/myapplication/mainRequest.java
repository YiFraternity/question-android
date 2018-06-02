package com.example.hsx.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class mainRequest extends StringRequest {
    private static final String SHOWANSWER_REQUEST_URL = "http://120.79.138.185/ShowAnswer.php";
    private Map<String,String> params;

    public mainRequest(String question, Response.Listener<String> listener){
        super(Method.POST,SHOWANSWER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("question",question);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
