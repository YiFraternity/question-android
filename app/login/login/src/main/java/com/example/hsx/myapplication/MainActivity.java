package com.example.hsx.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.app.Activity;


public class MainActivity extends Activity {


    private Button reg = null;
    private int location = -1;
    private Spinner spinner = null;
    private CheckBox checkBox = null;
    private EditText editText01 = null ;
    private EditText editText02 = null;
    private EditText editText03 = null;
    private RadioButton radio =null ;
    private ListView listView = null;
    private RadioGroup sex;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到关心的控件
        reg  = (Button) findViewById(R.id.reg);
        spinner  = (Spinner) findViewById(R.id.spinner);
        checkBox  = (CheckBox) findViewById(R.id.checkBox);
        editText01  = (EditText) findViewById(R.id.editText01);
        editText02  = (EditText) findViewById(R.id.editText02);
        editText03   = (EditText) findViewById(R.id.editText03);

        listView  = (ListView) findViewById(R.id.listView);
        sex = (RadioGroup) findViewById(R.id.sex);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.care, android.R.layout.simple_spinner_item);
        listView.setAdapter(adapter);//适配器与列表视图关联
        //为复选框控件添加监听器
        checkBox.setOnCheckedChangeListener(new checkBoxOnCheckedChangeListener ());

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radio = (RadioButton) findViewById(checkedId);

            }
        });
        spinner.setOnItemSelectedListener(new spinnerOnItemSelectedListener());
        reg.setOnClickListener(new regOnClickListener());
    }


    class regOnClickListener implements OnClickListener{
        public void onClick(View v) {

            Log.i("您输入的用户名为：", editText01.getText().toString());
            Log.i("您输入的密码为：", editText02.getText().toString());
            Log.i("您输入的确认密码为：", editText03.getText().toString());

            if (radio != null) {
                Log.i("您选择的性别为：", radio.getText().toString());
            }else {
                Log.i("您选择的性别为：", "无");
            }
            Log.i("您选择的身份为：", spinner.getItemAtPosition(location).toString());
        }
    }

    class spinnerOnItemSelectedListener implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //获取下拉列表框控件选中的位置
            location = position;
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }


    //复选框控件监听器
    class checkBoxOnCheckedChangeListener implements OnCheckedChangeListener{
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                reg.setVisibility(View.VISIBLE);
            }else {
                reg.setVisibility(View.INVISIBLE);
            }
        }
    }
}
