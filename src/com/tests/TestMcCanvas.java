package com.tests;

import static com.gui.McDrawApp.currentlyDrawing;
import static com.gui.McDrawApp.edgeColour;
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
    public int _width, _height;
    double x1;
    double y1;
    double x2;
    double y2;
    Color edgeColour = Color.BLACK;
    Color fillColour = Color.WHITE;
    int canvasSize = 1;
    public ArrayList<McShape> listOfMcShapes = new ArrayList<McShape>();

    private int getCanvasSize(){
        return this._height;
    }


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
        mcCanvas = new McCanvas(500, 500);
        this.mcCanvas.setSize(100,100);
        McShape myMcPoint = new Plot(0.5, 0.5, edgeColour, getCanvasSize());
        this.mcCanvas.listOfMcShapes.add(myMcPoint);
        this.listOfMcShapes.add(myMcPoint);
        assertEquals(this.listOfMcShapes, mcCanvas.getMcShapesList());
    }
    /*Test 4: setMcShapesList
     */
    @Test
    public void testSetMcShapesList(){
        mcCanvas = new McCanvas(500, 500);
        this.mcCanvas.setSize(100,100);
        McShape myMcPoint = new Plot(0.5, 0.5, edgeColour, getCanvasSize());
        this.mcCanvas.listOfMcShapes.add(myMcPoint);
        this.mcCanvas.setMcShapesList(mcCanvas.listOfMcShapes);
        assertEquals(this.mcCanvas.listOfMcShapes, mcCanvas.getMcShapesList());
    }
    /*Test 5: removeLastShape
     */
    @Test
    public void testRemoveLastShape(){
        mcCanvas = new McCanvas(500, 500);
        this.mcCanvas.setSize(100,100);
        McShape myMcPoint = new Plot(0.5, 0.5, edgeColour, getCanvasSize());
        this.mcCanvas.listOfMcShapes.add(myMcPoint);
        this.listOfMcShapes.add(myMcPoint);
        this.mcCanvas.removeLastShape();
        listOfMcShapes.clear();
        assertEquals( listOfMcShapes, mcCanvas.listOfMcShapes);
    }
}
