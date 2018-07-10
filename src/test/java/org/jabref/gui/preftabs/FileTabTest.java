package org.jabref.gui.preftabs;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import static org.junit.Assert.assertNotNull;

public class FileTabTest{

    protected Screen scr;

    @BeforeClass
    public static void inicializaStatic() {
        Settings.MinSimilarity = 0.60;
        Settings.LogTime = true;

        ImagePath.setBundlePath("fig/");
    }

    @Before
    public void inicializa(){
        scr = new Screen();
    }

    @Test
    public void main() throws FindFailed{

        //Match m = scr.find("1529883693023.png");
        //assertNotNull(m);
        if (scr.exists("1529883693023.png") != null) {
            try {
                scr.click("1529883693023.png");
                scr.click("1529883697672.png");
                scr.click("1529883705671.png");
                scr.click("1529883711601.png");
                scr.click("1529884201034.png");
                scr.click("1529884206633.png");
                scr.click("1529884211663.png");
                scr.click("1529884322106.png");
                scr.click("1529884229509.png");
                scr.click("1529884233709.png");
                scr.click("1529884243093.png");
                scr.click("1529884259705.png");
                scr.click("1529884264947.png");
                scr.click("1529884269576.png");
                scr.click("1529784322106.png");
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}


