package com.demo.project1;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread extends Thread {
    Socket clientSocket;
    BufferedReader bufferedReader;
    String receivePoint;
    MyService myService;
//    DataInputStream bufferedReader;
    public ClientThread(MyService myService) {
        this.myService = myService;

    }

    @Override
    public void run() {
        super.run();
        try {
//            InetAddress serverIp = InetAddress.getByName("140.116.6.51");//"140.116.6.51"serverIp
            clientSocket = new Socket("192.168.0.4", 8088);
            Log.v("test","yes1");


            //printStream = new PrintStream(clientSocket.getOutputStream(), true, "UTF-8");
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
//            bufferedReader=new DataInputStream(clientSocket.getInputStream());

            while(true) {
                Log.v("TAGER","123");
                receivePoint = bufferedReader.readLine();
//                receivePoint = bufferedReader.readUTF();
                Log.v("TAGER",""+receivePoint);
                Intent intent = new Intent();
                intent.setAction(Main3Activity.REGISTER);
                intent.putExtra(Main3Activity.KEY, receivePoint);
                this.myService.sendBroadcast(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("test","yes"+e);
        }
    }
}
