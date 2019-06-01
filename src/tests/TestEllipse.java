package tests;

import static org.junit.jupiter.api.Assertions.*;

import shapes.Ellipse;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TestEllipse
{
    //Set up helpers
    double x1;
    double y1;
    double x2;
    double y2;
    Color edgeColour = Color.BLACK;
    Color fillColour = Color.WHITE;
    int canvasSize = 1;

    private Ellipse2D.Double createEllipse(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the Ellipse
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Ellipse2D.Double(x, y, width, height);
    }

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
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
    }
    /*Test 2: Test x1
     */
    @Test
    public void testEllipseX1(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(x1, ellipse.getStartX());
    }
    /*Test 3: Test y1
     */
    @Test
    public void testEllipseY1(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(y1, ellipse.getStartY());
    }
    /*Test 4: Test x2
     */
    @Test
    public void testEllipseX2(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(x2, ellipse.getEndX());
    }
    /*Test 5: Test y2
     */
    @Test
    public void testEllipseY2(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(y2, ellipse.getEndY());
    }
    /*Test 6: Test edgeColour
     */
    @Test
    public void testEllipseEdgeColour(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(edgeColour, ellipse.getEdgeColour());
    }
    /*Test 7: Test fillColour
     */
    @Test
    public void testEllipseFillColour(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(fillColour, ellipse.getFillColour());
    }
    /*Test 8: Test canvasSize
     */
    @Test
    public void testEllipseCanvasSize(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 10;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(10,canvasSize);
    }
    /*Test 9: Test canvasSize resizing
     */
    @Test
    public void testEllipseConvertVector(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 100;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals(0.005, ellipse.getStartX());
    }
    /*Test 10: Test createEllipse
     */
    @Test
    public void testEllipseCreateEllipse(){
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        Shape s = createEllipse(this.x1*canvasSize, this.y1*canvasSize,this.x2*canvasSize,this.y2*canvasSize);
        assertEquals(createEllipse(0.5,0.5,0.7,0.7), s);
    }
    /*Test 11: Test stringParts
     *Test for correct string format
     */
    @Test
    public void testEllipseStringParts() {
        x1 = 0.5;
        y1 = 0.5;
        x2 = 0.7;
        y2 = 0.7;
        Color edgeColour = Color.BLACK;
        Color fillColour = Color.WHITE;
        int canvasSize = 1;
        ellipse = new Ellipse(x1,y1,x2,y2,edgeColour,fillColour,canvasSize);
        assertEquals("ELLIPSE 0.5 0.5 0.7 0.7", ellipse.commandExport());
    }
}
