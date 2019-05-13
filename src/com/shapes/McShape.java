package com.shapes;

import java.awt.*;

/**
 * A Shape class to encapsulate a
 * Shape and Graphic2D class
 */

public abstract class McShape {
    private Shape _shape;
    private Graphics2D _2dShape;
    private Color _edgeColour;


    public McShape(){}


    // Draws the shape to the screen
    public abstract void draw(Graphics2D g, int currentCanvasSize);
}
