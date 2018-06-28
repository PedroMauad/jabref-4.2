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

public class GeneralTabTest{

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

        //Match m = scr.find("1529883481429.png");
        //assertNotNull(m);
        if (scr.exists("1529883481429.png") != null) {
            try {
                scr.click("1529883481429.png");
                scr.click("1529883485701.png");
                scr.click("1529883651764.png");
                scr.click("1529883495470.png");
                scr.click("1529883500872.png");
                scr.click("1529883504773.png");
                scr.click("1529883517051.png");
                scr.click("1529883527967.png");
                scr.click("1529883538731.png");
                scr.click("1529883542811.png");
                scr.click("1529883548052.png");
                scr.click("1529883554068.png");
                scr.doubleClick("1529883569213.png");
                scr.type("Teste");
                scr.click("1529883584537.png");
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}
