package com.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public class Rectangle extends McShape
{
    //VARIABLES
    private Color _edgeColour;
    private Color _fillColour;
    private double _x1;
    private double _y1;
    private double _x2;
    private double _y2;
    //CONSTRUCTOR
    public Rectangle(double x1, double y1, double x2, double y2, Color edge, Color fill, int canvasSize){

        this._edgeColour = edge;
        this._fillColour = fill;
        this._x1 = convertToVector(x1, canvasSize);
        this._y1 = convertToVector(y1, canvasSize);
        this._x2 = convertToVector(x2, canvasSize);
        this._y2 = convertToVector(y2, canvasSize);
    }

    //CONSTRUCTOR used when loading data from a file
    public Rectangle(String values, Color edgecolour, Color fillcolour)
    {
        this._edgeColour = edgecolour;
        this._fillColour = fillcolour;

        String[] parts = values.split(" ");
        _x1 = Double.parseDouble(parts[0]);
        _y1 = Double.parseDouble(parts[1]);
        _x2 = Double.parseDouble(parts[2]);
        _y2 = Double.parseDouble(parts[3]);
    }



    //SETTERS AND GETTERS

    public double getStartX() {
        return _x1;
    }
    public double getStartY() {
        return _y1;
    }
    public double getEndX() {
        return _x2;
    }
    public double getEndY() {
        return _y2;
    }

    @Override
    public Color getEdgeColour()
    {
        return this._edgeColour;
    }

    @Override
    public Color getFillColour()
    {
        return this._fillColour;
    }

    //METHODS

    private Rectangle2D.Double createRectangle(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g, int currentCanvasSize){
        int ccs = currentCanvasSize;
        Shape s = createRectangle(this._x1*ccs, this._y1*ccs,this._x2*ccs, this._y2*ccs);

        if (this._fillColour != null){
            g.setPaint(this._fillColour);
            g.fill(s);
        }

        g.setPaint(this._edgeColour);
        g.draw(s);
    }

    @Override
    public String commandExport()
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String x1 = decimalFormat.format(_x1);
        String y1 = decimalFormat.format(_y1);
        String x2 = decimalFormat.format(_x2);
        String y2 = decimalFormat.format(_y2);

        return "RECTANGLE "+x1+" "+y1+" "+x2+" "+y2;
    }
}
