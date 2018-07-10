/*package org.jabref.gui.preftabs;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import static org.junit.Assert.assertNotNull;

public class PreferencesDialogTest{

    protected Screen scr;

    @BeforeClass
    public static void inicializaStatic() {
        Settings.MinSimilarity = 0.60;
        Settings.LogTime = true;

        ImagePath.setBundlePath("fig/PreferencesDialogTeste/");
    }

    @Before
    public void inicializa(){
        scr = new Screen();
    }

    @Test
    public void main() throws FindFailed{

        //Match m = scr.find("1529879302275.png");
        //assertNotNull(m);
        if (scr.exists("options.png") != null) {
            try {
                scr.click("options.png");
                scr.click("preferences.png");
                scr.click("general.png");
                scr.click("file.png");
                scr.click("resultado.png");
                scr.click("cancel.png");
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}

*/
