package com.example.collab_canvas;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class TouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        DrawingView drawingView = (DrawingView) view;
        Path path;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                view.performClick();
                path = new Path();
                // Set the beginning of the next line to the point (x,y).
                path.moveTo(x, y);
                drawingView.addPath(path);
                break;
            case MotionEvent.ACTION_MOVE:
                path = drawingView.getLastPath();
                if (path != null) path.lineTo(x, y);
                break;
        }

        // mark the view as needing to be drawn
        drawingView.invalidate();
        return true;

    }

}
