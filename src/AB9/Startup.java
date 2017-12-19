package AB9;

import javafx.application.Application;
import javafx.stage.Stage;

public class Startup extends Application{
	
    /**
     * Die Main-Methode.
     * 
     * @param args die Aufrufparameter.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		RSA_Algorithmus rsa = new RSA_Algorithmus();
		Hybride_Verschluesselung hv = new Hybride_Verschluesselung();
		Verschluesselung_GUI  Verschluesselung_GUI = new Verschluesselung_GUI(primaryStage, rsa, hv);
	}

}
