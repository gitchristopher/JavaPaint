package shapes;

import java.awt.*;

/**
 * A custom abstract shape class
 */
public abstract class McShape {
    private Color _edgeColour;
    private Color _fillColour;

    public McShape(){}

    public McShape(Color edgeColour)
    {
        setEdgeColour(edgeColour);
    }

    public McShape(Color edgeColour, Color fillColour)
    {
        setEdgeColour(edgeColour);
        setFillColour(fillColour);
    }

    /**
     * Gets the shapes edge colour
     * @return a Color object for the given shapes edge, {@code Color.BLACK} if null
     */
    public Color getEdgeColour()
    {
        if (this._edgeColour != null)
            return this._edgeColour;
        else
            return Color.BLACK;
    }

    /**
     * Gets the shapes fill colour
     * @return a Color object for the given shapes fill, null if fill not set
     */
    public Color getFillColour()
    {
        if (this._fillColour != null)
            return this._fillColour;
        else
            return null;
    }

    /**
     * Sets the shapes edge colour
     */
    void setEdgeColour(Color edgeColour)
    {
        this._edgeColour = edgeColour;
    }

    /**
     * Gets the shapes fill colour
     */
    void setFillColour(Color fillColour)
    {
        this._fillColour = fillColour;
    }
    /**
     * Converts the given number (screen coordinate) to a decimal percentage based on the current canvas size
     * @param num a coordinate on the canvas
     * @param canvasSize the current size of the canvas
     * @return a vectorised coordinate
     */
    double convertToVector(double num, int canvasSize)
    {
        return num / canvasSize;
    }

    /**
     * Draws the given shape to the screen
     * @param g the current {@code Graphics2D} context
     * @param currentCanvasSize the canvas size is used to calculate shape size
     */
    public abstract void draw(Graphics2D g, int currentCanvasSize);

    /**
     * Shapes are saved as command & variable strings
     * @return a string representing the data for a given shape
     */
    public abstract String commandExport();
    //TODO: change printed statements to have [] like the assignment sheet????
}
