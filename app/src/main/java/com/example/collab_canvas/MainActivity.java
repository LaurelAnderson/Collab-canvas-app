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
                    System.out.println("Draw Listener: started");
                    int current = -1;

                    while (true) {
                        System.out.println("Draw Listener: waiting for draw-update packet...");
                        char c = in.readChar();
                        float x = in.readFloat();
                        float y = in.readFloat();
                        int s = in.readInt();
                        System.out.println("Draw Listener: received draw-update packet: ["+c+"]: @("+x+", "+y+") [s = "+s+"]");

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Main: started application.");
        drawingView = findViewById(R.id.canvas);
        touch_listener = new TouchListener();
        drawingView.setOnTouchListener(touch_listener);
        System.out.println("Main: starting networking thread...");
        new Thread(new Runnable() {
            @Override
            public void run() {try {
                    System.out.println("Net: Connecting to server...");
                    socket = new Socket("10.0.2.2", 8080);
                    System.out.println("Net: Connected!");
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    touch_listener.setSocketStream(out);
                    System.out.println("Net: Starting draw listener thread...");
                    start_draw_listener();
                } catch (IOException i) { i.printStackTrace(); }}
        }).start();
    }
}
