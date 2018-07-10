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

public class EntryEditorPrefsTabTest{

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

        //Match m = scr.find("1529880277095.png");
        //assertNotNull(m);
        if (scr.exists("1529880277095.png") != null) {
            try {
                scr.click("1529880277095.png");
                scr.click("1529880281282.png");
                scr.click("1529880290633.png");
                scr.click("1529880298005.png");
                scr.click("1529880302132.png");
                scr.click("1529880305883.png");
                scr.click("1529880309848.png");
                scr.click("1529880313163.png");
                scr.click("1529880316291.png");
                scr.click("1529880328733.png");
                scr.click("1529784322106.png");

            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}


