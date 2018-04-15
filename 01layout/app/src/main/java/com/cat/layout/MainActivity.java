package com.cat.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClik(View view) {
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.btn_linear_layout:
                intent = new Intent(this, LinearLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_relative_layout:
                intent = new Intent(this, RelativeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_table_layout:
                intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_frame_layout:
                intent = new Intent(this, FrameActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_calc_layout:
                intent = new Intent(this, CalcActivity.class);
                startActivity(intent);
                break;
        }

    }
}
