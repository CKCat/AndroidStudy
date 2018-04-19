package com.cat.fileoperator;

import android.app.Activity;
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

public class FileOpreateActivity extends Activity {
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
            //返回一个File对象，其路径是data/data/package/files
            File file = new File(getFilesDir(), "info.txt");

            //返回值也是一个File对象，其路径是data/data/package/cache
            //File file = new File(getCacheDir(), "info.txt");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                fos.write((name + "##" + pass).getBytes());
                fos.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //创建并显示吐司对话框
        Toast.makeText(this, "登录成功", 0).show();


    }

    public void readAccount(){
        File file = new File(getFilesDir(), "info.txt");
        if (file.exists()){
            FileInputStream fis = null;
            try {

                fis = new FileInputStream(file);
                //把字节流转成字符流
                BufferedReader buff = new BufferedReader(new InputStreamReader(fis));
                //读取文件里的用户名和密码
                String text = buff.readLine();
                String[] s = text.split("##");

                et_name.setText(s[0]);
                et_pass.setText(s[1]);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
