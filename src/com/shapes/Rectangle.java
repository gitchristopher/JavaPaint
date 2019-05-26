package com.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

/**
 * The {@code Rectangle} extends the abstract shape class {@code McShape} to give more customisation and control over the object.
 * The class encapsulates the idea of a rectangle object in double precision.
 *
 * The class uses {@code Rectangle2D.Double} to paint the object to the screen
 */
public class Rectangle extends McShape
{
    //VARIABLES
    private double _x1;
    private double _y1;
    private double _x2;
    private double _y2;

    //CONSTRUCTOR

    /**
     * Constructs and initialises a Rectangle with the specified coordinates, colours, and screen dimensions.
     * @param x1 The X coordinate of the first corner of the Rectangle
     * @param y1 The Y coordinate of the first corner of the Rectangle
     * @param x2 The X coordinate of the opposite corner of the Rectangle
     * @param y2 The Y coordinate of the opposite corner of the Rectangle
     * @param edge The edge colour of the {@code Rectangle}
     * @param fill The fill colour, if any, of the {@code Rectangle}
     * @param canvasSize The current size of the canvas
     */
    public Rectangle(double x1, double y1, double x2, double y2, Color edge, Color fill, int canvasSize)
    {
        super(edge,fill);
        this._x1 = convertToVector(x1, canvasSize);
        this._y1 = convertToVector(y1, canvasSize);
        this._x2 = convertToVector(x2, canvasSize);
        this._y2 = convertToVector(y2, canvasSize);
    }

    //CONSTRUCTOR used when loading data from a file

    /**
     * Constructs and initialises a Rectangle from a saved file with the specified values and colours.
     * @param values A string of two XY coordinates describing the start and opposite corners of a Rectangle
     * @param edgecolour The edge colour of the {@code Rectangle}
     * @param fillcolour The fill colour, if any, of the {@code Rectangle}
     */
    public Rectangle(String values, Color edgecolour, Color fillcolour)
    {
        super(edgecolour,fillcolour);
        String[] parts = values.split(" ");
        _x1 = Double.parseDouble(parts[0]);
        _y1 = Double.parseDouble(parts[1]);
        _x2 = Double.parseDouble(parts[2]);
        _y2 = Double.parseDouble(parts[3]);
    }


    //SETTERS AND GETTERS

    /**
     * Gets the vectorised start X location of the Rectangle on the canvas
     * @return a double precision number representing the vectorised X coordinate of the rectangles first corner
     */
    public double getStartX() {
        return _x1;
    }

    /**
     * Gets the vectorised start Y location of the Rectangle on the canvas
     * @return a double precision number representing the vectorised Y coordinate of the rectangles first corner
     */
    public double getStartY() {
        return _y1;
    }

    /**
     * Gets the vectorised end X location of the Rectangle on the canvas
     * @return a double precision number representing the vectorised X coordinate of the rectangles opposite corner
     */
    public double getEndX() {
        return _x2;
    }

    /**
     * Gets the vectorised end Y location of the Rectangle on the canvas
     * @return a double precision number representing the vectorised Y coordinate of the rectangles opposite corner
     */
    public double getEndY() {
        return _y2;
    }

    //METHODS

    /**
     * Constructs and initialises an {@code Rectangle2D.Double} based on the encapsulated {@code Rectangle} start and end values
     * @param x1 The X coordinate of the first corner of the rectangle in vector format
     * @param y1 The Y coordinate of the first corner of the rectangle in vector format
     * @param x2 The X coordinate of the opposite corner of the rectangle in vector format
     * @param y2 The Y coordinate of the opposite corner of the rectangle in vector format
     * @return a new {@code Ellipse2D.Double}
     */
    private Rectangle2D.Double createRectangle(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Draws the custom shapes edge and fill using the settings of the current {@code Graphics2D} object
     * @param g the current {@code Graphics2D} context
     * @param currentCanvasSize the canvas size is used to calculate shape size
     */
    @Override
    public void draw(Graphics2D g, int currentCanvasSize){
        int ccs = currentCanvasSize;
        Shape s = createRectangle(this.getStartX()*ccs, this.getStartY()*ccs,this.getEndX()*ccs, this.getEndY()*ccs);

        if (getFillColour() != null){
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
        String x1 = decimalFormat.format(_x1);
        String y1 = decimalFormat.format(_y1);
        String x2 = decimalFormat.format(_x2);
        String y2 = decimalFormat.format(_y2);

        return "RECTANGLE "+x1+" "+y1+" "+x2+" "+y2;
    }
}
