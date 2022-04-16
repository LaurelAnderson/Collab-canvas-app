package com.example.collab_canvas;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.example.networking.Packet;

public class TouchListener implements View.OnTouchListener {
    private ObjectOutputStream o;
    private int current = -1;
    private boolean erasing = false;
    public void setSocketStream(Socket socket) {

        try {
            this.o = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.o == null)
            System.out.println("setSocketStream: this.o is null");
        else
            System.out.println("setSocketStream: this.o is not null");

    }
    @Override public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int s = erasing ? 0x40ffffff : 10 << 24;
        DrawingView drawingView = (DrawingView) view;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (x < 50 && y < 50) erasing = !erasing; // temporary
                view.performClick();
                Path path = new Path();
                path.moveTo(x, y);
                current = drawingView.paths.size();
                drawingView.paths.add(path);
                drawingView.attributes.add(s);
                new Thread(new Runnable() {
                    @Override
                    public void run() {try {

                        // create packet + send over stream
                        System.out.println(o);
                        System.out.println("sending: " +x+ ", " +y+ ", " +s);
                        o.writeObject(new Packet('A', x, y, s));

                    } catch(Exception e) { e.printStackTrace(); }}
                }).start();
                break;
            case MotionEvent.ACTION_MOVE:
                drawingView.paths.get(current).lineTo(x, y);
                new Thread(new Runnable() {
                    @Override
                    public void run() {try {
                        // create packet + send over stream
//                        out.writeChar('M'); out.writeFloat(x); out.writeFloat(y); out.writeInt(0);
                        if (o == null) System.out.println("Out is null");
                        o.writeObject(new Packet('M', x, y, 0));
                    } catch(Exception e) {e.printStackTrace();}}
                }).start();
                break;
        }
        drawingView.invalidate();
        return true;
    }
}
