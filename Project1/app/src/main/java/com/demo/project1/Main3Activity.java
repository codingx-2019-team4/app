package com.demo.project1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;

public class Main3Activity extends AppCompatActivity {
    public static String REGISTER = "REGISTER";
    public static String KEY = "POSITION";
    ConstraintLayout root;
    Broadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//螢幕保持橫向
        root = (ConstraintLayout) findViewById(R.id.root);

        broadcast = new Broadcast(this, root);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Main3Activity.REGISTER);
        this.registerReceiver(broadcast, filter);


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                root.removeViews(1,root.getChildCount()-1);
//                for (int i = 0; i < StorePositionData.getInstance().position.size(); i++) {
//                    ImageView pointImage = new ImageView(Main3Activity.this);
//                    ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(
//                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//                    pointImage.setLayoutParams(layoutParams2);
//                    pointImage.setX(StorePositionData.getInstance().position.get(i).getX());
//                    pointImage.setY(StorePositionData.getInstance().position.get(i).getY());
//                    if (i == 0) {
//                        pointImage.setImageResource(R.drawable.run_point);
//                    } else {
//                        pointImage.setImageResource(R.drawable.start_point);
//                    }
//                    root.addView(pointImage);
//
//                }
//                StorePositionData.getInstance().position.clear();
//                handler.postDelayed(this, 1000);
//            }
//        }, 0);
    }

//    public void onclick_moving(View view) {
//
//        mTextview3.setPadding(10 + a, 10, 10, 10);
//        a += 10;
//    }

    public class Broadcast extends BroadcastReceiver {
        Context context;
        ConstraintLayout root;

        public Broadcast(Context context, ConstraintLayout root) {
            this.context = context;
            this.root = root;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Main3Activity.REGISTER)) {
                String position = intent.getStringExtra(KEY);
                StorePositionData.getInstance().position.clear();
                Log.v("position", position);
                JSONArray mJSONArray = null;
                try {
                    mJSONArray = new JSONArray(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Log.v("position_test", mJSONArray.length() + "");
                    for (int i = 0; i < mJSONArray.length(); i++) {
                        StorePositionData.getInstance().position.add(new StorePositionData.Position(
                                (int) mJSONArray.getJSONArray(i).get(0),
                                (int) mJSONArray.getJSONArray(i).get(1)));
//                        Log.v("positionX", StorePositionData.getInstance().position.get(i).getX() + "" +
//                                StorePositionData.getInstance().position.get(i).getY() + "");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                root.removeViews(1, root.getChildCount() - 1);
                addNewPosition();
            }
        }

        public void addNewPosition() {
            for (int i = 0; i < StorePositionData.getInstance().position.size(); i++) {
                ImageView pointImage = new ImageView(context);
                ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                pointImage.setLayoutParams(layoutParams2);
                pointImage.setX(StorePositionData.getInstance().position.get(i).getX());
                pointImage.setY(StorePositionData.getInstance().position.get(i).getY());
                if (i == 0) {
                    pointImage.setImageResource(R.drawable.run_point);
                } else {
                    pointImage.setImageResource(R.drawable.start_point);
                }
                root.addView(pointImage);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);

    }
}
