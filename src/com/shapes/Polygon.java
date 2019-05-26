package com.shapes;

import java.awt.*;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.gui.McDrawApp.isPolyOpen;

/**
 * The {@code Polygon} extends the abstract shape class {@code McShape} to give more customisation and control over the object.
 * The class encapsulates the idea of a polygon object in double precision.
 *
 * The class uses {@code Path2D.Double} to paint the object to the screen
 */
public class Polygon extends McShape
{
    double[] _xPoly;
    double[] _yPoly;
    public ArrayList<Double> _xList = new ArrayList<Double>();
    public ArrayList<Double> _yList = new ArrayList<Double>();
    private Boolean _isClosed = false;

    /**
     * Constructs and initialises a Polygon with the specified coordinates, colours, and screen dimensions.
     * @param XPoly An array of X coordinate of the {@code Polygon}
     * @param YPoly An array of X coordinate of the {@code Polygon}
     * @param edge The edge colour of the {@code Polygon}
     * @param fill The fill colour, if any, of the {@code Polygon}
     * @param canvasSize The current size of the canvas
     */
    public Polygon(double XPoly, double YPoly, Color edge, Color fill, int canvasSize)
    {
        super(edge, fill);
        _xList.add(convertToVector(XPoly, canvasSize));
        _yList.add(convertToVector(YPoly, canvasSize));
    }

    //CONSTRUCTOR used when loading data from a file

    /**
     * Constructs and initialises a Polygon from a saved file with the specified values and colours.
     * @param values A string of XY coordinates describing all X and Y coordinates in the {@code Polygon}
     * @param edgecolour The edge colour of the {@code Polygon}
     * @param fillcolour The fill colour, if any, of the {@code Polygon}
     */
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

    /**
     * Adds a new set of XY coordinates to the polygon
     * @param xValue a double precision number representing the X location on the canvas
     * @param yValue a double precision number representing the Y location on the canvas
     * @param canvasSize an integer of the current canvas size
     */
    public void addPlot(double xValue, double yValue, int canvasSize)
    {
        _xList.add(convertToVector(xValue, canvasSize));
        _yList.add(convertToVector(yValue, canvasSize));
    }

    /**
     * Gets the vectorised X value of the polygon's last vertex
     * @return a double precision vectorised X coordinate value
     */
    public double getLastX(){
        return _xList.get(_xList.size()-1);
    }

    /**
     * Gets the vectorised Y value of the polygon's last vertex
     * @return a double precision vectorised Y coordinate value
     */
    public double getLastY(){
        return _yList.get(_yList.size()-1);
    }

    /**
     * Gets the vectorised X value of the polygon's first vertex
     * @return a double precision vectorised X coordinate value
     */
    public double getFirstX(){
        return _xList.get(0);
    }

    /**
     * Gets the vectorised Y value of the polygon's first vertex
     * @return a double precision vectorised Y coordinate value
     */
    public double getFirstY(){
        return _yList.get(0);
    }

    /**
     * Gets the polygon's closed property
     * @return a boolean representing the closed status of the Polygon
     */
    public boolean getIsClosed(){
        return this._isClosed;
    }

    /**
     * Sets the polygon's closed property
     * @param isClosed
     */
    public void setIsClosed(boolean isClosed)
    {
        this._isClosed = isClosed;
    }

    /**
     * Closes the Polygon. moves the XY values from the internal list to the internal array
     */
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
        this.setIsClosed(true);
        isPolyOpen = false; // variable on McDrawApp
    }

    /**
     * Constructs and initialises an {@code Path2D.Double} based on the encapsulated {@code Polygon} values
     * @param x an array of double precision canvas coordinates (X)
     * @param y an array of double precision canvas coordinates (Y)
     * @return a new {@code Path2D.Double}
     */
    public Path2D.Double createPath(double[] x, double[] y)
    {
        Path2D.Double thePath = new Path2D.Double();

        thePath.moveTo(x[0],y[0]);
        for (int i = 1; i < x.length; i++)
        {
            thePath.lineTo(x[i],y[i]);
        }

        if (getIsClosed())
        {
            thePath.closePath();
        }

        return thePath;
    }


    /**
     * Draws the custom shapes edge and fill using the settings of the current {@code Graphics2D} object
     * @param g the current {@code Graphics2D} context
     * @param currentCanvasSize the canvas size is used to calculate shape size
     */
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

    /**
     * Constructs a string representing the object and its encapsulated values
     * @return a string representing the object
     */
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
