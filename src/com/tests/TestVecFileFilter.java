package com.tests;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

import com.files.VecFileFilter;
import org.junit.jupiter.api.*;

public class TestVecFileFilter
{
    //Set up helpers
    String _fileDescription = "";
    String _fileExtension = "";

    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        return (f.getName().toLowerCase().endsWith(this._fileExtension));
    }

    public String getDescription() {
        return this._fileDescription;
    }

    //Declare Object
    VecFileFilter vecFileFilter;

    //Clear object before test
    @BeforeEach
    public void setUpEllipse() {
        vecFileFilter = null;
    }
    /*Test 1: Constructing vecFileFilter
     */
    @Test
    public void testVecFileFilterConstruction(){

        vecFileFilter = new VecFileFilter(_fileExtension, _fileDescription);
        this._fileExtension = ".vec";
        this._fileDescription = "Vec Images (*.vec)";
    }
    /*Test 2: Test accept
     */
    @Test
    public void testVecFileFilterAccept(){

        File f = new File(new File("testsave.vec").getAbsolutePath());
        File selectedFile = f;

        assertTrue(accept(selectedFile));
    }
    /*Test 3: Test getDescription
     */
    @Test
    public void testVecFileFilterGetDescription(){
        vecFileFilter = new VecFileFilter(_fileExtension, _fileDescription);
        this._fileExtension = ".vec";
        this._fileDescription = "Vec Images (*.vec)";
        assertEquals("Vec Images (*.vec)", getDescription());
    }

}
