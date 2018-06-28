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

public class ExportSortingPrefsTabTest{

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

        //Match m = scr.find("1529880469219.png");
        //assertNotNull(m);
        if (scr.exists("1529880469219.png") != null) {
            try {
                scr.click("1529880469219.png");
                scr.click("1529880473062.png");
                scr.click("1529880484278.png");
                scr.click("1529880490513.png");
                scr.click("1529880496515.png");
                scr.click("1529880531482.png");
                scr.click("1529880543221.png");
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}

