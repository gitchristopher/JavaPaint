package tests;

import files.VecFile;
import files.VecFileFilter;
import gui.McDrawApp;
import shapes.*;
import shapes.Polygon;
import shapes.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class TestVecFile
{
    //Set up helpers
    public int _width, _height;
    double x1;
    double y1;
    double x2;
    double y2;
    Color edgeColour = Color.BLACK;
    Color fillColour = Color.WHITE;
    int canvasSize = 10;
    public ArrayList<McShape> listOfMcShapes = new ArrayList<McShape>();

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

    //Declare Object
    VecFile vecFile;

    //Clear object before test
    @BeforeEach
    public void setUpVecFile() {
        vecFile = null;
    }

    /*Test 1: Test OpenFileDlg
     */
    @Test
    public void testOpenFileDlg(){
        vecFile = new VecFile();
        System.out.println("The file open method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String fileName = "testsave.vec";
        File f = new File(new File(fileName).getAbsolutePath());
        File selectedFile = f;
        OpenFile(selectedFile);

        //Get the current path where any file will automatically save to.
        //Then remove everything that isnt part of the directory address.
        //Then add the file name
        String currentDirFile = Paths.get(".").toAbsolutePath().normalize() + "\\" + fileName;

        File location = new File(currentDirFile);
        assertEquals(location, selectedFile);
    }
    /*Test 2: Test SaveFileDlg
     */
    @Test
    public void testSaveFileDlg(){
        vecFile = new VecFile();

        McShape myMcPoint = new Plot(0.5, 0.5, edgeColour, canvasSize);
        this.listOfMcShapes.add(myMcPoint);

        System.out.println("The file save method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        File myFile = new File(new File("testsave3.vec").getAbsolutePath());
        File aNewFile = new File(myFile.getName().replaceAll(".vec","")+".vec");
        SaveFile(listOfMcShapes, aNewFile);
        File f = new File(new File("testsave3.vec").getName());
        assertEquals(f, aNewFile);
    }
    /*Test 3: Test OpenFile
     */
    @Test
    public void testOpenFile(){
        vecFile = new VecFile();
        System.out.println("The file open method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String fileName = "testsave.vec";
        File f = new File(new File(fileName).getAbsolutePath());
        File selectedFile = f;

        //Get the current path where any file will automatically save to.
        //Then remove everything that isnt part of the directory address.
        //Then add the file name
        String currentDirFile = Paths.get(".").toAbsolutePath().normalize() + "\\" + fileName;


        File location = new File(currentDirFile);
        OpenFile(selectedFile);
        assertEquals(location, selectedFile);
    }
    /*Test 4: Test SaveFile
     */
    @Test
    public void testSaveFile(){
        vecFile = new VecFile();

        McShape myMcPoint = new Plot(0.5, 0.5, edgeColour, canvasSize);
        this.listOfMcShapes.add(myMcPoint);

        System.out.println("The file save method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        File myFile = new File(new File("testsave3.vec").getAbsolutePath());
        File aNewFile = new File(myFile.getName().replaceAll(".vec","")+".vec");
        SaveFile(listOfMcShapes, aNewFile);
        File f = new File(new File("testsave3.vec").getName());
        assertEquals(f, aNewFile);
    }
    /*Test 5: Test WriteData
     */
    @Test
    public void testWriteData() throws FileNotFoundException {
        vecFile = new VecFile();
        System.out.println("The file save method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        PrintWriter aNewFile = new PrintWriter("testsave3.vec");
        this.WriteData(listOfMcShapes, aNewFile);
        assertNotNull(aNewFile);
    }
    /*Test 6: Test LoadData
     */
    @Test
    public void testLoadData() throws FileNotFoundException {
        vecFile = new VecFile();
        File f = new File(new File("testsave.vec").getAbsolutePath());
        File selectedFile = f;
        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            LoadData(br);
            assertNotNull(br);
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
}
