package com.example.collab_canvas;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TouchListener implements View.OnTouchListener {

    private DataOutputStream socket_stream;
    private int current = -1;

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        DrawingView drawingView = (DrawingView) view;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                view.performClick();
                Path path = new Path();
                path.moveTo(x, y);
                current = drawingView.paths.size();
                drawingView.paths.add(path);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("sending an A...");
                             socket_stream.writeChar('A');
                             socket_stream.writeFloat(x);
                             socket_stream.writeFloat(y);
                        } catch(Exception e) { e.printStackTrace(); }
                    }
                }).start();
                break;

            case MotionEvent.ACTION_MOVE:
                drawingView.paths.get(current).lineTo(x, y);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("sending an M...");
                            socket_stream.writeChar('M');
                            socket_stream.writeFloat(x);
                            socket_stream.writeFloat(y);
                        } catch(Exception e) {e.printStackTrace();}
                    }
                }).start();
                break;
        }
        drawingView.invalidate();
        return true;

    }

    public void setSocketStream(DataOutputStream stream) {
        System.out.println("TouchListener: set socket stream.");
        this.socket_stream = stream;
    }
}
