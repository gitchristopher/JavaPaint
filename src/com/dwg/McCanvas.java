package com.dwg;

import com.shapes.McShape;
import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;
import com.shapes.Ellipse;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import static com.gui.McDrawApp.*;

public class McCanvas extends JComponent
{

    public ArrayList<McShape> listOfMcShapes = new ArrayList<McShape>();
    //java.awt.Point[] xCoord, yCoord;  // Arrays to hold the coordinates.
    //public int pointNum = 0;          // Number of points in the arrays.
    java.awt.Point drawStart, drawEnd, firstPoint;
    Graphics2D graphicSettings;


    public McCanvas()
    {
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                currentlyDrawing = true;
                Double startX = 0.0, startY = 0.0;
                System.out.println("Current action: "+currentPaintingAction);
                if (currentPaintingAction != 5){
                    drawStart = new java.awt.Point(e.getX(),e.getY());
                }
                if (currentPaintingAction == 5 && drawStart != null){
                    //do nothing
                }else{
                    drawStart = new java.awt.Point(e.getX(),e.getY());
                    firstPoint = drawStart;
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            Shape myNewShape = null;

            @Override
            public void mouseReleased(MouseEvent e) {
                currentlyDrawing = false;
                //TODO: Change this to a switch statement
                if (currentPaintingAction == 1){
                    McShape myMcPoint = new Plot(drawStart.getX(), drawStart.getY(), edgeColour, getCanvasSize());
                    listOfMcShapes.add(myMcPoint);
                }
                if (currentPaintingAction == 2){
                    drawEnd = new java.awt.Point(e.getX(),e.getY());
                    McShape myMcLine = new Line(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, getCanvasSize());
                    listOfMcShapes.add(myMcLine);
                }
                if (currentPaintingAction == 3){
                    drawEnd = new java.awt.Point(e.getX(),e.getY());
                    McShape myMcRectangle = new Rectangle(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, fillColour, getCanvasSize());
                    listOfMcShapes.add(myMcRectangle);
                }
                if (currentPaintingAction == 4){
                    drawEnd = new java.awt.Point(e.getX(),e.getY());
                    McShape myMcEllipse = new Ellipse(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, fillColour, getCanvasSize());
                    listOfMcShapes.add(myMcEllipse);
                }
                if (currentPaintingAction == 5){
                    drawEnd = new java.awt.Point(e.getX(),e.getY());
                    McShape myMcLine = new Line(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY(), edgeColour, getCanvasSize());
                    listOfMcShapes.add(myMcLine);
                }
                //repaint refreshes a number of things, paint being one of them, that is why the method is below
                repaint();
                if (currentPaintingAction == 5) {
                    drawStart = drawEnd;
                    drawEnd = null;
                    repaint();
                }else{
                    drawStart = null;
                    drawEnd = null;
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                if (currentlyDrawing){
                    drawEnd = new java.awt.Point(e.getX(), e.getY());
                    repaint();
                }
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                int minSize = Math.min(c.getWidth(), c.getHeight());
                c.setSize(minSize,minSize);
            }
        });
    }




    //METHODS
    public void paint(Graphics g) {
        graphicSettings = (Graphics2D)g;
        graphicSettings.setColor(edgeColour);
        graphicSettings.setColor(Color.white);
        int size = Math.min(getWidth(), getHeight());
        graphicSettings.fillRect(0, 0, size, size );

        for (McShape s : listOfMcShapes){
            s.draw(graphicSettings, this.getHeight());
        }

        //This draws the temporary shape so you can see where it will be
        if (drawStart != null && drawEnd != null){
            graphicSettings.setPaint(Color.lightGray);
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
            if (currentPaintingAction == 5){
                myTempShape = drawLine(drawStart.getX(), drawStart.getY(), drawEnd.getX(), drawEnd.getY());
            }

            graphicSettings.draw(myTempShape);
        }
    }




    private int getCanvasSize(){
        return this.getHeight();
    }

    public ArrayList<McShape> getMcShapesList(){
        return this.listOfMcShapes;
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
}
