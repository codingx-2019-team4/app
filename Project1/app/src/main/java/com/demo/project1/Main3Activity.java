package com.demo.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.Random;

public class Main3Activity extends AppCompatActivity {

    TextView mTextview3 = (TextView) findViewById(R.id.textView3);
    int a=0,b,c,d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


    }

    public void onclick_moving(View view){

        mTextview3.setPadding(10+a,10,10,10);
        a+=10;
    }

}
