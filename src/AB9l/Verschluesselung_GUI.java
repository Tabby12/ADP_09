package AB9l;/**
 * Created by MaWi on 14.06.2017.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Verschluesselung_GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;

    private GridPane root;

    private Scene scene;

    private Hybrid_Verschluesselung hybrid;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.hybrid = new Hybrid_Verschluesselung();
        this.work();
    }

    private void work(){
        root = new GridPane();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Verschlüsseler 3000");
        root.setPadding(new Insets(20, 20, 20, 20));

        Label LabelIntern = new Label("Intern");
        GridPane.setConstraints(LabelIntern,2 ,0 );
        root.getChildren().add(LabelIntern);


        TextField TFMassage = new TextField("Text eingeben");
        GridPane.setConstraints(TFMassage, 2,2);
        root.getChildren().add(TFMassage);
        Label LabelMassage = new Label("Klartext eingeben");
        GridPane.setConstraints(LabelMassage, 1,2);
        root.getChildren().add(LabelMassage);
        TextField TFPublicKey = new TextField(String.valueOf(hybrid.getMyPublicKey()[0]));
        TFPublicKey.setDisable(true);
        GridPane.setConstraints(TFPublicKey, 2,4);
        root.getChildren().add(TFPublicKey);
        Label LabelPublicKey = new Label("Public Key");
        GridPane.setConstraints(LabelPublicKey, 1,4);
        root.getChildren().add(LabelPublicKey);
        TextField TFPublicN = new TextField(String.valueOf(hybrid.getMyPublicKey()[1]));
        TFPublicN.setDisable(true);
        GridPane.setConstraints(TFPublicN, 2,6);
        root.getChildren().add(TFPublicN);
        Label LabelPublicN = new Label("Public N");
        GridPane.setConstraints(LabelPublicN, 1,6);
        root.getChildren().add(LabelPublicN);
        TextField TFCheffrierterText = new TextField();
        GridPane.setConstraints(TFCheffrierterText, 2,8);
        root.getChildren().add(TFCheffrierterText);
        Label LabelCiffText = new Label("Chiffrierter Text");
        GridPane.setConstraints(LabelCiffText, 1,8);
        root.getChildren().add(LabelCiffText);
        Button ButtonVerschluesseln = new Button("Verschlüsseln");
        GridPane.setConstraints(ButtonVerschluesseln, 2, 10);
        root.getChildren().add(ButtonVerschluesseln);

        root.setGridLinesVisible(true);

        ButtonVerschluesseln.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(! TFMassage.getText().isEmpty()){
                    TFCheffrierterText.setText(hybrid.chiffrierenHybrid(TFMassage.getText(), hybrid.getMyPublicKey()[0],hybrid.getMyPublicKey()[1]));
                }
            }
        });


        TextField TFMassage2 = new TextField("Text eingeben");
        GridPane.setConstraints(TFMassage2, 5,2);
        root.getChildren().add(TFMassage2);
        Label LabelMassage2 = new Label("Verschlüsselten Text eingeben");
        GridPane.setConstraints(LabelMassage2, 4,2);
        root.getChildren().add(LabelMassage2);
        TextField TFPublicKey2 = new TextField();
        GridPane.setConstraints(TFPublicKey2, 5,4);
        root.getChildren().add(TFPublicKey2);
        Label LabelPublicKey2 = new Label("Public Key");
        GridPane.setConstraints(LabelPublicKey2, 4,4);
        root.getChildren().add(LabelPublicKey2);
        TextField TFPublicN2 = new TextField();
        GridPane.setConstraints(TFPublicN2, 5,6);
        root.getChildren().add(TFPublicN2);
        Label LabelPublicN2 = new Label("Public N");
        GridPane.setConstraints(LabelPublicN2, 4,6);
        root.getChildren().add(LabelPublicN2);
        TextField TFCheffrierterText2 = new TextField();
        GridPane.setConstraints(TFCheffrierterText2, 5,8);
        root.getChildren().add(TFCheffrierterText2);
        Label LabelCiffText2 = new Label("Dechiffrierter Text");
        GridPane.setConstraints(LabelCiffText2, 4,8);
        root.getChildren().add(LabelCiffText2);
        Button ButtonEntschluesseln = new Button("Entschlüsseln");
        GridPane.setConstraints(ButtonEntschluesseln, 5, 10);
        root.getChildren().add(ButtonEntschluesseln);

        ButtonEntschluesseln.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(! TFMassage2.getText().isEmpty()) {
                    TFCheffrierterText2.setText(hybrid.dechiffrierenHybrid(TFMassage2.getText()));
                }
            }
        });


        Label LabelExtern = new Label("Extern");
        GridPane.setConstraints(LabelExtern,2 ,12 );
        root.getChildren().add(LabelExtern);

        TextField TFMassage3 = new TextField("Text eingeben");
        GridPane.setConstraints(TFMassage3, 2,14);
        root.getChildren().add(TFMassage3);
        Label LabelMassage3 = new Label("Klartext eingeben");
        GridPane.setConstraints(LabelMassage3, 1,14);
        root.getChildren().add(LabelMassage3);
        TextField TFPublicKey3 = new TextField();
        GridPane.setConstraints(TFPublicKey3, 2,16);
        root.getChildren().add(TFPublicKey3);
        Label LabelPublicKey3 = new Label("Public Key");
        GridPane.setConstraints(LabelPublicKey3, 1,16);
        root.getChildren().add(LabelPublicKey3);
        TextField TFPublicN3 = new TextField();
        GridPane.setConstraints(TFPublicN3, 2,18);
        root.getChildren().add(TFPublicN3);
        Label LabelPublicN3 = new Label("Public N");
        GridPane.setConstraints(LabelPublicN3, 1,18);
        root.getChildren().add(LabelPublicN3);
        TextField TFCheffrierterText3 = new TextField();
        GridPane.setConstraints(TFCheffrierterText3, 2,20);
        root.getChildren().add(TFCheffrierterText3);
        Label LabelCiffText3 = new Label("Chiffrierter Text");
        GridPane.setConstraints(LabelCiffText3, 1,20);
        root.getChildren().add(LabelCiffText3);
        Button ButtonVerschluesseln3 = new Button("Verschlüsseln");
        GridPane.setConstraints(ButtonVerschluesseln3, 2, 22);
        root.getChildren().add(ButtonVerschluesseln3);

        root.setGridLinesVisible(true);

        ButtonVerschluesseln3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(! TFMassage3.getText().isEmpty()){
                    TFCheffrierterText3.setText(hybrid.chiffrierenHybrid(TFMassage3.getText(), Integer.parseInt(TFPublicKey3.getText()),Integer.parseInt(TFPublicN3.getText())));
                }
            }
        });

        stage.show();
    }
}
