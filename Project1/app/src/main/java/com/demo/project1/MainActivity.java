package com.demo.project1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button nextPageBtn = (Button) findViewById(R.id.button2);

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main3Activity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void text(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Main3Activity.class);
        startActivity(intent);
    }
}
