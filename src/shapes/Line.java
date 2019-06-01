package shapes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

/**
 * The {@code Line} extends the abstract shape class {@code McShape} to give more customisation and control over the object.
 * The class encapsulates the idea of a Line object which has a start and end point.
 *
 * The class uses {@code Line2D.Double} to paint the object to the screen
 */
public class Line extends McShape
{
    //VARIABLES
    private Plot _startPoint;
    private Plot _endPoint;

    //CONSTRUCTOR

    /**
     * Constructs and initialises a Line with the specified coordinates, edge colour, and screen dimensions.
     * @param x1 The X coordinate of the first point of the Line
     * @param y1 The Y coordinate of the first point of the Line
     * @param x2 The X coordinate of the second point of the Line
     * @param y2 The Y coordinate of the second point of the Line
     * @param colour The edge colour of the {@code Line}
     * @param canvasSize The current size of the canvas
     */
    public Line(double x1, double y1, double x2, double y2, Color colour, int canvasSize)
    {
        super(colour);
        this._startPoint = new Plot(x1, y1, colour, canvasSize);
        this._endPoint = new Plot(x2, y2, colour, canvasSize);
    }

    //CONSTRUCTOR used when loading data from a file

    //TODO: Remove fill colour from a LINE and the PLOT classes(load file ramifications?)
    /**
     * Constructs and initialises a Line from a saved file with the specified values and colour.
     * @param values A string of two XY coordinates describing the beginning and end of the Line
     * @param edgecolour The edge colour of the {@code Line}
     * @param fillcolour The fill colour of the {@code Line}
     */
    public Line(String values, Color edgecolour, Color fillcolour)
    {
        super(edgecolour);
        String[] parts = values.split(" ");
        String sp = parts[0] + " " + parts[1];
        String ep = parts[2] + " " + parts[3];
        this._startPoint = new Plot(sp, edgecolour, fillcolour);
        this._endPoint = new Plot(ep, edgecolour, fillcolour);
    }

    //SETTERS AND GETTERS

    /**
     * Gets the start location of the Line
     * @return a {@code Plot} object representing the XY start location of the Line
     */
    public Plot getStartPlot() {
        return _startPoint;
    }

    /**
     * Gets the end location of the Line
     * @return a {@code Plot} object representing the XY end location of the Line
     */
    public Plot getEndPlot() {
        return _endPoint;
    }


    //METHODS

    /**
     * Constructs and initialises an {@code Line2D.Double} based on the encapsulated {@code Line} start and end values
     * @param x1 The X coordinate of the start point of the Line in vector format
     * @param y1 The Y coordinate of the start point of the Line in vector format
     * @param x2 The X coordinate of the end point of the Line in vector format
     * @param y2 The Y coordinate of the end point of the Line in vector format
     * @return a new {@code Line2D.Double}
     */
    private Line2D.Double createShape(double x1, double y1, double x2, double y2){

        return new Line2D.Double(x1, y1, x2, y2);
    }

    /**
     * Draws the custom shapes edge using the settings of the current {@code Graphics2D} object
     * @param g the current {@code Graphics2D} context
     * @param currentCanvasSize the canvas size is used to calculate shape size
     */
    @Override
    public void draw(Graphics2D g, int currentCanvasSize) {
        int ccs = currentCanvasSize;
        Shape s = createShape(this._startPoint.getX()*ccs, this._startPoint.getY()*ccs,this._endPoint.getX()*ccs, this._endPoint.getY()*ccs);
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
        String x1 = decimalFormat.format(getStartPlot().getX());
        String y1 = decimalFormat.format(getStartPlot().getY());
        String x2 = decimalFormat.format(getEndPlot().getX());
        String y2 = decimalFormat.format(getEndPlot().getY());

        return "LINE "+x1+" "+y1+" "+x2+" "+y2;
    }
}
