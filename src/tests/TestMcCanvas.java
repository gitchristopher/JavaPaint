package tests;

import static org.junit.jupiter.api.Assertions.*;

import gui.McCanvas;
import shapes.*;
import org.junit.jupiter.api.*;
import java.awt.*;
import java.util.ArrayList;


public class TestMcCanvas
{
    //Set up helpers
    public int _height;

    Color edgeColour = Color.BLACK;
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
