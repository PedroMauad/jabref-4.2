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

public class BibtexKeyPatternPrefTabTest{

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

        //Match m = scr.find("1529879697037.png");
        //assertNotNull(m);
        if (scr.exists("1529879697037.png") != null) {
            try {
                scr.click("1529879697037.png");
                scr.click("1529879703882.png");
                scr.click("1529879720927.png");
                scr.type("1529879777801.png", "Teste");
                scr.type("1529879849626.png", "Teste");
                scr.type("1529880145726.png", "Teste");
                scr.click("1529880116113.png");
                scr.click("1529784322106.png");
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}

