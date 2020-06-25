package main.java;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Login.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle(Constants.logo);
		stage.getIcons().add(new Image(Constants.path));
		new animatefx.animation.FadeIn(root).play();
		stage.show();
	}
    
    public static void main(String[] args) {
        launch(args);
    }
}