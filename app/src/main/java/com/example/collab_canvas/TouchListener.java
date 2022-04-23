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
    private Socket socket;
    private int current = -1;
    private int color = 10 << 24; // drawing color begins as default black
    public void setSocketStream(Socket socket) { this.socket = socket; }
    public void setColor(int c) { color = c; } // called when user hits color buttons

    @Override public boolean onTouch(View view, MotionEvent event) { // when user touches the view
        float x = event.getX(); // get the coords user touched on the view
        float y = event.getY();
        int s = color;
        DrawingView drawingView = (DrawingView) view;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // user first touches (first pixel of a draw)
                view.performClick();
                Path path = new Path();
                path.moveTo(x, y); // use coords gotten from the view passed
                current = drawingView.paths.size(); // get size of the path obj
                drawingView.paths.add(path);
                drawingView.attributes.add(s);

                new Thread(new Runnable() { // spawn a new thread
                    @Override
                    public void run() {try { // create packet + send over stream
                        System.out.println("sending: " +x+ ", " +y+ ", " +s);
                        // sending coords, action type and color info over the stream
                        ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
                        o.writeObject(new Packet('A', x, y, s));

                    // catch any errors while trying to send info over the stream
                    } catch(Exception e) { e.printStackTrace(); }}
                }).start();
                break;

            case MotionEvent.ACTION_MOVE: // user has initially touched already, now dragging
                drawingView.paths.get(current).lineTo(x, y);
                new Thread(new Runnable() { // spawn thread to send over info for move/dragging action
                    @Override
                    public void run() {try {
                        ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
                        o.writeObject(new Packet('M', x, y, 0));

                    } catch(Exception e) {e.printStackTrace();}}
                }).start();
                break;
        }
        drawingView.invalidate(); // invalidate == redraw on the screen, call view's onDraw() method
        return true;
    }
}
