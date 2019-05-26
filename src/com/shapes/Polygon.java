package com.shapes;

import java.awt.*;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.gui.McDrawApp.isPolyOpen;

public class Polygon extends McShape
{
    double[] _xPoly;
    double[] _yPoly;
    public ArrayList<Double> _xList = new ArrayList<Double>();
    public ArrayList<Double> _yList = new ArrayList<Double>();
    Boolean _isClosed = false;

    public Polygon(double XPoly, double YPoly, Color edge, Color fill, int canvasSize)
    {
        super(edge, fill);
        _xList.add(convertToVector(XPoly, canvasSize));
        _yList.add(convertToVector(YPoly, canvasSize));
    }

    //CONSTRUCTOR used when loading data from a file
    public Polygon(String values, Color edgecolour, Color fillcolour)
    {
        super(edgecolour, fillcolour);

        String[] parts = values.split("\\s");
        for (int i = 0; i < parts.length; i++)
        {
            _xList.add(Double.parseDouble(parts[i]));
            _yList.add(Double.parseDouble(parts[i+1]));
            i++;
        }
        finishPolygon();
    }




    public void addPlot(double xValue, double yValue, int canvasSize)
    {
        _xList.add(convertToVector(xValue, canvasSize));
        _yList.add(convertToVector(yValue, canvasSize));
    }

    public double getLastX(){
        return _xList.get(_xList.size()-1);
    }
    public double getLastY(){
        return _yList.get(_yList.size()-1);
    }
    public double getFirstX(){
        return _xList.get(0);
    }
    public double getFirstY(){
        return _yList.get(0);
    }

    public void finishPolygon()
    {
        this._xPoly = new double[_xList.size()];
        int counter = 0;
        for (double d: _xList)
        {
            this._xPoly[counter] = d;
            counter++;
        }
        this._yPoly = new double[_yList.size()];
        counter = 0;
        for (double d: _yList)
        {
            this._yPoly[counter] = d;
            counter++;
        }
        this._isClosed = true;
        isPolyOpen = false; // variable on McDrawApp
    }

    public Path2D.Double createPath(double[] x, double[] y)
    {
        Path2D.Double thePath = new Path2D.Double();

        thePath.moveTo(x[0],y[0]);
        for (int i = 1; i < x.length; i++)
        {
            thePath.lineTo(x[i],y[i]);
        }

        if (_isClosed){thePath.closePath();}

        return thePath;
    }


    @Override
    public void draw(Graphics2D g, int currentCanvasSize)
    {

        int ccs = currentCanvasSize;
        double[] xPolyTemp = new double[this._xList.size()];
        double[] yPolyTemp = new double[this._yList.size()];

        for (int i = 0; i < this._xList.size(); i++)
        {
            xPolyTemp[i] = this._xList.get(i) * ccs;
            yPolyTemp[i] = this._yList.get(i) * ccs;
        }
        Shape s = createPath(xPolyTemp, yPolyTemp);

        if (getFillColour() != null)
        {
            g.setPaint(getFillColour());
            g.fill(s);
        }

        g.setPaint(getEdgeColour());
        g.draw(s);
    }

    @Override
    public String commandExport()
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        StringBuilder coordinates = new StringBuilder();

        for (int i = 0; i < _xPoly.length; i++)
        {
            coordinates.append(decimalFormat.format(_xPoly[i]));
            coordinates.append(" ");
            coordinates.append(decimalFormat.format(_yPoly[i]));
            coordinates.append(" ");
        }
        coordinates.deleteCharAt(coordinates.lastIndexOf(" "));

        return "POLYGON "+coordinates.toString();
    }
}
