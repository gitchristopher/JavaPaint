package com.shapes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class Line extends McShape
{
    //VARIABLES
    private Color _edgeColour;
    private Color _fillColour;

    private Plot _startPoint;
    private Plot _endPoint;

    //CONSTRUCTOR

    public Line(double x1, double y1, double x2, double y2, Color colour, int canvasSize)
    {
        this._edgeColour = colour;
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


    //METHODS

    private Line2D.Double createShape(double x1, double y1, double x2, double y2){

        return new Line2D.Double(x1, y1, x2, y2);
    }

    private double convertToVector(double num, int canvasSize)
    {
        return num / canvasSize;
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

    @Override
    public void draw(Graphics2D g, int currentCanvasSize) {
        int ccs = currentCanvasSize;
        Shape s = createShape(this._startPoint.getX()*ccs, this._startPoint.getY()*ccs,this._endPoint.getX()*ccs, this._endPoint.getY()*ccs);
        g.setPaint(this._edgeColour);
        g.draw(s);
    }

    @Override
    public String commandExport()
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String x1 = decimalFormat.format(getStartPlot().getX());
        String y1 = decimalFormat.format(getStartPlot().getY());
        String x2 = decimalFormat.format(getEndPlot().getX());
        String y2 = decimalFormat.format(getEndPlot().getY());

        return "LINE "+x1+" "+y1+" "+x2+" "+y2;
        //return "LINE "+getStartPlot().getX()+" "+getStartPlot().getY()+" "+getEndPlot().getX()+" "+getEndPlot().getY();
    }
}
