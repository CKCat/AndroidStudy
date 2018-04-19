package com.cat.fileoperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.btn_file:
                intent = new Intent(this, FileOpreateActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_sp:
                intent = new Intent(this, SharePreferenceActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_write_xml:
                intent = new Intent(this, WriteXmlActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_read_xml:
                intent = new Intent(this, ReadXmlActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_sql:
                intent = new Intent(this, SqlActivity.class);
                startActivity(intent);
                break;
        }
    }
}
