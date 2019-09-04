package com.demo.myprojectrenew;

//import org.bytedeco.leptonica.PIX;
//import org.bytedeco.tesseract.TessBaseAPI;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;


//import org.bytedeco.opencv.global.opencv_imgcodecs;
//import org.bytedeco.opencv.opencv_core.Mat;

import  org.bytedeco.javacpp.opencv_core.Mat;
import  org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageProcessing {

    private String datapath = "";

    private AssetManager asset;

    private Mat map;

    public AndroidFrameConverter converterToBitmap = new AndroidFrameConverter();
    public OpenCVFrameConverter.ToIplImage converterToIplImage = new OpenCVFrameConverter.ToIplImage();
    public OpenCVFrameConverter.ToMat converterToMat =  new OpenCVFrameConverter.ToMat();


//    private File fileDir;

    public ImageProcessing(AssetManager assetPath, File fil){

        asset = assetPath;

        datapath = fil + "/image";

        checkFile(new File(datapath + "/image/"));

        map = opencv_imgcodecs.imread(datapath);

    }

    public Mat getMap(){
        return map;
    }

    public Bitmap imagev2Bitmap(ImageView imgv){
        imgv.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        return bitmap;
    }

    public Mat imagev2Mat(ImageView imgv){
        Bitmap bi = imagev2Bitmap(imgv);
        Frame fr = converterToBitmap.convert(bi);
        Mat mat = converterToMat.convertToMat(fr);
        return mat;
    }




    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            this.copyFiles();
        }
        if(dir.exists()) {
//            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            String datafilepath = datapath+ "/image/";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/image/fireHouse2_cut.jpg";


            InputStream instream = asset.open("/image/fireHouse2_cut.jpg");
            OutputStream outstream = new FileOutputStream(filepath);


            //map = opencv_imgcodecs.imread(instream.toString());

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

