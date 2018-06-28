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

public class AppearancePrefsTabTest{

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

        //Match m = scr.find("1529879036797.png");
        //assertNotNull(m);
        if (scr.exists("1529879036797.png") != null) {
            try {
                scr.click("1529879036797.png");
                scr.click("1529879045461.png");
                scr.click("1529879935733.png");
                scr.click("1529879074368.png");
                scr.doubleClick("1529879963152.png");
                scr.type("5500");
                scr.click("1529879103185.png");
                scr.click("1529879107770.png");
                scr.click("1529879114778.png");
                scr.click("1529879994557.png");
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}
