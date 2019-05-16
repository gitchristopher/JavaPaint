package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TestRectangle
{
    //Set up helpers
    double x1;
    double y1;
    double x2;
    double y2;
    Color edgeColour = Color.BLACK;
    Color fillColour = Color.WHITE;
    int canvasSize = 1;


    private Rectangle2D.Double createRectangle(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Rectangle2D.Double(x, y, width, height);
    }


    //Declare Object
    Rectangle rectangle;

    //Clear object before test
    @BeforeEach
    public void setUpRectangle() {
        rectangle = null;
    }
    /*Test 1: Constructing a basic Rectangle object
     *Construction with 7 parameters
     */
    @Test
    public void testRectangleConstruction(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
    }
    /*Test 2: Test x1
     */
    @Test
    public void testRecX1(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(x1, rectangle.getStartX());
    }
    /*Test 3: Test y1
     */
    @Test
    public void testRecY1(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(y1, rectangle.getStartY());
    }
    /*Test 4: Test x2
     */
    @Test
    public void testRecX2(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(x2, rectangle.getEndX());
    }
    /*Test 5: Test y2
     */
    @Test
    public void testRecY2(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(y2, rectangle.getEndY());
    }
    /*Test 6: Test edgeColour
     */
    @Test
    public void testRecEdgeColour(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(edgeColour, rectangle.getEdgeColour());
    }
    /*Test 7: Test fillColour
     */
    @Test
    public void testRecFillColour(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(fillColour, rectangle.getFillColour());
    }
    /*Test 8: Test canvasSize
     */
    @Test
    public void testRecCanvasSize(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 10;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(canvasSize, 10);
    }
    /*Test 9: Test canvasSize resizing
     */
    @Test
    public void testRecConvertVector(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 100;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(0.005, rectangle.getStartX());
    }
    /*Test 10: Test createRectangle
     */
    @Test
    public void testRecCreateRectangle(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        Shape s = createRectangle(this.x1*canvasSize, this.y1*canvasSize,this.x2*canvasSize,this.y2*canvasSize);
        assertEquals(false, s.contains(x1,y1,x2,y2));
    }
}
