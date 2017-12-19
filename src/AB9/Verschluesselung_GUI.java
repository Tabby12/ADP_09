package AB9;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p.HAWcipher;

public class Verschluesselung_GUI{
	
	RSA_Algorithmus yourRSA;
	
	public Verschluesselung_GUI(Stage primaryStage,RSA_Algorithmus myRSA, Hybride_Verschluesselung hv) {
		
		primaryStage.setTitle("Kryptomaster 6000");
		
		GridPane contentPane = new GridPane();
		
		Scene scene = new Scene(contentPane,1000,800);
		

		
		Label prim1 = new Label("1. Primzahl =");
		Label prim2 = new Label("2. Primzahl =");
		Label publicKey = new Label("Public Key =");
		Label PrivateKey = new Label("Private Key =");
		Label modulusN = new Label("N =");
		Label phiVonN = new Label("Phi =");
		Label publicKeyGet = new Label("Erhaltener Public Key:");
		
		TextArea gottenPubKey = new TextArea();
		gottenPubKey.setPrefHeight(20);
		gottenPubKey.setPrefWidth(100);
		
		Label actPubKey = new Label("Public Key =");
		Label actModN = new Label("N =");
		
		Button activateKeyBtn = new Button("Aktiviere Public Key");
		activateKeyBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				yourRSA = new RSA_Algorithmus();
				yourRSA.erzeugeRSAmitPubKey(gottenPubKey.getText());
				actPubKey.setText("Public Key = " + yourRSA.getPublicKey());
				actModN.setText("N = " + yourRSA.getModulusN() );
			}
		});
		
		VBox rsaPaneLeft = new VBox();
		rsaPaneLeft.setPadding(new Insets(10));
		rsaPaneLeft.setSpacing(8);
		rsaPaneLeft.setPrefHeight(150);
		rsaPaneLeft.setPrefWidth(350);
		rsaPaneLeft.getChildren().add(prim1);
		rsaPaneLeft.getChildren().add(prim2);
		rsaPaneLeft.getChildren().add(modulusN);
		rsaPaneLeft.getChildren().add(phiVonN);
		rsaPaneLeft.getChildren().add(publicKey);
		rsaPaneLeft.getChildren().add(PrivateKey);
		rsaPaneLeft.getChildren().add(publicKeyGet);
		rsaPaneLeft.getChildren().add(gottenPubKey);
		rsaPaneLeft.getChildren().add(activateKeyBtn);
		
		Label publicKeyForFriend = new Label("Public Key zum weitergeben:");
		
		TextArea createdPubKey = new TextArea();
		createdPubKey.setPrefHeight(20);
		createdPubKey.setPrefWidth(100);
		createdPubKey.setEditable(false);
		
		Button createKeyBtn = new Button("Erzeuge Public Key");
		createKeyBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				createdPubKey.setText(myRSA.ErzeugeKeys(16));
				prim1.setText("1. Primzahl = " + myRSA.getPrim1());
				prim2.setText("2. Primzahl = " + myRSA.getPrim2());
				modulusN.setText("N = " + myRSA.getModulusN());
				phiVonN.setText("Phi = " + myRSA.getPhiVonN());
				publicKey.setText("Public Key = " + myRSA.getPublicKey());
				PrivateKey.setText("Private Key = " + myRSA.getPrivateKey());
				
			}
		});
		
		Label gabFiller = new Label();
		gabFiller.setPrefHeight(55);
		
		VBox rsaPaneRight = new VBox();
		rsaPaneRight.setPadding(new Insets(10));
		rsaPaneRight.setSpacing(8);
		rsaPaneRight.setPrefHeight(150);
		rsaPaneRight.setPrefWidth(300);
		rsaPaneRight.getChildren().add(publicKeyForFriend);
		rsaPaneRight.getChildren().add(createdPubKey);
		rsaPaneRight.getChildren().add(createKeyBtn);
		rsaPaneRight.getChildren().add(gabFiller);
		rsaPaneRight.getChildren().add(actPubKey);
		rsaPaneRight.getChildren().add(actModN);
		
		Label zuVerschluesseln = new Label("-----Zu Verschlüsselnder Text-----");
		Label VerSessionKey = new Label("Session Key =");
		
		TextArea textAreaVerschluesselt = new TextArea();
		textAreaVerschluesselt.setPrefHeight(40);
		textAreaVerschluesselt.setPrefWidth(100);
		
		TextArea textAreaVerschluesseln = new TextArea();
		textAreaVerschluesseln.setPrefHeight(40);
		textAreaVerschluesseln.setPrefWidth(100);
		
		Button verschluesselnBtn = new Button("Verschlüsseln");
		verschluesselnBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				textAreaVerschluesselt.setText(hv.verschluesseln(textAreaVerschluesseln.getText(), yourRSA));
				VerSessionKey.setText("Session Key= " + hv.Byte2BigInt(hv.sessionkey));
			}
		});
		
		
		VBox verschluesselnPane = new VBox();
		verschluesselnPane.setPadding(new Insets(10));
		verschluesselnPane.setSpacing(8);
		verschluesselnPane.setPrefHeight(200);
		verschluesselnPane.setPrefWidth(350);
		verschluesselnPane.getChildren().add(zuVerschluesseln);
		verschluesselnPane.getChildren().add(VerSessionKey);
		verschluesselnPane.getChildren().add(textAreaVerschluesseln);
		verschluesselnPane.getChildren().add(verschluesselnBtn);
		verschluesselnPane.getChildren().add(textAreaVerschluesselt);
		
		Label zuEntschluesseln = new Label("-----Zu Entschlüsselnder Text-----");
		Label EntSessionKey = new Label("Session Key =");
		TextArea textAreaEntschluesseln = new TextArea();
		TextArea textAreaEntschluesselt = new TextArea();
		Button entschluesselnBtn = new Button("Entschlüsseln");
		entschluesselnBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				textAreaEntschluesselt.setText(hv.entschluesseln(textAreaEntschluesseln.getText(), myRSA));
		        EntSessionKey.setText("session key = " + Hybride_Verschluesselung.Byte2BigInt(hv.sessionkey));
			}
		});
			
		VBox entschluesselnPane = new VBox();
		entschluesselnPane.setPadding(new Insets(10));
		entschluesselnPane.setSpacing(8);
		entschluesselnPane.setPrefHeight(200);
		entschluesselnPane.setPrefWidth(600);
		entschluesselnPane.getChildren().add(zuEntschluesseln);
		entschluesselnPane.getChildren().add(EntSessionKey);
		entschluesselnPane.getChildren().add(textAreaEntschluesseln);
		entschluesselnPane.getChildren().add(entschluesselnBtn);
		entschluesselnPane.getChildren().add(textAreaEntschluesselt);
		
		contentPane.add(rsaPaneLeft, 0, 0);
		contentPane.add(rsaPaneRight, 1, 0);
		contentPane.add(verschluesselnPane, 0, 1);
		contentPane.add(entschluesselnPane, 0, 2);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
