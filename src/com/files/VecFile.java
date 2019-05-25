package com.files;

import com.dwg.McCanvas;
import com.gui.McDrawApp;
import com.shapes.*;
import com.shapes.Polygon;
import com.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

public class VecFile
{

    public VecFile()
    {

    }

    public static boolean OpenFileDlg()
    {
        System.out.println("The file open method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);

        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            OpenFile(selectedFile);
            return true;
        }else{
            return false;
        }
    }
    public static void SaveFileDlg(ArrayList<McShape> McShapeList)
    {
        System.out.println("The file save method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);

        int result = jFileChooser.showSaveDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File myFile = jFileChooser.getSelectedFile();
            File aNewFile = new File(myFile.getName().replaceAll(".vec","")+".vec");
            SaveFile(McShapeList, aNewFile);
        }
    }

    private static void OpenFile(File selectedFile)
    {

        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            LoadData(br);
            br.close();
        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }finally
        {
            //br.close();
        }
    }

    private static void SaveFile(ArrayList<McShape> McShapeList, File aNewFile)
    {
        System.out.println("Selected file: " + aNewFile.getAbsolutePath());
        try
        {
            PrintWriter infoToWrite = new PrintWriter( new BufferedWriter( new FileWriter(aNewFile)));
            WriteData(McShapeList, infoToWrite);
        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }
    }

    private static void WriteData(ArrayList<McShape> McShapeList, PrintWriter infoToWrite)
    {
        List<String> commandList = new ArrayList<>();

        Color edge = Color.BLACK;
        Color fill = null;
        boolean edgeUpdated = false;
        boolean fillUpdated = false;
        for (McShape shape:McShapeList)
        {
            // get colour
            Color shapeEdgeColour = shape.getEdgeColour();
            Color shapeFillColour = shape.getFillColour();

            // if its different to the current colour update the colour and set boolean to true
            if (shapeEdgeColour != null)
            {
                if (shapeEdgeColour != edge)
                {
                    edge = shapeEdgeColour;
                    edgeUpdated = true;
                }
            }
            if (shapeFillColour != null)
            {
                if (shapeFillColour != fill)
                {
                    fill = shapeFillColour;
                    fillUpdated = true;
                }
            }else
                {
                    if (shapeFillColour != fill)
                    {
                        fill = null;
                        fillUpdated = true;
                    }
                }

            // if boolean is true input PEN or FILL command and set the bool to false
            if (edgeUpdated)
            {
                String hex = "#"+Integer.toHexString(edge.getRGB()).substring(2).toUpperCase();
                commandList.add("PEN "+hex);
                edgeUpdated = false;
            }
            if (fillUpdated)
            {
                String hex;
                if (fill == null)
                {
                    hex = "OFF";
                }else
                    {
                        hex = "#"+Integer.toHexString(fill.getRGB()).substring(2).toUpperCase();
                    }
                commandList.add("FILL "+hex);
                fillUpdated = false;
            }

            // export shape details
            commandList.add(shape.commandExport());
        }

        for (String command:commandList)
        {
            infoToWrite.println(command);
        }

        infoToWrite.close();
    }

    private static void LoadData(BufferedReader br) throws IOException
    {
        ArrayList<McShape> importListOfMcShapes = new ArrayList<McShape>();

        try
        {
            Color edge = Color.BLACK;
            Color fill = null;


            String singleCommand = br.readLine();

            while (singleCommand != null) {

                // Separate the first word (the command) from the values after it
                int i = singleCommand.indexOf(' ');
                String command = singleCommand.substring(0, i);
                String value = singleCommand.substring(i);

                // Switch on command

                switch(command) {
                    case "PEN":
                        // Set the edge colour
                        String edgecolour = value.trim();
                        Color commandEdgeColour = Color.decode(edgecolour);
                        if (commandEdgeColour != null)
                        {
                            if (commandEdgeColour != edge)
                            {
                                edge = commandEdgeColour;
                            }
                        }
                        break;
                    case "FILL":
                        // Set the fill colour
                        String fillcolour = value.trim();
                        Color commandFillColour;
                        if (fillcolour.contains("OFF"))
                        {
                            commandFillColour = null;
                        }else
                            {
                                commandFillColour = Color.decode(fillcolour);
                            }

                        if (commandFillColour != fill)
                        {
                            fill = commandFillColour;
                        }

                        break;
                    case "PLOT":
                        String plotValues = value.trim();
                        Plot newPlot = new Plot(plotValues, edge, fill);
                        importListOfMcShapes.add((McShape)newPlot);
                        break;
                    case "LINE":
                        String lineValues = value.trim();
                        Line newLine = new Line(lineValues, edge, fill);
                        importListOfMcShapes.add((McShape)newLine);
                        break;
                    case "RECTANGLE":
                        String rectValues = value.trim();
                        Rectangle newRectangle = new Rectangle(rectValues, edge, fill);
                        importListOfMcShapes.add((McShape)newRectangle);
                        break;
                    case "ELLIPSE":
                        String ellValues = value.trim();
                        Ellipse newEllipse = new Ellipse(ellValues, edge, fill);
                        importListOfMcShapes.add((McShape)newEllipse);
                        break;
                    case "POLYGON":
                        String polyValues = value.trim();
                        Polygon newPolygon = new Polygon(polyValues, edge, fill);
                        importListOfMcShapes.add((McShape)newPolygon);
                        break;
                    default:
                        // code block
                }




                // Get the next line of the saved document
                singleCommand = br.readLine();
            }

        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }catch (NullPointerException e)
        {
            System.out.println("An Null Error Occurred");
            System.exit(0);
        }finally
        {
            br.close();
        }
        br.close();
        McDrawApp.LoadedData.clear();
        McDrawApp.LoadedData.addAll(importListOfMcShapes);
    }
}


