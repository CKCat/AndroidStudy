package com.cat.intent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        int id = view.getId();
        Intent intent = new Intent();;
        switch (id){
            case R.id.btn_call:
                //隐式意图打电话
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:1110"));
                startActivity(intent);
                break;
            case R.id.btn_baidu:
                //隐式打开百度
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
                break;
            case R.id.btn_jmp_second:
                //隐式跳转secondActivity
//                intent.setAction("com.cat.intent.a1");
//                intent.setData(Uri.parse("cat1"));

                intent.setAction("com.cat.intent.a3");
                intent.setDataAndType(Uri.parse("ckcat"), "text/username");
                startActivity(intent);
                break;
            case R.id.btn_second:
                intent.setClass(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_data:
                intent.setClass(this, SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("malename", "张三");
                bundle.putString("femalename", "李四");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    public void onclickAdd(View view) {

    }

    public void onclickRequest(View view) {
    }

    public void onclickSendSMS(View view) {
    }
}
