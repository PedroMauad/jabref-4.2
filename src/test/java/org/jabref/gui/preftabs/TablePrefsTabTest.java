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

public class TablePrefsTabTest{

    protected Screen scr;

    @BeforeClass
    public static void inicializaStatic() {
        Settings.MinSimilarity = 0.60;
        Settings.LogTime = true;

        ImagePath.setBundlePath("fig/TablePrefsTab/");
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
                // Maximiza a janela
                scr.click("preferences.png");
                scr.click("entryTable.png");
                scr.click("showLastNamesOnly.png");
                scr.click("showNamesUnchanged.png");
                scr.click("ok.png");
                scr.wait(2.0);

            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }
}
*/
