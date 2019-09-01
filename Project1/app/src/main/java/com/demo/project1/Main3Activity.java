package com.demo.project1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

public class Main3Activity extends AppCompatActivity {
    public static String REGISTER = "REGISTER";
    public static String KEY = "POSITION";
    TextView mTextview3;
    int a = 0, b, c, d;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//螢幕保持橫向

        mTextview3 = (TextView) findViewById(R.id.textView3);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextview3.setX(StorePositionData.getInstance().getY());
                mTextview3.setY(StorePositionData.getInstance().getY());
                handler.postDelayed(this,1000);
            }
        },2000);
    }

    public void onclick_moving(View view) {

        mTextview3.setPadding(10 + a, 10, 10, 10);
        a += 10;
    }

    public static class Broadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(Main3Activity.REGISTER)) {
                String position = intent.getStringExtra(KEY);

                JSONArray mJSONArray = new JSONArray(Arrays.asList(position));
                try {
                    for(int i = 0;i < mJSONArray.length();i++ ) {
                        StorePositionData.getInstance().setPostion(
                                (int)mJSONArray.getJSONArray(i).get(0),
                                (int)mJSONArray.getJSONArray(i).get(1)
                        );
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//                Log.v("position", position);
//                StorePositionData.getInstance().setPostion(position,position);
            }
        }
    }
}
