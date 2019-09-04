package com.demo.myprojectrenew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


//import org.bytedeco.opencv.opencv_core.Mat;
//import org.bytedeco.opencv.opencv_core.Point;
//import org.bytedeco.opencv.opencv_core.Scalar;
//import org.bytedeco.opencv.global.opencv_imgcodecs;
//import org.bytedeco.opencv.global.opencv_imgproc;

import  org.bytedeco.javacpp.opencv_core.Mat;
import  org.bytedeco.javacpp.opencv_core.Point;
import  org.bytedeco.javacpp.opencv_core.Scalar;
import  org.bytedeco.javacpp.opencv_imgproc;
import  org.bytedeco.javacpp.opencv_imgcodecs;




import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class Main3Activity extends AppCompatActivity {
    public static String REGISTER = "REGISTER";
    public static String KEY = "POSITION";
    ConstraintLayout root;
    Broadcast broadcast;

    private AssetManager assetManager;
    private File file;

    private ImageProcessing imageP;

    private Mat map;
    private Mat mapE;

    public AndroidFrameConverter converterToBitmap;
    //public OpenCVFrameConverter.ToIplImage converterToIplImage = new OpenCVFrameConverter.ToIplImage();
    public OpenCVFrameConverter.ToMat converterToMat;


    public ImageView pointImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        root = (ConstraintLayout) findViewById(R.id.root);

        broadcast = new Broadcast(this, root);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Main3Activity.REGISTER);
        this.registerReceiver(broadcast, filter);


        map = new Mat();
        mapE = new Mat();

        converterToBitmap = new AndroidFrameConverter();
        converterToMat =  new OpenCVFrameConverter.ToMat();



        assetManager = getAssets();
        file = getFilesDir();


        imageP = new ImageProcessing(assetManager,file);



        pointImage = (ImageView)findViewById(R.id.photo_view);

        map = imageP.imagev2Mat(pointImage);

        map.copyTo(mapE);

        pointImage.getLayoutParams().height = 0;
        pointImage.getLayoutParams().width = 0;

        Intent intent = new Intent(this, MyService.class);
        startService(intent);

    }

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
                if(position == null) {
                    return;
                }
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


//                pt = new Point(StorePositionData.getInstance().position.get(i).getX(),
//                                        StorePositionData.getInstance().position.get(i).getY());

                //Point pt=new Point(100,200);
                Point pt=new Point();
                pt.x(StorePositionData.getInstance().position.get(i).getX());
                pt.y(StorePositionData.getInstance().position.get(i).getY());
                //opencv_imgproc.circle(map,pt,3,new Scalar(0,0,255,0));
                Log.v("pointpoint", pt.x()+" "+pt.y());
                opencv_imgproc imgR = new opencv_imgproc();
                Log.v("pointpoint", imgR+"");
                //imgR.circle(map,pt,3,new Scalar(255,255,0,1),2,2,1);
                if (i==0){
                    imgR.circle(mapE,pt,2,new Scalar(255,0,0,200));
                }else{
                    imgR.circle(mapE,pt,2,new Scalar(0,0,255,200));
                }


            }

            Frame frame_after = converterToMat.convert(mapE);
            Bitmap bitAfter = converterToBitmap.convert(frame_after);
            pointImage.setImageBitmap(bitAfter);
            map.copyTo(mapE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);

    }
}
