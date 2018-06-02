package com.example.hsx.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageView imageView02;
    private EditText regUsernameEditText;
    private EditText regFirstPwdEditText;
    private EditText regSecondPwdEditText;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioGroup sex;
    private Spinner spinner1;
    private Spinner spinner2;
    private ListView listView;
    private CheckBox checkBox;
    private Button reg;
    private Button reg_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();

        checkBox.setOnCheckedChangeListener(new checkBoxOnCheckedChangeListener ());
    }

    private void initView() {
        imageView02 = (ImageView) findViewById(R.id.imageView02);
        regUsernameEditText = (EditText) findViewById(R.id.regUsernameEditText);
        regFirstPwdEditText = (EditText) findViewById(R.id.regFirstPwdEditText);
        regSecondPwdEditText = (EditText) findViewById(R.id.regSecondPwdEditText);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        sex = (RadioGroup) findViewById(R.id.sex);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        listView = (ListView) findViewById(R.id.listView);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        reg = (Button) findViewById(R.id.reg);
        reg_cancel = (Button) findViewById(R.id.reg_cancel);

        reg.setOnClickListener(this);
        reg_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg:
                register();
                break;
            case R.id.reg_cancel:
                cancel();
                break;
            case R.id.spinner1:
                spinner_set(spinner1);
                break;
            case R.id.spinner2:
                spinner_set(spinner2);
                break;
        }
    }

    private void submit() {
        // validate
        String regUsernameEditTextString = regUsernameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(regUsernameEditTextString)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String regFirstPwdEditTextString = regFirstPwdEditText.getText().toString().trim();
        if (TextUtils.isEmpty(regFirstPwdEditTextString)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String regSecondPwdEditTextString = regSecondPwdEditText.getText().toString().trim();
        if (TextUtils.isEmpty(regSecondPwdEditTextString)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    private void register(){
        submit();
        if(!regFirstPwdEditText.getText().toString().trim()
                .equals(regSecondPwdEditText.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            final String username = regUsernameEditText.getText().toString();
            final String password = regSecondPwdEditText.getText().toString();
            final String email = "";
            final String grade = spinner2.getSelectedItem().toString();   // 年级
            String gender = "";                                           // 性别
            for(int i=0;i<sex.getChildCount();i++){
                RadioButton rd = (RadioButton) sex.getChildAt(i);
                if (rd.isChecked()) {
                    gender=rd.getText().toString();
                    break;
                }
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("注册失败")
                                    .setNegativeButton("重试",null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(username,password,email,grade,gender,responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }

    }

    //下拉列表赋值，相应函数
    private void spinner_set(Spinner sp){
        final ArrayAdapter<String> _Adapter;
        //从下拉列表中获取值
        String[] arr=null;
        if (sp == spinner1) {
            arr = getResources().getStringArray(R.array.type);
        }else if(sp == spinner2) {
            arr = getResources().getStringArray(R.array.grade);
        }
        // 建立Adapter并且绑定数据源
        _Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arr);
        // 绑定Adapter到控件
        sp.setAdapter(_Adapter);
        //下拉列表选定值后响应
        sp.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3){
                if(position != 0) {
                    _Adapter.getItem(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){
                // TODO Auto-generated method stub
            }
        });


    }

    //复选框控件监听器
    class checkBoxOnCheckedChangeListener implements OnCheckedChangeListener{
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                reg.setVisibility(View.VISIBLE);
            }else {
                reg.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void cancel(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
