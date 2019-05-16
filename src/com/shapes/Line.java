package com.shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends McShape
{
    //VARIABLES
    private Color _colour;

    private Plot _startPoint;
    private Plot _endPoint;

    //CONSTRUCTOR

    public Line(double x1, double y1, double x2, double y2, Color colour, int canvasSize)
    {
        this._colour = colour;
        this._startPoint = new Plot(x1, y1, colour, canvasSize);
        this._endPoint = new Plot(x2, y2, colour, canvasSize);
    }


    //SETTERS AND GETTERS

    public Plot getStartPlot() {
        return _startPoint;
    }

    public Plot getEndPlot() {
        return _endPoint;
    }

    public Color getColor() {
        return _colour;
    }

    //METHODS

    private Line2D.Double createShape(double x1, double y1, double x2, double y2){

        return new Line2D.Double(x1, y1, x2, y2);
    }

    private double convertToVector(double num, int canvasSize)
    {
        return num / canvasSize;
    }

    @Override
    public void draw(Graphics2D g, int currentCanvasSize) {
        int ccs = currentCanvasSize;
        Shape s = createShape(this._startPoint.getX()*ccs, this._startPoint.getY()*ccs,this._endPoint.getX()*ccs, this._endPoint.getY()*ccs);
        g.setPaint(this._colour);
        g.draw(s);
    }
}
