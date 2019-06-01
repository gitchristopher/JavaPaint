package tests;

import static org.junit.jupiter.api.Assertions.*;
import static gui.McDrawApp.isPolyOpen;

import org.junit.jupiter.api.*;
import shapes.Polygon;

import java.awt.*;
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

        assertNotNull(s);
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
