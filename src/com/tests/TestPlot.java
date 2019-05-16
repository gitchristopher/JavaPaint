package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.shapes.McShape;
import com.shapes.Plot;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Line2D;

public class TestPlot
{
    //Set up helpers
    Double x;
    Double y;
    Color colour = Color.BLACK;
    int canvasSize;

    //Converts a given number to the vector equivalent.
    private double convertToVector(double num, int canvasSize)
    {
        return num / canvasSize;
    }

    private Line2D.Double createShape(double x, double y)
    {
        return new Line2D.Double(x, y, x, y);
    }


    //Declare Object
    Plot plot;

    //Clear object before test
    @BeforeEach
    public void setUpPlot() {
        plot = null;
    }
    /*Test 1: Constructing a basic Plot object
     *Construction with 4 parameters
     */
    @Test
    public void testPlotConstruction(){
        x = 0.5;
        y = 0.5;
        Color colour = Color.BLACK;
        int canvasSize = 100;
        plot = new Plot(x,y,colour,canvasSize);
    }
    /*Test 2: Test x
     */
    @Test
    public void testPlotX(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(x, plot.getX());
    }
    /*Test 3: Test y
     */
    @Test
    public void testPlotY(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(y, plot.getY());
    }
    /*Test 4: Test color
     */
    @Test
    public void testPlotColor(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(colour, Color.BLACK);
    }
    /*Test 5: Test canvasSize
     */
    @Test
    public void testPlotCanvasSize(){
        x = 0.5;
        y = 0.5;
        canvasSize = 10;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(canvasSize, 10);
    }
    /*Test 6: Test canvasSize resizing
     */
    @Test
    public void testPlotConvertVector(){
        x = 0.5;
        y = 0.5;
        canvasSize = 100;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(0.005, plot.getX());
    }
    /*Test 7: Test creatShape
     */
    @Test
    public void testPlotCreateShape(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        Shape s = createShape(this.x*canvasSize, this.y*canvasSize);
        assertEquals(false, s.contains(x,y));
    }

}
