package com.example.collab_canvas;

import android.graphics.Path;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView;
    private Socket socket;
    private TouchListener touch_listener;

    private void start_draw_listener() {
        new Thread(new Runnable() {
            public void run() {try {
                    int current = -1;
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    while (true) {

                        // change to packet
//                        char c = in.readChar(); float x = in.readFloat(), y = in.readFloat(); int s = in.readInt();
                        Packet pack = (Packet)in.readObject();
                        char c = pack.action;
                        float x = pack.x_coord;
                        float y = pack.y_coord;
                        int s = pack.color;

                        System.out.println("Received draw-update: ["+c+"]: @("+x+", "+y+") [s = "+s+"]");
                        if (c == 'A') {
                           Path path = new Path();
                           path.moveTo(x, y);
                           current = drawingView.paths.size();
                           drawingView.paths.add(path);
                           drawingView.attributes.add(s);
                        } else if (c == 'M') drawingView.paths.get(current).lineTo(x, y);
                        drawingView.invalidate();
                    }
                } catch(Exception i) {i.printStackTrace();}}
        }).start();
    }
    @Override protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            System.out.println("started!");  //
            setContentView(R.layout.activity_main);
            drawingView = findViewById(R.id.canvas);
            touch_listener = new TouchListener();
            drawingView.setOnTouchListener(touch_listener);

            new Thread(new Runnable() {
                @Override
                public void run() {try {
                    System.out.println("Connecting to server..."); System.out.flush(); //

                    try {
                        socket = new Socket("10.0.2.2", 8080);
                        System.out.println("connected.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    touch_listener.setSocketStream(socket);
                    start_draw_listener();
                    System.out.println("started touch and draw listener.");

                }catch(Exception e){ e.printStackTrace(); }}
            }).start();
        }

}
