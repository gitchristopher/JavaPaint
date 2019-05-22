package com.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.dwg.McCanvas;
import com.shapes.*;
import com.shapes.Rectangle;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;


public class TestMcCanvas
{
    //Set up helpers
    int height;
    double x1;
    double y1;
    double x2;
    double y2;
    Color edgeColour = Color.BLACK;
    Color fillColour = Color.WHITE;
    int canvasSize = 1;

    //Declare Object
    McCanvas mcCanvas;

    //Clear object before test
    @BeforeEach
    public void setUpEllipse() {
        mcCanvas = null;
    }
    /*Test 1: Constructing a basic McCanvas object
     */
    @Test
    public void testMcCanvasConstruction(){
        mcCanvas = new McCanvas(500, 500);
    }
    /*Test 2: getCanvasSize
     */
    @Test
    public void testGetCanvasSize(){

        mcCanvas = new McCanvas(500, 500);
        this.mcCanvas.setSize(100,100);
        assertEquals(100, mcCanvas.getHeight());
    }
    /*Test 3: getMcShapesList
     */
    @Test
    public void testGetMcShapesList(){


    }
    /*Test 4: makePaintToolButton
     */
    @Test
    public void testMakePaintToolButton(){


    }
}
