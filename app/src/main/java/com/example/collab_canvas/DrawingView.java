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
    private Paint paint = new Paint(); // class holds style and color info
    public ArrayList<Path> paths = new ArrayList<>(); // list of paths user has drawn
    public ArrayList<Integer> attributes = new ArrayList<>();
    public DrawingView(Context context) { super(context); }
    public DrawingView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) { // for every pixel in a path
            int s = attributes.get(i);
            paint.setColor(s|(255<<24)); // set the color based on the lower 3 bytes of s.
            paint.setStrokeWidth(s>>24); // set the stroke width based on the upper byte of s.
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths.get(i), paint);
        }
    }
}
