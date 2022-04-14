package com.example.networking;

import java.io.Serializable;

public class Packet implements Serializable {
    public char action;
    public float x_coord;
    public float y_coord;
    public int color;

    public Packet(char action, float x, float y, int color) {
        this.action = action;
        this.x_coord = x;
        this.y_coord = y;
        this.color = color;
    }
}
