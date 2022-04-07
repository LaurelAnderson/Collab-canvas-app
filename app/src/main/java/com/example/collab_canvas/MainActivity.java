package com.example.collab_canvas;

import android.graphics.Path;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private TouchListener touch_listener;

    private void start_draw_listener() {
        new Thread(new Runnable() {
            public void run() {try {
                    int current = -1;
                    while (true) {
                        char c = in.readChar(); float x = in.readFloat(), y = in.readFloat(); int s = in.readInt();
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
                } catch(IOException i) {i.printStackTrace();}}
        }).start();
    }
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingView = findViewById(R.id.canvas);
        touch_listener = new TouchListener();
        drawingView.setOnTouchListener(touch_listener);
        new Thread(new Runnable() {
            @Override public void run() {try {
                    System.out.println("Connecting to server...");
                    socket = new Socket("10.0.2.2", 8080);
                    System.out.println("Connected!");
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    touch_listener.setSocketStream(out);
                    start_draw_listener();
                } catch (IOException i) { i.printStackTrace(); }}
        }).start();
    }
}
