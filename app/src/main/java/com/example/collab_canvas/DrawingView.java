package com.example.collab_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawingView extends View {

    private final ArrayList<Path> paths = new ArrayList<>();

    public DrawingView(Context context) {
        super(context);
    }
    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void addPath(Path path) {
        paths.add(path);
    }

    public Path getLastPath() {
        if (paths.size() > 0) {
            return paths.get(paths.size() - 1);
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Path path : paths) {
            Paint paint = new Paint();
            // set all the line attributes
            paint.setColor(0xff000000); // black for now
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10f);
            // paint the lines in path
            canvas.drawPath(path, paint);
        }
    }

}
