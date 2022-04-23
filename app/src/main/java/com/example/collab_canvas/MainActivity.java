package com.example.collab_canvas;

import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import java.net.*;
import java.io.*;

import com.example.networking.Packet;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView; // view users can draw on
    private Socket socket;
    private TouchListener touch_listener; // listens for user activity

    private void start_draw_listener() { // begin listening for user activity
        new Thread(new Runnable() { // spawn new thread
            public void run() {try {
                    int current = -1;

                    System.out.println("constructing object input stream...");

                    while (true) { // loop for entire time user is drawing on view
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        System.out.println("draw_listener: waiting for object...");

                        // use packet class so can send over 1 unit over streams
                        Packet pack = (Packet)in.readObject();
                        char c = pack.action;
                        float x = pack.x_coord; // coordinates from the views
                        float y = pack.y_coord;
                        int s = pack.color; // color options, includes white for erasing

                        System.out.println("Received draw-update: ["+c+"]: @("+x+", "+y+") [s = "+s+"]");
                        if (c == 'A') { // first pixel drawn of a line (pushed down)
                           Path path = new Path(); // Path obj for representing lines user drew
                           path.moveTo(x, y); // coords where user drew, as field of Packet class
                           current = drawingView.paths.size();
                           drawingView.paths.add(path);
                           drawingView.attributes.add(s);

                           // else user is now dragging the line, line of pixels
                        } else if (c == 'M') drawingView.paths.get(current).lineTo(x, y);
                        drawingView.invalidate();
                    }
                } catch(Exception i) {i.printStackTrace();}}
        }).start();
    }

    // code for responding to users clicking color options at bottom of the view
    public void eraseButton(View view) { touch_listener.setColor(0x40ffffff); }
    public void blueButton(View view) { System.out.println("GREEN"); touch_listener.setColor(0x102196F3); }
    public void purpleButton(View view) { System.out.println("PURPLE"); touch_listener.setColor(0x10673AB7); }
    public void greenButton(View view) { System.out.println("BLUE"); touch_listener.setColor(0x10AAF0D1); }

    @Override protected void onCreate(Bundle savedInstanceState) { // Bundle used to pass data between activities
            super.onCreate(savedInstanceState);
            System.out.println("started!");
            setContentView(R.layout.activity_main);
            drawingView = findViewById(R.id.canvas); // retrieve view's identifier name
            touch_listener = new TouchListener(); // new so listen for new activity to the view
            drawingView.setOnTouchListener(touch_listener);

            new Thread(new Runnable() {
                @Override
                public void run() {try {
                    System.out.println("Connecting to server...");
                    System.out.flush();

                    try {
                        // use 10.0.2.2 so host loopback interface
                        socket = new Socket("10.0.2.2", 8080);
                        System.out.println("connected."); // No except. thrown, so connection successful
                    } catch (IOException e) { // error establishing a connection through Socket()
                        e.printStackTrace();
                    }
                    touch_listener.setSocketStream(socket);

                    start_draw_listener(); // listen for user activity
                    System.out.println("started touch and draw listener.");

                }catch(Exception e){ e.printStackTrace(); }}
            }).start(); // start the new spawned thread
        }

}
