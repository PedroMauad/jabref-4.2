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

public class AdvancedTabTest{

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

        //Match m = scr.find("1529879302275.png");
        //assertNotNull(m);
        if (scr.exists("1529879302275.png") != null) {
            try {
                scr.click("1529879302275.png");
                scr.click("1529879316263.png");
                scr.click("1529880027127.png");
                scr.click("1529879333703.png");
                scr.click("1529879404920.png");
                scr.click("1529879408918.png");
                scr.doubleClick("1529880055141.png");
                scr.type("10");
                scr.click("1529879490966.png");
                scr.click("1529879495618.png");
                scr.click("1529879631855.png");
                scr.click("1529784322106.jpg");

            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}
