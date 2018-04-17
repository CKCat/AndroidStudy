package com.cat.fileoperator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FileOpreateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flielayout);

        EditText et_name = findViewById(R.id.et_name);
        EditText et_pass = findViewById(R.id.et_pass);
    }
    void Login(View v){

    }
}
