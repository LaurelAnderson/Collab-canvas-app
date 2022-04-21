package com.example.collab_canvas;

import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import java.net.*;
import java.io.*;

import com.example.networking.Packet;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView;
    private Socket socket;
    private TouchListener touch_listener;

    private void start_draw_listener() {
        new Thread(new Runnable() {
            public void run() {try {
                    int current = -1;

                    System.out.println("constructing object input stream...");

                    while (true) {

                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                        // change to packet
//                        char c = in.readChar(); float x = in.readFloat(), y = in.readFloat(); int s = in.readInt();

                        System.out.println("draw_listener: waiting for object...");

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

    public void eraseButton(View view) { touch_listener.setColor(0x40ffffff); }
    public void blueButton(View view) { System.out.println("GREEN"); touch_listener.setColor(0x102196F3); }
    public void purpleButton(View view) { System.out.println("PURPLE"); touch_listener.setColor(0x10673AB7); }
    public void greenButton(View view) { System.out.println("BLUE"); touch_listener.setColor(0x10AAF0D1); }

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
