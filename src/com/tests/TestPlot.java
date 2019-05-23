package com.tests;

import static java.sql.Types.NULL;
import static org.junit.jupiter.api.Assertions.*;

import com.shapes.McShape;
import com.shapes.Plot;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Line2D;

public class TestPlot
{
    //Set up helpers
    double x;
    double y;
    Color colour = Color.BLACK;
    int canvasSize;

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
        x = 0;
        y = 0;
    }
    /*Test 1: Constructing a basic Plot object
     *Construction with 4 parameters
     */
    @Test
    public void testPlotConstruction(){
        x = 0.5;
        y = 0.5;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
    }
    /*Test 2: Test x
     */
    @Test
    public void testPlotX(){
        x = 0.5;
        y = 0.5;
        int canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(x, plot.getX());
    }
    /*Test 3: Test y
     */
    @Test
    public void testPlotY(){
        x = 0.5;
        y = 0.5;
        int canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(y, plot.getY());
    }
    /*Test 4: Test color
     */
    @Test
    public void testPlotColor(){
        x = 0.5;
        y = 0.5;
        int canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(colour, Color.BLACK);
    }
    /*Test 5: Test canvasSize
     */
    @Test
    public void testPlotCanvasSize(){
        x = 0.5;
        y = 0.5;
        int canvasSize = 10;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(10 , canvasSize);
    }
    /*Test 6: Test canvasSize resizing
     */
    @Test
    public void testPlotConvertVector(){
        x = 0.5;
        y = 0.5;
        int canvasSize = 100;
        plot = new Plot(x,y,colour,canvasSize);
        assertEquals(0.005, plot.getX());
    }
    /*Test 7: Test createShape
     */
    @Test
    public void testPlotCreateShape(){
        x = 0.5;
        y = 0.5;
        int canvasSize = 1;
        plot = new Plot(x,y,colour,canvasSize);
        Shape s = createShape(this.x*canvasSize, this.y*canvasSize);
        assertNotNull(s);
    }
    /*Test 8: Test stringParts
     *Test for correct string format
     */
    @Test
    public void testPlotStringParts(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot("0.5 0.5", Color.BLACK,Color.WHITE);
        assertEquals("PLOT 0.5 0.5", plot.commandExport());
    }
    /*Test 9: Test edge colors
     */
    @Test
    public void testPlotColors(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot("0.5 0.5", Color.BLACK,Color.WHITE);
        assertEquals(Color.BLACK, plot.getEdgeColour());
    }
    /*Test 10: Test fill colors
     *Fill colour will always be null on plot
     */
    @Test
    public void testPlotFillColors(){
        x = 0.5;
        y = 0.5;
        canvasSize = 1;
        plot = new Plot("0.5 0.5", Color.BLACK,Color.WHITE);
        assertEquals(null, plot.getFillColour());
    }

}
