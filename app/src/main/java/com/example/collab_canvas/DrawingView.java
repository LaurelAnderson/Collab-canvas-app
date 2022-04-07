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
    private Paint paint = new Paint();
    public ArrayList<Path> paths = new ArrayList<>();
    public ArrayList<Integer> attributes = new ArrayList<>();
    public DrawingView(Context context) { super(context); }
    public DrawingView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); }
    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            int s = attributes.get(i);
            paint.setColor(s|(255<<24));
            paint.setStrokeWidth(s>>24);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths.get(i), paint);
        }
    }
}
