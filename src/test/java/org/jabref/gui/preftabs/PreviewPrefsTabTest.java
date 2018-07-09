import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

public class SikuliTest {
    // Instanciando objeto Sikuli
    Screen scr = new Screen();
    //App.open("/usr/bin/google-chrome");
    ImagePath.setBundlePath("fig/PreviewPrefsTabTeste.sikuli/");
    Match m = scr.find("options.png");

    @Test
	public static void main(String[] args) throws FindFailed {		
		if (scr.exists("options.png") != null) {
			try {
				scr.click("options.png");
                scr.click("preferences.png");
                scr.click("entryPreview.png");
                scr.click("aids.png");
                scr.click("adicionar.png");
                scr.click("test.png");
                scr.click("telaResultado.png");
                scr.click("ok.png");
                scr.click("ok.png");		
                scr.popup("Teste encerrado");
			} catch (FindFailed e) {
				e.printStackTrace();
			}
		}
	}
}