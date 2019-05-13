package com.dwg;

import com.shapes.McShape;
import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import static com.gui.McDrawApp.*;

public class McCanvas extends JComponent
{

    //private Point p1 = new Point();
    //private Point p2 = new Point();
    //private List listLine = new ArrayList();
    //private List listRec = new ArrayList();
    //private List listMarker = new ArrayList();
    //private Line line;
    //private int clicks = 0;

    public ArrayList<McShape> listOfMcShapes = new ArrayList<McShape>();
    java.awt.Point drawStart, drawEnd;
    Graphics2D graphicSettings;


    public McCanvas() {
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                currentlyDrawing = true;
                Double startX = 0.0, startY = 0.0;
                System.out.println("Current action: "+currentPaintingAction);
                if (currentPaintingAction > 0){
                    drawStart = new java.awt.Point(e.getX(),e.getY());
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
                repaint();
                drawStart = null;
                drawEnd = null;
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
    }


    @Override
    public void paint(Graphics g) {
        graphicSettings = (Graphics2D)g;
        graphicSettings.setColor(edgeColour);
        g.setColor(Color.lightGray);
        g.fillRect(5, 5, 570, 500);
        for (McShape s : listOfMcShapes){
            s.draw(graphicSettings, this.getHeight());
        }
        //This draws the temporary shape so you can see where it will be
        if (drawStart != null && drawEnd != null){
            graphicSettings.setPaint(Color.pink);
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
            graphicSettings.draw(myTempShape);
        }
    }




    private int getCanvasSize(){
        return this.getHeight();
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
}


    //Class for "Point"
    //class Point {
//
    //    private int x;
    //    private int y;
//
    //    public Point() {
    //    }
//
    //    public Point(int x, int y) {
    //        this.x = x;
    //        this.y = y;
    //    }
//
    //    public int getX() {
    //        return x;
    //    }
//
    //    public int getY() {
    //        return y;
    //    }
//
    //    public void setX(int x) {
    //        this.x = x;
    //    }
//
    //    public void setY(int y) {
    //        this.y = y;
    //    }
    //}
    //Class for Line
    //class Line {
//
    //    private Point p1;
    //    private Point p2;
//
    //    public Line() {
    //    }
//
    //    public Line(Point p1, Point p2) {
    //        this.p1 = p1;
    //        this.p2 = p2;
    //    }
//
    //    public Point getP1() {
    //        return p1;
    //    }
//
    //    public Point getP2() {
    //        return p2;
    //    }
//
    //    public void setP1(Point p1) {
    //        this.p1 = p1;
    //    }
//
    //    public void setP2(Point p2) {
    //        this.p2 = p2;
    //    }
    //}