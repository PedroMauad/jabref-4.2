import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

public class SikuliTest {
    // Instanciando objeto Sikuli
    Screen scr = new Screen();
    //App.open("/usr/bin/google-chrome");
    ImagePath.setBundlePath("fig/TableColumnsTabTeste.sikuli/");
    Match m = scr.find("options.png");

    @Test
	public static void main(String[] args) throws FindFailed {		
		if (scr.exists("options.png") != null) {
			try {
				scr.click("options.png");
                scr.click("preferences.png");
                scr.click("entryTableColumns.png");
                scr.click("naoenxergo.png");
                scr.click("seta.png");
                scr.click("showRelevance.png");
                scr.click("ok.png");		
                scr.popup("Teste encerrado");
			} catch (FindFailed e) {
				e.printStackTrace();
			}
		}
	}
}