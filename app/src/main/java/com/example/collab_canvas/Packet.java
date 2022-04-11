package com.example.collab_canvas;
import java.io.Serializable;

public class Packet implements Serializable {
    char action;
    float x_coord;
    float y_coord;
    int color;

    public Packet(char action, float x, float y, int color) {
        this.action = action;
        this.x_coord = x;
        this.y_coord = y;
        this.color = color;
    }
}
