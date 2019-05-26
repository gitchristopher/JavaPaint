package com.shapes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

/**
 * The {@code Plot} extends the abstract shape class {@code McShape} to give more customisation and control over the object.
 * The class encapsulates the idea of a point object in double precision.
 *
 * The class uses {@code Line2D.Double} to paint the object to the screen
 */
public class Plot extends McShape
{
    //VARIABLES
    private double _x;
    private double _y;

    //CONSTRUCTOR

    /**
     * Constructs and initialises a Plot with the specified coordinates, edge colour, and screen dimensions.
     * @param x The X coordinate of the point
     * @param y The Y coordinate of the point
     * @param colour The edge colour of the {@code Plot}
     * @param canvasSize The current size of the canvas
     */
    public Plot(double x, double y, Color colour, int canvasSize)
    {
        super(colour);
        this._x = convertToVector(x,canvasSize);
        this._y = convertToVector(y,canvasSize);
    }
    //TODO: Remove fill colour from a LINE and the PLOT classes(load file ramifications)
    //Constructor used when loading data from a file

    /**
     * Constructs and initialises a Plot from a saved file with the specified values and colour.
     * @param values A string of one set of vectorised XY coordinates describing Plots location
     * @param edgecolour The edge colour of the {@code Plot}
     * @param fillcolour The fill colour of the {@code Plot}
     */
    public Plot(String values, Color edgecolour, Color fillcolour)
    {
        super(edgecolour);
        String[] parts = values.split(" ");
        this._x = Double.parseDouble(parts[0]);
        this._y = Double.parseDouble(parts[1]);
    }

    //ACCESSORS AND SETTERS

    /**
     * Gets the vectorised X location of the Plot on the canvas
     * @return a double precision number representing the vectorised X coordinate of the Plot
     */
    public double getX() {
        return _x;
    }

    /**
     * Gets the vectorised Y location of the Plot on the canvas
     * @return a double precision number representing the vectorised X coordinate of the Plot
     */
    public double getY() {
        return _y;
    }

    //METHODS

    /**
     * Constructs and initialises an {@code Line2D.Double} based on the encapsulated {@code Line} start and end values
     * In the case of a plot the start and end point are the same location
     * @param x The X coordinate of the point of the Plot in vector format
     * @param y The Y coordinate of the point of the Plot in vector format
     * @return a new {@code Line2D.Double}
     */
    private Line2D.Double createShape(double x, double y)
    {
        return new Line2D.Double(x, y, x, y);
    }


    /**
     * Draws the custom shapes edge using the settings of the current {@code Graphics2D} object
     * @param g the current {@code Graphics2D} context
     * @param currentCanvasSize the canvas size is used to calculate shape size
     */
    @Override
    public void draw(Graphics2D g, int currentCanvasSize)
    {
        int ccs = currentCanvasSize;
        Shape s = createShape(this.getX()*ccs, this.getY()*ccs);
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
        String x = decimalFormat.format(this.getX());
        String y = decimalFormat.format(this.getY());

        return "PLOT "+x+" "+y;
    }
}
