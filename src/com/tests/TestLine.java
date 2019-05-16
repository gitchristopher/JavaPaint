package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.shapes.Line;
import com.shapes.Plot;
import org.junit.jupiter.api.*;

import java.awt.*;

public class TestLine
{
    //Set up helpers
    Double x1;
    Double y1;
    Double x2;
    Double y2;
    Color colour = Color.BLACK;
    int canvasSize = 100;

    //Declare Object
    Line line;

    //Clear object before test
    @BeforeEach
    public void setUpLine() {
        line = null;
    }
    /*Test 1: Constructing a basic Line object
     *Construction with 6 parameters
     */
    @Test
    public void testLineConstruction(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
    }
}
