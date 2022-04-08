package com.example.collab_canvas;
import java.io.Serializable;

public class Packet implements Serializable {
    char action;
    int x_coord;
    int y_coord;
    int color;

    public Packet(boolean isMoveAction, int x, int y, int color) {
        if (isMoveAction) action = 'M';
        else action = 'A';
        x_coord = x;
        y_coord = y;
        this.color = color;
    }
}
