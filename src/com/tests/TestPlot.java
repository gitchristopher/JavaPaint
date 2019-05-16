package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.shapes.Plot;
import org.junit.jupiter.api.*;

import java.awt.*;

public class TestPlot
{
    //Set up helpers
    Double x;
    Double y;
    Color colour = Color.BLACK;
    int canvasSize = 100;

    //Declare Object
    Plot plot;

    //Clear object before test
    @BeforeEach
    public void setUpPlot() {
        plot = null;
    }
    /*Test 1: Constructing a basic Plot object
     *Construction with 3 parameters
     */
    @Test
    public void testPlotConstruction(){
        x = 0.5;
        y = 0.5;
        plot = new Plot(x,y,colour,canvasSize);
    }
}
