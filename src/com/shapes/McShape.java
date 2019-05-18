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
    private Color _fillColour;

    public McShape(){}

    public abstract Color getEdgeColour();
    public abstract Color getFillColour();

    // Draws the shape to the screen
    public abstract void draw(Graphics2D g, int currentCanvasSize);
    // Returns a string used in the save file
    public abstract String commandExport();
}
