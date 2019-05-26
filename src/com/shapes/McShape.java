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

    /**
     * The shapes edge colour
     * @return a Color object for the given shapes edge
     */
    public abstract Color getEdgeColour();

    /**
     * The shapes fill colour
     * @return a Color object for the given shapes fill
     */
    public abstract Color getFillColour();

    /**
     * Converts the given number (screen coordinate) to a decimal percentage based on the current canvas size
     * @param num a coordinate on the canvas
     * @param canvasSize the current size of the canvas
     * @return a vectorised coordinate
     */
    public double convertToVector(double num, int canvasSize)
    {
        return num / canvasSize;
    }

    /**
     * Draws the given shape to the screen
     * @param g
     * @param currentCanvasSize the canvas size is used to calculate shape size
     */
    public abstract void draw(Graphics2D g, int currentCanvasSize);

    /**
     * Shapes are saved as command & variable strings
     * @return a string representing the data for a given shape
     */
    public abstract String commandExport();
}
