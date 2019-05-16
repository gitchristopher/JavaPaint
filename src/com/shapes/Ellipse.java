package com.shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ellipse extends McShape
{
    //VARIABLES
    private Color _edgeColour;
    private Color _fillColour;
    private double _x1;
    private double _y1;
    private double _x2;
    private double _y2;
    //CONSTRUCTOR
    public Ellipse(double x1, double y1, double x2, double y2, Color edge, Color fill, int canvasSize){

        this._edgeColour = edge;
        this._fillColour = fill;
        this._x1 = convertToVector(x1, canvasSize);
        this._y1 = convertToVector(y1, canvasSize);
        this._x2 = convertToVector(x2, canvasSize);
        this._y2 = convertToVector(y2, canvasSize);
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
    public Color getEdgeColour() {
        return _edgeColour;
    }
    public Color getFillColour() {
        return _fillColour;
    }


    //METHODS
    private double convertToVector(double num, int canvasSize){
        return num / canvasSize;
    }
    private Ellipse2D.Double createEllipse(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the Ellipse
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Ellipse2D.Double(x, y, width, height);
    }
    @Override
    public void draw(Graphics2D g, int currentCanvasSize){
        int ccs = currentCanvasSize;
        Shape s = createEllipse(this._x1*ccs, this._y1*ccs,this._x2*ccs, this._y2*ccs);
        g.setPaint(this._edgeColour);
        g.draw(s);
        if (this._fillColour != null){
            g.setPaint(this._fillColour);
            g.fill(s);
        }
    }
}
