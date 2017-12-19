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
		Verschluesselung_GUI  Verschluesselung_GUI = new Verschluesselung_GUI(primaryStage, rsa);
	}

}
