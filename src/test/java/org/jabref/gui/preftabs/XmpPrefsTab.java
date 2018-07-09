import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

public class SikuliTest {
    // Instanciando objeto Sikuli
    Screen scr = new Screen();
    //App.open("/usr/bin/google-chrome");
    ImagePath.setBundlePath("fig/XmpPrefsTabTeste.sikuli/");
    Match m = scr.find("options.png");

    @Test
	public static void main(String[] args) throws FindFailed {		
		if (scr.exists("options.png") != null) {
			try {
				scr.click("options.png");
				// Maximiza a janela
                scr.click("preferences.png");
                scr.click(".png");
                scr.click("pdf.png");
                scr.click("menos.png");
                scr.click("ok.png");
                scr.wait(2.0);		
                scr.popup("Teste encerrado");
			} catch (FindFailed e) {
				e.printStackTrace();
			}
		}
	}
}