package com.dwg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import static com.gui.McDrawApp.lineButton;
import static com.gui.McDrawApp.recButton;
import static com.gui.McDrawApp.markerButton;

public class McCanvas extends JComponent implements MouseInputListener {

        private Point p1 = new Point();
        private Point p2 = new Point();
        private List listLine = new ArrayList();
        private List listRec = new ArrayList();
        private List listMarker = new ArrayList();
        private Line line;
        private int clicks = 0;





        public McCanvas() {
            addMouseListener(this);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.lightGray);
            g.fillRect(5, 5, 570, 500);
            g.setColor(Color.black);
            Line currLine;

            for (int i = 0; i < listLine.size(); i++) {
                currLine = (Line) (listLine.get(i));
                g.drawLine(currLine.getP1().getX(), currLine.getP1().getY(),
                        currLine.getP2().getX(), currLine.getP2().getY());
            }

            for (int i = 0; i < listRec.size(); i++) {
                currLine = (Line) (listRec.get(i));
                drawRect(g, currLine.getP1().getX(), currLine.getP1().getY(),
                        currLine.getP2().getX(), currLine.getP2().getY());
            }

            for (int i = 0; i < listMarker.size(); i++) {
                currLine = (Line) (listMarker.get(i));
                g.drawLine(currLine.getP1().getX(), currLine.getP1().getY(),
                        currLine.getP2().getX(), currLine.getP2().getY());
            }
        }

    public void drawRect(Graphics g, int x, int y, int x2, int y2) {

        int pointX = Math.min(x, x2);
        int pointY = Math.min(y, y2);
        int valueW = Math.abs(x - x2);
        int valueH = Math.abs(y - y2);

        g.drawRect(pointX, pointY, valueW, valueH);
    }



        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            //Function for drawing lines
            if (clicks == 0 && lineButton == true) {
                line = new Line();
                line.setP1(new Point(x, y));
            } else {
                clicks = 0;
            }
            //Function for drawing rectangles
            if(clicks == 0 && recButton == true){
                line = new Line();
                line.setP1(new Point(x, y));
            }else {
                clicks = 0;
            }
            //Function for drawing Markers
            if (clicks == 0 && markerButton == true) {
                line = new Line();
                line.setP1(new Point(x, y));
                line.setP2(new Point(x, y));
                listMarker.add(line);
            } else {
                clicks = 0;
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            //Function for drawing lines
            if (clicks == 0 && lineButton == true) {
                line.setP2(new Point(x, y));
                listLine.add(line);
            } else {
                clicks = 0;
            }
            //Function for drawing rectangles
            if(clicks == 0 && recButton == true){
                line.setP2(new Point(x, y));
                listRec.add(line);

            }else {
                clicks = 0;
            }
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }


    //Class for "Point"
    class Point {

        private int x;
        private int y;

        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    //Class for Line
    class Line {

        private Point p1;
        private Point p2;

        public Line() {
        }

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public Point getP1() {
            return p1;
        }

        public Point getP2() {
            return p2;
        }

        public void setP1(Point p1) {
            this.p1 = p1;
        }

        public void setP2(Point p2) {
            this.p2 = p2;
        }
    }