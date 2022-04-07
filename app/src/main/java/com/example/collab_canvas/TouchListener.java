package com.example.collab_canvas;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import java.io.DataOutputStream;

public class TouchListener implements View.OnTouchListener {

    private DataOutputStream out;
    private int current = -1;
    private boolean erasing = false;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
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
                        System.out.println("sending A packet...");
                        out.writeChar('A');
                        out.writeFloat(x);
                        out.writeFloat(y);
                        out.writeInt(s);
                    } catch(Exception e) { e.printStackTrace(); }}
                }).start();
                break;
            case MotionEvent.ACTION_MOVE:
                drawingView.paths.get(current).lineTo(x, y);
                new Thread(new Runnable() {
                    @Override
                    public void run() {try {
                        System.out.println("sending M packet...");
                        out.writeChar('M');
                        out.writeFloat(x);
                        out.writeFloat(y);
                        out.writeInt(0);
                    } catch(Exception e) {e.printStackTrace();}}
                }).start();
                break;
        }
        drawingView.invalidate();
        return true;
    }
    public void setSocketStream(DataOutputStream o) { out = o; }
}
