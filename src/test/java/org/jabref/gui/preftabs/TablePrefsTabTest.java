package org.jabref.gui.preftabs;
import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

public class SikuliTest {
    @BeforeEach
    Screen scr = new Screen();

    //App.open("/usr/bin/google-chrome");
    ImagePath.setBundlePath("fig/TablePrefsTab.sikuli/");
    // Instanciando objeto Sikuli
    Match m = scr.find("options.png");

    @Test
	public static void main(String[] args) throws FindFailed {
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
                scr.popup("Teste encerrado");
			} catch (FindFailed e) {
				e.printStackTrace();
			}
		}
	}
}