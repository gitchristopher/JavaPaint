package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.shapes.Ellipse;
import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;
import org.junit.jupiter.api.*;

import java.awt.*;

public class TestEllipse
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
    Ellipse ellipse;

    //Clear object before test
    @BeforeEach
    public void setUpEllipse() {
        ellipse = null;
    }
    /*Test 1: Constructing a basic Ellipse object
     *Construction with 7 parameters
     */
    @Test
    public void testEllipseConstruction(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
    }
}
