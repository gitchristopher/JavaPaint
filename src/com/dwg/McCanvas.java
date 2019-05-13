package com.dwg;

import com.shapes.McShape;
import com.shapes.Line;
import com.shapes.Plot;
import com.shapes.Rectangle;

import java.awt.*;
import java.awt.event.*;
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

            //Line currLine;

            //for (int i = 0; i < listLine.size(); i++) {
            //    currLine = (Line) (listLine.get(i));
            //    g.drawLine(currLine.getP1().getX(), currLine.getP1().getY(),
            //            currLine.getP2().getX(), currLine.getP2().getY());
            //}
//
            //for (int i = 0; i < listRec.size(); i++) {
            //    currLine = (Line) (listRec.get(i));
            //    drawRect(g, currLine.getP1().getX(), currLine.getP1().getY(),
            //            currLine.getP2().getX(), currLine.getP2().getY());
            //}
//
            //for (int i = 0; i < listMarker.size(); i++) {
            //    currLine = (Line) (listMarker.get(i));
            //    g.drawLine(currLine.getP1().getX(), currLine.getP1().getY(),
            //            currLine.getP2().getX(), currLine.getP2().getY());
            //}
        }

    public void drawRect(Graphics g, int x, int y, int x2, int y2) {

        int pointX = Math.min(x, x2);
        int pointY = Math.min(y, y2);
        int valueW = Math.abs(x - x2);
        int valueH = Math.abs(y - y2);

        g.drawRect(pointX, pointY, valueW, valueH);
    }


    private int getCanvasSize(){
        return this.getHeight();
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