package mx.com.chilitech.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Hi extends Application{
	@Override
	 public void start(Stage stage) {
	 Button boton = new Button();
	 boton.setText("Di 'Hola Mundo'");
	 boton.setOnAction(new EventHandler<ActionEvent>() {
	 @Override
	 public void handle(ActionEvent event) {
	 System.out.println("Hola Mundo!");
	 }
	 });

	 StackPane layout = new StackPane();
	 layout.getChildren().add(boton);
	 Scene escena = new Scene(layout, 300, 250);
	 stage.setTitle("Hola Mundo!");
	 stage.setScene(escena);
	 stage.show();
	 }
	public static void main(String[] args) {
	 launch(args);
	 }
}
