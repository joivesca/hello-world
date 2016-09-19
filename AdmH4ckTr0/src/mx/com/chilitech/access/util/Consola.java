package mx.com.chilitech.access.util;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Consola extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		Scene scene = new Scene(bp, 300, 300);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
