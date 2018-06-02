package com.example.hsx.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hsx on 2018/5/6.
 */

public class mainActivity extends Activity implements View.OnClickListener {

    private TextView main_question_number;
    private TextView main_question;
    private TextView main_answer;
    private EditText main_useranswer_editText;
    private Button main_submit_btn;
    private TextView main_questionslist;
    private TextView main_answer_question;
    private TextView main_user;

    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        Intent intent= getIntent();
        String question = intent.getStringExtra("question");
        String question_number = intent.getStringExtra("question_number");
        String answer = intent.getStringExtra("answer");
        String useranswer = intent.getStringExtra("useranswer");

        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");
        String grade = intent.getStringExtra("grade");

        String mobile = "xxx";
        if (email.equals(""))
            email = "xxx";

        userInfo = new UserInfo(username,email,gender,grade,mobile);

        main_question_number.setText(question_number);
        main_question.setText(question);
        main_answer.setText(answer);
        main_useranswer_editText.setText(useranswer);

    }

    private void initView() {
        main_question_number = (TextView) findViewById(R.id.main_question_number);
        main_question = (TextView) findViewById(R.id.main_question);
        main_answer = (TextView) findViewById(R.id.main_answer);
        main_useranswer_editText = (EditText) findViewById(R.id.main_useranswer_editText);
        main_submit_btn = (Button) findViewById(R.id.main_submit_btn);
        main_questionslist = (TextView) findViewById(R.id.main_questionslist);
        main_answer_question = (TextView) findViewById(R.id.main_answer_question);
        main_user = (TextView) findViewById(R.id.main_user);

        main_submit_btn.setOnClickListener(this);
        main_questionslist.setOnClickListener(this);
        main_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_submit_btn:
                get_answer();
                break;
            case R.id.main_questionslist:
                questions();
                break;
            case R.id.main_user:
                user();
                break;
        }
    }

    private void submit() {
        // validate
        String editText = main_useranswer_editText.getText().toString().trim();
        if (TextUtils.isEmpty(editText)) {
            Toast.makeText(this, "请输入您的答案", Toast.LENGTH_SHORT).show();
        }
        // TODO validate success, do something

    }

    private void questions(){
        Intent intent = new Intent(mainActivity.this, QuestionlistActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }

//    private void answer() {
//        Intent intent = new Intent(mainActivity.this, mainActivity.class);
//        startActivity(intent);
//    }

    private void user() {
        Intent intent = new Intent(mainActivity.this, PersonalCenterActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }

    private void get_answer() {
        final String useranswer = main_useranswer_editText.getText().toString();
        System.out.println("奥i大家佛埃斯基的佛挨饿金佛爱上京东方"+useranswer);
        if (useranswer.length()>0)
         {
            final String question = main_question.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            String answer = jsonObject.getString("answer");
                            // Test
                            Intent intent = new Intent(mainActivity.this, mainActivity.class);
                            intent.putExtra("question", question);
                            intent.putExtra("answer", "正确答案\n" + answer);
                            intent.putExtra("useranswer",useranswer);
                            intent.putExtra("username",userInfo.getUsername());
                            intent.putExtra("email",userInfo.getEmail());
                            intent.putExtra("grade",userInfo.getGrade());
                            intent.putExtra("gender",userInfo.getGender());
                            intent.putExtra("mobile",userInfo.getMobile());
                            startActivity(intent);

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity.this);
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
            mainRequest mRequest = new mainRequest(question, responseListener);
            RequestQueue queue = Volley.newRequestQueue(mainActivity.this);
            queue.add(mRequest);
        }
        else{
            submit();
        }
    }

}