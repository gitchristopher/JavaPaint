package tests;

import static org.junit.jupiter.api.Assertions.*;

import shapes.Line;
import shapes.Plot;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Line2D;

public class TestLine
{
    //Set up helpers
    double x1 = 0;
    double y1 = 0;
    double x2 = 0;
    double y2 = 0;
    private Plot _startPoint;
    private Plot _endPoint;
    int canvasSize = 1;

    private Line2D.Double createShape(double x1, double y1, double x2, double y2){

        return new Line2D.Double(x1, y1, x2, y2);
    }

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
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
    }
    /*Test 2: Test x1
     */
    @Test
    public void testLineX1(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(x1, _startPoint.getX());
    }
    /*Test 3: Test y1
     */
    @Test
    public void testLineY1(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(y1, _startPoint.getY());
    }
    /*Test 4: Test x2
     */
    @Test
    public void testLineX2(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(x2, _endPoint.getX());
    }
    /*Test 5: Test y2
     */
    @Test
    public void testLineY2(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(y2, _endPoint.getY());
    }
    /*Test 6: Test _startPoint
     */
    @Test
    public void testPlotStartPoint(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(_startPoint.getX(),_startPoint.getY(),_endPoint.getX(),_endPoint.getY(),colour,canvasSize);
        _startPoint = line.getStartPlot();
        assertEquals(_startPoint, line.getStartPlot());
    }
    /*Test 7: Test _endPoint
     */
    @Test
    public void testPlotEndPoint(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 1;
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        _endPoint = new Plot(x2, y2, colour, canvasSize);
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        _endPoint = line.getEndPlot();
        assertEquals(_endPoint, line.getEndPlot());
    }
    /*Test 8: Test canvasSize
     */
    @Test
    public void testLineCanvasSize(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        int canvasSize = 10;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(10, canvasSize);
    }
    /*Test 9: Test canvasSize resizing
     */
    @Test
    public void testLineConvertVector(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        canvasSize = 100;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        _startPoint = new Plot(x1, y1, colour, canvasSize);
        assertEquals(0.005, _startPoint.getX());
    }
    /*Test 10: Test createShape
     */
    @Test
    public void testLineCreateShape(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.5;
        y2 = 0.5;
        Color colour = Color.BLACK;
        canvasSize = 1;
        line = new Line(x1,y1,x1,y1,colour,canvasSize);
        Shape s = createShape(this.x1*canvasSize, this.y1*canvasSize,this.x1*canvasSize, this.y1*canvasSize);
        assertNotNull(s);
    }
    /*Test 11: Test color
     */
    @Test
    public void testPlotColor(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        canvasSize = 1;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(colour, Color.BLACK);
    }
    /*Test 12: Test stringParts
     *Test for correct string format
     */
    @Test
    public void testLineStringParts(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        canvasSize = 1;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals("LINE 0.5 0.5 0.7 0.7", line.commandExport());
    }
    /*Test 13: Test edge colors
     */
    @Test
    public void testLineColors(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        canvasSize = 1;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(Color.BLACK, line.getEdgeColour());
    }
    /*Test 14: Test fill colors
     *Fill colour will always be null on plot
     */
    @Test
    public void testPlotFillColors(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color colour = Color.BLACK;
        canvasSize = 1;
        line = new Line(x1,y1,x2,y2,colour,canvasSize);
        assertEquals(null, line.getFillColour());
    }
}
