package com.example.hsx.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class QuestionlistRequest extends StringRequest {
    private static final String QUESTION_REQUEST_URL = "http://120.79.138.185/Questionlist.php";
    private Map<String,String> params;

    public QuestionlistRequest(Response.Listener<String> listener){
        super(Method.POST,QUESTION_REQUEST_URL, listener, null);
    }

    static class response implements Response.Listener<String>{
        String[] result;
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");

                if(success){
                    int count = jsonObject.length();
                    int i=1;
                    result = new String[count];
                    result[0]="";
                    for(i=1;i<count;i++){
                        result[i] = jsonObject.getString("question"+i);
                        System.out.println(result[i]);
                    }
//                                callback.onSuccess(quest);
                    // Test
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String[] get_data(){
            return this.result;
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

