package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;
import org.junit.jupiter.api.*;

import java.awt.*;

public class TestRectangle
{
    //Set up helpers
    Double x1;
    Double y1;
    Double x2;
    Double y2;
    Color edgeColour = Color.BLACK;
    Color fillColour = Color.WHITE;
    int canvasSize = 100;

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
        rectangle = new Rectangle(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
    }
}
