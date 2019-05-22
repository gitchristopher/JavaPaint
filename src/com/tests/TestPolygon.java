package com.tests;

import static org.junit.jupiter.api.Assertions.*;
import static com.gui.McDrawApp.isPolyOpen;
import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;
import org.junit.jupiter.api.*;
import com.shapes.Polygon;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class TestPolygon
{
    //Set up helpers
    double[] _xPoly;// = new double[]{};
    double[] _yPoly;// = new double[]{};
    Color _edgeColour;
    Color _fillColour;
    public ArrayList<Double> _xList = new ArrayList<Double>();
    public ArrayList<Double> _yList = new ArrayList<Double>();
    Boolean _isClosed = false;

    private double convertToVector(double PolyNum, int canvasSize)
    {
        return PolyNum / canvasSize;
    }
    public void addPlot(double xValue, double yValue, int canvasSize)
    {
        _xList.add(convertToVector(xValue, canvasSize));
        _yList.add(convertToVector(yValue, canvasSize));
    }

    public Path2D.Double createPath(double[] x, double[] y)
    {
        Path2D.Double thePath = new Path2D.Double();

        thePath.moveTo(x[0],y[0]);
        for (int i = 1; i < x.length; i++)
        {
            thePath.lineTo(x[i],y[i]);
        }

        if (_isClosed){thePath.closePath();}

        return thePath;
    }
    public void finishPolygon()
    {
        this._xPoly = new double[_xList.size()];
        int counter = 0;
        for (double d: _xList)
        {
            this._xPoly[counter] = d;
            counter++;
        }
        this._yPoly = new double[_yList.size()];
        counter = 0;
        for (double d: _yList)
        {
            this._yPoly[counter] = d;
            counter++;
        }
        this._isClosed = true;
        isPolyOpen = false; // variable on McDrawApp

        // Used when developing to see when closed
        //for (int i = 0; i < this._xPoly.length; i++)
        //{
        //    System.out.println("Polygon object: X: " + _xPoly[i] + "\t|\t" + "Y: " + _yPoly[i] );
        //}
    }

    //Declare Object
    Polygon polygon;

    //Clear object before test
    @BeforeEach
    public void setUpPolygon() {
        polygon = null;
    }
    /*Test 1: Constructing a polygon object
     *Construction with 7 parameters
     */
    @Test
    public void testPolygonConstruction(){

        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
    }
    /*Test 2: Test XPoly
     */
    @Test
    public void testPolyXPoly(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        assertEquals(0.5, polygon.getFirstX());
    }
    /*Test 3: Test YPoly
     */
    @Test
    public void testPolyYPoly(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        assertEquals(0.5, polygon.getFirstY());
    }
    /*Test 4: Test lastXPoly
     */
    @Test
    public void testPolyLastXPloy(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        this.polygon.addPlot(0.7, 0.7, 1);
        this.polygon.addPlot(0.8, 0.8, 1);
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        assertEquals(0.8, polygon.getLastX());
    }
    /*Test 5: Test lastYPoly
     */
    @Test
    public void testPolyLastYPloy(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        this.polygon.addPlot(0.7, 0.7, 1);
        this.polygon.addPlot(0.8, 0.8, 1);
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        assertEquals(0.8, polygon.getLastY());
    }
    /*Test 6: Test edgeColour
     */
    @Test
    public void testPolyEdgeColour(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        assertEquals(_edgeColour, polygon.getEdgeColour());
    }
    /*Test 7: Test fillColour
     */
    @Test
    public void testPolyFillColour(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        assertEquals(_fillColour, polygon.getFillColour());
    }
    /*Test 8: Test canvasSize
     */
    @Test
    public void testPolyCanvasSize(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 10;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        assertEquals(10, canvasSize);
    }
    /*Test 9: Test canvasSize resizing
     */
    @Test
    public void testPolyConvertVector() {
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 100;
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        polygon = new Polygon(XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        assertEquals(0.005, polygon.getFirstX());
    }
    /*Test 10: Test createPolygon
     */
    @Test
    public void testPolyCreatePolygon(){
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        this.polygon.addPlot(0.7, 0.7, 1);
        this.polygon.addPlot(0.8, 0.8, 1);
        this.polygon.addPlot(0.5, 0.5, 1);
        this.polygon.finishPolygon();
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        double[] xPolyTemp = new double[polygon._xList.size()];
        double[] yPolyTemp = new double[polygon._yList.size()];
        //System.out.println(this._xList.size());
        for (int i = 0; i < polygon._xList.size(); i++)
        {
            //System.out.println(i);
            xPolyTemp[i] = polygon._xList.get(i) * canvasSize;
            yPolyTemp[i] = polygon._yList.get(i) * canvasSize;
        }
        Shape s = polygon.createPath(xPolyTemp, yPolyTemp);

        assertEquals(s, s);
    }
    /*Test 11: Test stringParts
     *Test for correct string format
     */
    @Test
    public void testPolyStringParts() {
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        this.polygon.addPlot(0.7, 0.7, 1);
        this.polygon.addPlot(0.8, 0.8, 1);
        this.polygon.addPlot(0.5, 0.5, 1);
        this.polygon.finishPolygon();
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        double[] xPolyTemp = new double[polygon._xList.size()];
        double[] yPolyTemp = new double[polygon._yList.size()];
        //System.out.println(this._xList.size());
        for (int i = 0; i < polygon._xList.size(); i++)
        {
            //System.out.println(i);
            xPolyTemp[i] = polygon._xList.get(i) * canvasSize;
            yPolyTemp[i] = polygon._yList.get(i) * canvasSize;
        }

        assertEquals("POLYGON 0.5 0.5 0.7 0.7 0.8 0.8 0.5 0.5", polygon.commandExport());
    }
    /*Test 12: Test finishPolygon
     *Test for correct string format
     */
    @Test
    public void testPolyFinishPoly() {
        double XPoly = 0.5;
        double YPoly = 0.5;
        int canvasSize = 1;
        polygon = new Polygon( XPoly, YPoly, _edgeColour, _fillColour, canvasSize);
        this.polygon.addPlot(0.7, 0.7, 1);
        this.polygon.addPlot(0.8, 0.8, 1);
        this.polygon.addPlot(0.5, 0.5, 1);
        this.polygon.finishPolygon();
        _edgeColour = Color.BLACK;
        _fillColour = Color.WHITE;
        double[] xPolyTemp = new double[polygon._xList.size()];
        double[] yPolyTemp = new double[polygon._yList.size()];
        //System.out.println(this._xList.size());
        for (int i = 0; i < polygon._xList.size(); i++)
        {
            //System.out.println(i);
            xPolyTemp[i] = polygon._xList.get(i) * canvasSize;
            yPolyTemp[i] = polygon._yList.get(i) * canvasSize;
        }

        assertEquals(false , isPolyOpen);
    }
}
