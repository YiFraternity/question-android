package com.example.hsx.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionlistActivity extends Activity implements View.OnClickListener{

    private static final String QUESTION_REQUEST_URL = "http://120.79.138.185/Questionlist.php";

    ArrayList<HashMap<String,String>> questionsList;
    private List<HashMap<String, String>> mData;  // 为什么要用List<HashMap>????

    // 创建json解析对象
    JSONParser jsonParser = new JSONParser();

    private ProgressDialog progressDialog;

    private TextView title;
    private ListView questionlist;
    private TextView QAquestions;
    private TextView QAanswer;
    private TextView QAuser;
    private LinearLayout QAmenu;

    UserInfo userInfo;
    String[] quest=new String[60];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_list);
        initView();
        //  TEST
        System.out.println("****************啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈*******************");

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");
        String grade = intent.getStringExtra("grade");

        String mobile = "xxx";
        if (email.equals(""))
            email = "xxx";

        userInfo = new UserInfo(username,email,gender,grade,mobile);

        questionlist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获得选中项的HashMap对象
                HashMap<String,String> hashMap=mData.get(position);
                System.out.println("远方传来风笛，而我只在意你的消息"+position);
                System.out.print(hashMap.size());
                String title=hashMap.get("title");
                String info=hashMap.get("info");
                Intent intent = new Intent(QuestionlistActivity.this,mainActivity.class);
                intent.putExtra("username",userInfo.getUsername());
                intent.putExtra("email",userInfo.getEmail());
                intent.putExtra("grade",userInfo.getGrade());
                intent.putExtra("gender",userInfo.getGender());
                intent.putExtra("mobile",userInfo.getMobile());
                intent.putExtra("question",info);
                intent.putExtra("question_number",title);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),
//                        "你选择了第"+position+"个Item，title的值是："+title+"info的值是:"+info,
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onStart(){
        super.onStart();
        get_question();
        // TEST
        System.out.println("阿斯顿发射点发射点发射点亲热我个人话题体育局");

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        questionlist = (ListView) findViewById(R.id.questionlist);
        QAquestions = (TextView) findViewById(R.id.QAquestions);
        QAanswer = (TextView) findViewById(R.id.QAanswer);
        QAanswer.setOnClickListener(this);
        QAuser = (TextView) findViewById(R.id.QAuser);
        QAuser.setOnClickListener(this);
        QAmenu = (LinearLayout) findViewById(R.id.QAmenu);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.QAanswer:
                answer();
                break;
            case R.id.QAuser:
                user();
                break;
        }
    }

    /**
     * 自定义适配器
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;// 动态布局映射

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        // 决定ListView有几行可见
        @Override
        public int getCount() {
            return mData.size();// ListView的条目数
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.first_list_item, null);            //根据布局文件实例化view
            TextView title = (TextView) convertView.findViewById(R.id.first_list_item_title);//找某个控件
            title.setText(mData.get(position).get("title"));                      //给该控件设置数据(数据从集合类中来)
            TextView info = (TextView)convertView.findViewById(R.id.first_list_item_info);   //找到某个控件
            info.setText(mData.get(position).get("info"));
            return convertView;
        }
    }

//    private void questions(){
//        Intent intent = new Intent(QuestionlistActivity.this, QuestionlistActivity.class);
//        startActivity(intent);
//    }

    private void answer() {
        Intent intent = new Intent(QuestionlistActivity.this, mainActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }

    private void user() {
        Intent intent = new Intent(QuestionlistActivity.this, PersonalCenterActivity.class);
        intent.putExtra("username",userInfo.getUsername());
        intent.putExtra("email",userInfo.getEmail());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("gender",userInfo.getGender());
        intent.putExtra("mobile",userInfo.getMobile());
        startActivity(intent);
    }

    private void get_question(){
        // TEST
        System.out.println("啊手动阀手动阀手动阀手动阀的说法都是撒旦撒可见度");
        StringRequest getRequest = new StringRequest(Request.Method.GET, QUESTION_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TEST
                        System.out.println("哈哈山东i弘法大师客户分级撒旦撒可见度");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                // TEST
                                System.out.println("偶爱时间都覅饿哦激发i俄军佛龛i");
                                int count = jsonObject.length();
                                String[] result = new String[count];
                                mData = new ArrayList<HashMap<String, String>>();
                                System.out.println("alskjdfoiaewfjs");
                                // 第一个是success
                                for (int i = 0; i < count - 1; i++) {
                                    int j = i + 1;
                                    result[i] = jsonObject.getString("question" + j);

                                    // creating new HashMap
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    // adding each child node to HashMap key => value
                                    map.put("title", "题目" + j);
                                    map.put("info", result[i]);
                                    // adding HashList to ArrayList
                                    mData.add(map);
                                    System.out.println(mData.get(i).get("info"));
//                                    volleyCallback.onSuccess(result);
                                }
                                // TEST
                                System.out.println("a啊手动阀时代发的发夫人温热管委会天热天涯海角一热就" + mData.size());
                                MyAdapter adapter = new MyAdapter(QuestionlistActivity.this);//创建一个适配器
                                questionlist.setAdapter(adapter);//为ListView控件绑定适配器

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(QuestionlistActivity.this);
        queue.add(getRequest);
    }

}
