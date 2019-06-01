package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;

/**
 * The {@code Ellipse} extends the abstract shape class {@code McShape} to give more customisation and control over the object.
 * The class encapsulates the idea of an ellipse object which is bounded by a framing rectangle in double precision.
 *
 * The class uses {@code Ellipse2D.Double} to paint the object to the screen
 */
public class Ellipse extends McShape
{
    //VARIABLES
    private double _x1;
    private double _y1;
    private double _x2;
    private double _y2;


    //CONSTRUCTOR

    /**
     * Constructs and initialises an Ellipse with the specified coordinates, colours, and screen dimensions.
     * @param x1 The X coordinate of the first corner of the framing rectangle
     * @param y1 The Y coordinate of the first corner of the framing rectangle
     * @param x2 The X coordinate of the opposite corner of the framing rectangle
     * @param y2 The Y coordinate of the opposite corner of the framing rectangle
     * @param edge The edge colour of the {@code Ellipse}
     * @param fill The fill colour, if any, of the {@code Ellipse}
     * @param canvasSize The current size of the canvas
     */
    public Ellipse(double x1, double y1, double x2, double y2, Color edge, Color fill, int canvasSize){
        super(edge, fill);
        this._x1 = convertToVector(x1, canvasSize);
        this._y1 = convertToVector(y1, canvasSize);
        this._x2 = convertToVector(x2, canvasSize);
        this._y2 = convertToVector(y2, canvasSize);
    }
    //CONSTRUCTOR used when loading data from a file

    /**
     * Constructs and initialises an Ellipse from a saved file with the specified values and colours.
     * @param values A string of two XY coordinates describing the framing rectangle
     * @param edgecolour The edge colour of the {@code Ellipse}
     * @param fillcolour The fill colour, if any, of the {@code Ellipse}
     */
    public Ellipse(String values, Color edgecolour, Color fillcolour)
    {
        super(edgecolour, fillcolour);
        setEdgeColour(edgecolour);
        setFillColour(fillcolour);
        String[] parts = values.split(" ");
        _x1 = Double.parseDouble(parts[0]);
        _y1 = Double.parseDouble(parts[1]);
        _x2 = Double.parseDouble(parts[2]);
        _y2 = Double.parseDouble(parts[3]);
    }

    //SETTERS AND GETTERS

    /**
     * Gets the X position of the first corner of the framing rectangle
     * @return X position of the framing rectangle first corner in vector format (percentage of screen width)
     */
    public double getStartX() {
        return _x1;
    }

    /**
     * Gets the Y position of the first corner of the framing rectangle
     * @return Y position of the framing rectangle first corner in vector format (percentage of screen width)
     */
    public double getStartY() {
        return _y1;
    }

    /**
     * Gets the X position of the second corner of the framing rectangle
     * @return X position of the framing rectangle second corner in vector format (percentage of screen width)
     */
    public double getEndX() {
        return _x2;
    }

    /**
     * Gets the Y position of the second corner of the framing rectangle
     * @return Y position of the framing rectangle second corner in vector format (percentage of screen width)
     */
    public double getEndY() {
        return _y2;
    }

    //METHODS

    /**
     * Constructs and initialises an {@code Ellipse2D.Double} based on the encapsulated {@code Ellipse} framing rectangle values
     * @param x1 The X coordinate of the first corner of the framing rectangle in vector format
     * @param y1 The Y coordinate of the first corner of the framing rectangle in vector format
     * @param x2 The X coordinate of the opposite corner of the framing rectangle in vector format
     * @param y2 The Y coordinate of the opposite corner of the framing rectangle in vector format
     * @return a new {@code Ellipse2D.Double}
     */
    private Ellipse2D.Double createEllipse(double x1, double y1, double x2, double y2)
    {
        // Gets the top left hand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        // Determines the width and height of the Ellipse
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Ellipse2D.Double(x, y, width, height);
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
        Shape s = createEllipse(this._x1*ccs, this._y1*ccs,this._x2*ccs, this._y2*ccs);

        if (this.getFillColour() != null){
            g.setPaint(this.getFillColour());
            g.fill(s);
        }

        g.setPaint(this.getEdgeColour());
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

        return "ELLIPSE "+x1+" "+y1+" "+x2+" "+y2;
    }
}
