package com.example.networking;

import java.io.Serializable;

// class can be used to send packet info over stream as a single unit
public class Packet implements Serializable {
    public char action; // user either pushed down, or they are dragging on the view
    public float x_coord; // coords of where user drew on the view
    public float y_coord;
    public int color;

    // simple constructor, class acts as a struct
    public Packet(char action, float x, float y, int color) {
        this.action = action;
        this.x_coord = x;
        this.y_coord = y;
        this.color = color;
    }
}
