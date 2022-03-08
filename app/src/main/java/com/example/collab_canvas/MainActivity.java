package com.example.collab_canvas;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.canvas);
        drawingView.setOnTouchListener(new TouchListener());

    }
}
