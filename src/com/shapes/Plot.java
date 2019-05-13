package com.shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class Plot extends McShape
{
    //VARIABLES
    private Color _colour;
    private double _x;
    private double _y;


    //CONSTRUCTOR

    public Plot(double x, double y, Color colour, int canvasSize)
    {
        this._colour = colour;
        this._x = convertToVector(x,canvasSize);
        this._y = convertToVector(y,canvasSize);
    }

    //ACCESSORS AND SETTORS

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }


    //METHODS

    //Converts a given number to the vector equivalent.
    private double convertToVector(double num, int canvasSize)
    {
        return num / canvasSize;
    }

    private Line2D.Double createShape(double x, double y)
    {
        return new Line2D.Double(x, y, x, y);
    }

    @Override
    public void draw(Graphics2D g, int currentCanvasSize)
    {
        int ccs = currentCanvasSize;
        Shape s = createShape(this._x*ccs, this._y*ccs);
        g.setPaint(this._colour);
        g.draw(s);
    }
}
