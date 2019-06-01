package gui;

import shapes.McShape;
import shapes.Line;
import shapes.Plot;
import shapes.Rectangle;
import shapes.Ellipse;
import shapes.Polygon;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

import static gui.McDrawApp.*;

/**
 * The McCanvas component is a blank square area of the screen onto which the user can draw shapes.
 */
public class McCanvas extends JComponent
{
    // The initial height and width of the canvas
    private final int _width, _height;

    // The list holds all shapes drawn by the user
    public ArrayList<McShape> listOfMcShapes = new ArrayList<McShape>();

    // A temporary list to hold a polygon while it is being created
    private ArrayList<McShape> tempPolyList = new ArrayList<McShape>();

    // Utility variables used when creating shapes to store mouse click locations
    private java.awt.Point drawStart, drawEnd;

    /**
     * Creates the canvas which users draw on
     * @param width the desired width of the canvas
     * @param height the desired height of the canvas
     */
    public McCanvas(int width, int height)
    {
        this._width = width;
        this._height = height;
        this.setSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));

        // Listens for mouse activity
        this.addMouseListener(new MouseAdapter() {
            Shape myNewShape = null;

            /**
             * Lets the app know the user is drawing and stores the X Y location of the mouse event
             * @param e a mouse press event
             */
            @Override
            public void mousePressed(MouseEvent e) {
                currentlyDrawing = true;
                drawStart = new Point(e.getX(),e.getY());
            }

            /**
             * When the mouse is released the drawing is complete except for Polygons.
             * For a normal shape a new object is created and added to the list of shapes.
             * For a Polygon the system is told that the user is still drawing if there is a currently active Polygon.
             * After a polygon has been created any mouse release will either add to the existing polygon or finalise the shape.
             * @param e a mouse button release event
             */
            @Override
            public void mouseReleased(MouseEvent e) {

                // Checks to see if the user is drawing a polygon
                if (currentPaintingAction == 5 && isPolyOpen)
                {
                    currentlyDrawing = true;
                }else
                    {
                        // if the temp list has anything in it it means a poly is being drawn.
                        // since the action must not be DrawPolygon(5) the polygon is finalised
                        if (tempPolyList.size()>0)
                        {
                            Polygon poly = (Polygon)tempPolyList.get(0);
                            poly.finishPolygon();
                            listOfMcShapes.add((McShape)poly);
                            tempPolyList.clear();
                        }
                        currentlyDrawing = false;
                    }

                // Draw a new shaped based on the current painting action
                switch(currentPaintingAction)
                {
                    case 1:
                        McShape myMcPoint = new Plot(drawStart.getX(), drawStart.getY(), edgeColour, getCanvasSize());
                        listOfMcShapes.add(myMcPoint);
                        break;
                    case 2:
                        drawEnd = new Point(e.getX(),e.getY());
                        McShape myMcLine = new Line(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, getCanvasSize());
                        listOfMcShapes.add(myMcLine);
                        break;
                    case 3:
                        drawEnd = new Point(e.getX(),e.getY());
                        McShape myMcRectangle = new Rectangle(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, fillColour, getCanvasSize());
                        listOfMcShapes.add(myMcRectangle);
                        break;
                    case 4:
                        drawEnd = new Point(e.getX(),e.getY());
                        McShape myMcEllipse = new Ellipse(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, fillColour, getCanvasSize());
                        listOfMcShapes.add(myMcEllipse);
                        break;
                    case 5:

                        // Get the temp polygon added to the temp list, this is the current polygon being made as it hasnt be closed yet
                        // Make sure its not the first two point as you cant close a zero line polygon
                        // Check to see is the release point is near the beginning of the polygon and if it is, close the polygon
                        // If its not, add another point to the polygon
                        drawEnd = new Point(e.getX(),e.getY());

                        if (isPolyOpen)
                        {
                            Polygon myPoly = (Polygon)tempPolyList.get(tempPolyList.size()-1);
                            if (myPoly._xList.size()>2)
                            {
                                double x = Math.abs(drawEnd.getX()/getCanvasSize()-myPoly.getFirstX());
                                double y = Math.abs(drawEnd.getY()/getCanvasSize()-myPoly.getFirstY());
                                if (x < .025 && y < .025)
                                {
                                    myPoly.finishPolygon();
                                    listOfMcShapes.add(tempPolyList.get(0));
                                    tempPolyList.clear();
                                    currentlyDrawing = false; // allows for undo
                                }else{
                                    myPoly.addPlot(drawEnd.getX(), drawEnd.getY(), getCanvasSize());
                                }
                            }else
                            {
                                myPoly.addPlot(drawEnd.getX(), drawEnd.getY(), getCanvasSize());
                            }
                            if (tempPolyList.size()>0)
                            {
                                tempPolyList.set(tempPolyList.size()-1, (McShape)myPoly);
                            }
                        }
                        break;
                    default:
                }

                drawStart = null;
                drawEnd = null;
                repaint();
            }
        });

        // Used in the creation of polygons
        this.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                // If the user is currently drawing, has clicked while using draw polygon but not released yet
                // then a new polygon is created.
                // If a polygon is currently being drawn then the current mouse position is set to the end point
                // and the canvas is repainted allowing for the shadow line
                if (currentlyDrawing){
                    if (currentPaintingAction == 5)
                    {
                        // If the polygon isnt open than a new one has to be created.
                        // Close occurred when the previous polygon was finalised.
                        // The new polygon is instantiated and added to the shapes list.
                        // Additional points are added to the polygon on mouse release.
                        if (!isPolyOpen)
                        {
                            isPolyOpen = true;
                            McShape myMcPoly = new Polygon(drawStart.getX(), drawStart.getY(), edgeColour, fillColour, getCanvasSize());
                            tempPolyList.add(myMcPoly);
                        }
                    }
                }
                if (currentlyDrawing){
                    drawEnd = new Point(e.getX(), e.getY());
                    repaint();
                }
            }
        });

        // Allows for undo via CTRL + Z
        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK,false),"Undo");
        ActionMap am = this.getActionMap();
        am.put("Undo", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                removeLastShape();
            }
        });
    }

    //METHODS

    /**
     * This method paints all the shapes to the canvas
     * @param g
     */
    public void paint(Graphics g) {
        //Graphics2D graphicSettings;
        Graphics2D graphicSettings = (Graphics2D)g;
        graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphicSettings.setStroke(new BasicStroke(1));
        graphicSettings.setColor(edgeColour);
        graphicSettings.setColor(Color.white);
        int size = Math.min(getWidth(), getHeight());
        graphicSettings.fillRect(0, 0, size, size );

        for (McShape s : listOfMcShapes){
            s.draw(graphicSettings, this.getHeight());
        }
        for (McShape s : tempPolyList){
            s.draw(graphicSettings, this.getHeight());
        }

        //This draws the temporary shape so you can see where it will be
        if (drawStart != null && drawEnd != null){
            graphicSettings.setPaint(Color.lightGray);
            graphicSettings.setStroke(new BasicStroke(5));
            Shape myTempShape = null;

            switch (currentPaintingAction)
            {
                case 1:
                    myTempShape = drawPoint(drawStart.getX(), drawStart.getY());
                    break;
                case 2:
                    myTempShape = drawLine(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
                    break;
                case 3:
                    myTempShape = drawRectangle(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
                    break;
                case 4:
                    myTempShape = drawEllipse(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
                    break;
                case 5:
                    // The shape is cast from the list of shapes as a polygon to give access to its methods
                    // The Polygon object draws itself, a temp line is drawn to show where the new line will be
                    Polygon tempPoly;
                    Shape currentLine;
                    if (isPolyOpen)
                    {
                        tempPoly = (Polygon)tempPolyList.get(tempPolyList.size()-1);
                        if (tempPoly._xList.size() > 1)
                        {
                            currentLine = drawLine(tempPoly.getLastX()*getCanvasSize(), tempPoly.getLastY()*getCanvasSize(), drawEnd.getX(), drawEnd.getY());
                        }else
                        {
                            currentLine = drawLine(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
                        }
                        tempPoly.draw(graphicSettings, getCanvasSize());
                        graphicSettings.draw(currentLine);
                    }
                    break;
                default:
                    break;
            }

            // Used for NON polygon shapes
            if (myTempShape != null){
                graphicSettings.draw(myTempShape);
            }
        }
    }

    /**
     * Resets the canvas zoom and clears all shapes and painting history
     */
    public void resetCanvas()
    {
        this.zoom(100);
        this.tempPolyList.clear();
        this.listOfMcShapes.clear();
    }

    /**
     * Allows the user to zoom in and out of the drawing
     * @param zoomValue the percentage of zoom to apply
     */
    public void zoom(int zoomValue)
    {
        double scale = zoomValue / 100.0;
        int h = (int)(this._height * scale);
        Dimension size = new Dimension(h, h);
        this.setSize(size);
        this.setPreferredSize(size);
        this.repaint();
        this.invalidate();
        this.revalidate();
    }

    /**
     * Getter for canvas height
     * @return returns the canvas height, useful for vector drawing
     */
    private int getCanvasSize(){
        return this.getHeight();
    }

    /**
     * Getter for the list of shapes being drawn
     * @return a list of McShapes
     */
    public ArrayList<McShape> getMcShapesList(){
        return this.listOfMcShapes;
    }

    /**
     * Setter for the list of shapes being drawn
     * @param theList the list of shapes to be in the drawing
     */
    public void setMcShapesList(ArrayList<McShape> theList)
    {
        this.listOfMcShapes = new ArrayList<McShape>();
        repaint();
        this.listOfMcShapes.addAll(theList);
        repaint();
    }

    /**
     * removes the last completed shape drawn to the canvas (Undo function)
     */
    public void removeLastShape()
    {
        if (currentlyDrawing == false && listOfMcShapes.size() > 0)
        {
            this.listOfMcShapes.remove(listOfMcShapes.size()-1);
            repaint();
        }
    }

    //-----------------USED FOR THE shadow SHAPE WHEN DRAWING
    //The Shape interface provides a set of methods for describing and inspecting geometric path objects.
    //A Point is not a path, therefore a point is represented as a zero length line

    /**
     * Draws a shadow point to the screen to show the user where the drawing will occur
     * @param x the x location on the canvas
     * @param y the y location on the canvas
     * @return a Line2D.Double of zero length
     */
    private Line2D.Double drawPoint(double x, double y){
        return new Line2D.Double(x, y, x, y);
    }

    /**
     * Draws a shadow line to the screen to show the user where the drawing will occur
     * @param x1 the x location of the first point of the line
     * @param y1 the y location of the first point of the line
     * @param x2 the x location of the second point of the line
     * @param y2 the y location of the second point of the line
     * @return a Line2D.Double
     */
    private Line2D.Double drawLine(double x1, double y1, double x2, double y2){

        return new Line2D.Double(x1, y1, x2, y2);
    }

    /**
     * Draws a shadow rectangle to the screen to show the user where the drawing will occur
     * @param x1 the x location of the first corner of the rectangle
     * @param y1 the y location of the first corner of the rectangle
     * @param x2 the x location of the opposite corner of the rectangle
     * @param y2 the y location of the opposite corner of the rectangle
     * @return a Rectangle2D.Double
     */
    private Rectangle2D.Double drawRectangle(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Draws a shadow ellipse to the screen to show the user where the drawing will occur
     * @param x1 the x location of the first corner of a rectangle that would fit the ellipse
     * @param y1 the y location of the first corner of a rectangle that would fit the ellipse
     * @param x2 the x location of the opposite corner of a rectangle that would fit the ellipse
     * @param y2 the y location of the opposite corner of a rectangle that would fit the ellipse
     * @return a Ellipse2D.Double
     */
    private Ellipse2D.Double drawEllipse(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Ellipse2D.Double(x, y, width, height);
    }
}
