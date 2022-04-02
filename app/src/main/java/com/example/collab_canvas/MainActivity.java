package com.example.collab_canvas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;

import java.net.*;
import java.io.*;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {



    DrawingView drawingView;
    private Socket socket;
    private DataOutputStream socket_stream;
    private TouchListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("hello????");

        drawingView = findViewById(R.id.canvas);
        listener = new TouchListener();
        drawingView.setOnTouchListener(listener);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("connecting to server...");
                    socket = new Socket("10.0.2.2", 12000);
                    System.out.println("Connected");
                    socket_stream = new DataOutputStream(socket.getOutputStream());
                    listener.setSocketStream(socket_stream);
                } catch(IOException i) {i.printStackTrace();}
            }
        }).start();
    }


//    // onClose() {
//
//    socket_stream.close();
//    socket.close();
//
//    // }


}
