package com.dwg;

import com.shapes.*;
import com.shapes.McShape;
import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;
import com.shapes.Ellipse;
import com.shapes.Polygon;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.event.MouseInputListener;

import static com.gui.McDrawApp.*;

public class McCanvas extends JComponent
{
    // The initial height and width of the canvas
    public int _width, _height;
    public ArrayList<McShape> listOfMcShapes = new ArrayList<McShape>();
    public ArrayList<McShape> tempPolyList = new ArrayList<McShape>();
    //java.awt.Point[] xCoord, yCoord;  // Arrays to hold the coordinates.
    //public int pointNum = 0;          // Number of points in the arrays.
    java.awt.Point drawStart, drawEnd;
    Graphics2D graphicSettings;


    public McCanvas(int width, int height)
    {
        this._width = width;
        this._height = height;
        this.setSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));


        // Lets the system know the user is currently drawing a shape.
        // Stores the current X Y co ordinates for use in later drawings.
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Current action: "+currentPaintingAction);
                currentlyDrawing = true;
                drawStart = new Point(e.getX(),e.getY());
            }
        });

        this.addMouseListener(new MouseAdapter() {
            Shape myNewShape = null;

            // When the mouse is released the drawing is complete except for Polygons.
            // For a normal shape a new object is created and added to the list of shapes.
            // For a Polygon the system is told that the user is still drawing if there is a currently active Polygon.
            // After a polygon has been created any mouse release will either add to the existing polygon or finalise the shape.
            @Override
            public void mouseReleased(MouseEvent e) {

                if (currentPaintingAction == 5 && isPolyOpen)
                {
                    currentlyDrawing = true;
                }else
                    {
                        if (tempPolyList.size()>0)
                        {
                            Polygon poly = (Polygon)tempPolyList.get(0);
                            poly.finishPolygon();
                            listOfMcShapes.add((McShape)poly);
                            tempPolyList.clear();
                        }
                        currentlyDrawing = false;
                    }
                //TODO: Change this to a switch statement
                if (currentPaintingAction == 1){
                    McShape myMcPoint = new Plot(drawStart.getX(), drawStart.getY(), edgeColour, getCanvasSize());
                    listOfMcShapes.add(myMcPoint);
                }
                if (currentPaintingAction == 2){
                    drawEnd = new Point(e.getX(),e.getY());
                    McShape myMcLine = new Line(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, getCanvasSize());
                    listOfMcShapes.add(myMcLine);
                }
                if (currentPaintingAction == 3){
                    drawEnd = new Point(e.getX(),e.getY());
                    McShape myMcRectangle = new Rectangle(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, fillColour, getCanvasSize());
                    listOfMcShapes.add(myMcRectangle);
                }
                if (currentPaintingAction == 4){
                    drawEnd = new Point(e.getX(),e.getY());
                    McShape myMcEllipse = new Ellipse(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, fillColour, getCanvasSize());
                    listOfMcShapes.add(myMcEllipse);
                }

                // Get the last polygon added to the list, this is the current polygon being made as it hasnt be closed yet
                // Make sure its not the first two point as you cant close a zero line polygon
                // Check to see is the release point is near the beginning of the polygon and if it is, close the polygon
                // If its not, add another point to the polygon
                if (currentPaintingAction == 5){
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
                }

                drawStart = null;
                drawEnd = null;
                repaint();
            }
        });

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

        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK,false),"Undo");

        ActionMap am = this.getActionMap();
        am.put("Undo", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("My god this was terribly hard to find information on.");
                removeLastShape();
            }
        });
        //this.addComponentListener(new ComponentAdapter() {
        //    @Override
        //    public void componentResized(ComponentEvent e) {
        //        Component c = (Component)e.getSource();
        //        int minSize = Math.min(c.getWidth(), c.getHeight());
        //        c.setSize(minSize,minSize);
        //    }
        //});
    }




    //METHODS
    public void paint(Graphics g) {
        graphicSettings = (Graphics2D)g;
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

            if (currentPaintingAction == 1){
                myTempShape = drawPoint(drawStart.getX(), drawStart.getY());
            }
            if (currentPaintingAction == 2){
                myTempShape = drawLine(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
            }
            if (currentPaintingAction == 3){
                myTempShape = drawRectangle(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
            }
            if (currentPaintingAction == 4){
                myTempShape = drawEllipse(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
            }

            // The shape is cast from the list of shapes as a polygon to give access to its methods
            // The Polygon object draws itself, a temp line is drawn to show where the new line will be
            if (currentPaintingAction == 5){
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
            }

            // Used for NON polygon shapes
            if (myTempShape != null){
                graphicSettings.draw(myTempShape);
            }
        }
    }


    public void zoom(int zoomValue)
    {
        double scale = zoomValue / 100.0;
        int h = (int)(this._height * scale);
        Dimension size = new Dimension(h, h);
        System.out.println(h + " " + scale);
        this.setSize(size);
        this.setPreferredSize(size);
        this.repaint();
        this.invalidate();
        this.revalidate();
    }

    private int getCanvasSize(){
        return this.getHeight();
    }

    public ArrayList<McShape> getMcShapesList(){
        return this.listOfMcShapes;
    }

    public void setMcShapesList(ArrayList<McShape> theList)
    {
        this.listOfMcShapes = new ArrayList<McShape>();
        System.out.println("Cleared list");
        System.out.println(this.listOfMcShapes.size());
        for (McShape s:listOfMcShapes)
        {
            System.out.println("Here for: "+s);
        }
        repaint();
        System.out.println("Adding new items list");
        this.listOfMcShapes.addAll(theList);
        System.out.println(this.listOfMcShapes.size());
        repaint();
        for (McShape s:listOfMcShapes)
        {
            System.out.println(s);
        }
    }

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
    private Line2D.Double drawPoint(double x, double y){
        return new Line2D.Double(x, y, x, y);
    }
    private Line2D.Double drawLine(double x1, double y1, double x2, double y2){

        return new Line2D.Double(x1, y1, x2, y2);
    }
    private Rectangle2D.Double drawRectangle(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Rectangle2D.Double(x, y, width, height);
    }
    private Ellipse2D.Double drawEllipse(double x1, double y1, double x2, double y2){

        //Gets the top lefthand corner by finding the closest value to zero
        Double x = Math.min(x1, x2);
        Double y = Math.min(y1, y2);

        //Determines the width and height of the rectangle, GO MATHS!
        Double width = Math.abs(x1 - x2);
        Double height = Math.abs(y1 - y2);

        return new Ellipse2D.Double(x, y, width, height);
    }
    //private Path2D.Double drawPoly(McShape s){
    //    Polygon p = (Polygon)s;
    //    double[] _xPoly = new double[p._xList.size()];
    //    int counter = 0;
    //    for (double d: p._xList)
    //    {
    //        _xPoly[counter] = d;
    //        counter++;
    //    }
    //    double[] _yPoly = new double[p._yList.size()];
    //    counter = 0;
    //    for (double d: p._yList)
    //    {
    //        _yPoly[counter] = d;
    //        counter++;
    //    }
    //    Path2D.Double thePath = new Path2D.Double();
    //    thePath.moveTo(_xPoly[0],_yPoly[0]);
    //    for (int i = 1; i < _xPoly.length; i++)
    //    {
    //        thePath.lineTo(_xPoly[i],_yPoly[i]);
    //    }
    //    return thePath;
    //}
}
