package com.shapes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class Plot extends McShape
{
    //VARIABLES
    private double _x;
    private double _y;


    //CONSTRUCTOR

    public Plot(double x, double y, Color colour, int canvasSize)
    {
        super(colour);
        //this._edgeColour = colour;
        this._x = convertToVector(x,canvasSize);
        this._y = convertToVector(y,canvasSize);
    }
    //TODO: Remove fill colour from a LINE and the PLOT classes(load file ramifications)
    //Constructor used when loading data from a file
    public Plot(String values, Color edgecolour, Color fillcolour)
    {
        super(edgecolour);
        String[] parts = values.split(" ");
        this._x = Double.parseDouble(parts[0]);
        this._y = Double.parseDouble(parts[1]);
    }

    //ACCESSORS AND SETTERS

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    //METHODS

    private Line2D.Double createShape(double x, double y)
    {
        return new Line2D.Double(x, y, x, y);
    }


    @Override
    public void draw(Graphics2D g, int currentCanvasSize)
    {
        int ccs = currentCanvasSize;
        Shape s = createShape(this._x*ccs, this._y*ccs);
        g.setPaint(getEdgeColour());
        g.draw(s);
    }

    @Override
    public String commandExport()
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String x = decimalFormat.format(this.getX());
        String y = decimalFormat.format(this.getY());

        return "PLOT "+x+" "+y;
        //return "PLOT "+this.getX()+" "+this.getY();
    }
}
