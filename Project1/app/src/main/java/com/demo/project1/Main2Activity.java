package com.demo.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void fire(View view) {
        Intent intent = new Intent();
        intent.setClass(Main2Activity.this,Main3Activity.class);
        startActivity(intent);
    }
}
