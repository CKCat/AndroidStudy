package com.cat.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class SecondActivity extends Activity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String malename = bundle.getString("malename");
        Log.d(TAG, "onCreate: " + malename);
    }
}
