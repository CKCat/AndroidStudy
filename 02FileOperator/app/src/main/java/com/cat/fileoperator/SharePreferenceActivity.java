package com.cat.fileoperator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class SharePreferenceActivity extends Activity {
    private EditText et_name;
    private EditText et_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flielayout);

        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
    }

    public void Login(View v){
        String name = et_name.getText().toString();
        String pass = et_pass.getText().toString();

        CheckBox cb = (CheckBox) findViewById(R.id.cb_remember);
        //判断选框是否被勾选
        if(cb.isChecked()){
            SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", name);
            editor.putString("pass", pass);
            //提交
            editor.commit();

        }

        //创建并显示吐司对话框
        Toast.makeText(this, "登录成功", 0).show();


    }

    public void readAccount(){
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        String name = sp.getString("name", "");
        String pass = sp.getString("pass", "");

        et_name.setText(name);
        et_pass.setText(pass);
    }
}
