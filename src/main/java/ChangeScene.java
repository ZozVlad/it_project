package main.java;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ChangeScene {
	String path;


	public ChangeScene(String path) {
		this.path = path;
	}

	public void start() {
    	Parent root = null;
    	try {
    		root = FXMLLoader.load(getClass().getResource(this.path));
    		new animatefx.animation.FadeIn(root).play();
    		MainController.borderpanesaver.setCenter(root);
    	}
    	catch (IOException e) {
    		System.out.println(e);
    		Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,e);
    	}
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
