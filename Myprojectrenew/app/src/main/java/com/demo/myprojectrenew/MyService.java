package com.demo.myprojectrenew;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    ClientThread clientThread;
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("test","yes");
        clientThread = new ClientThread(this);
        clientThread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
