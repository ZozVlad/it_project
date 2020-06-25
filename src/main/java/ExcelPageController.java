package main.java;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class ExcelPageController implements Initializable{
	@FXML private CheckBox checkIgnore;
    @FXML private TableView<Item> tableview;
    @FXML private TableColumn<Item, ComboBox<String>> c1;
    @FXML private TableColumn<Item, String> c2;
	private double x,y;
	String filename;
	ObservableList<Item> choice = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filename = AddCampaignController.selectedFile.getAbsolutePath();
		for(String s : Excel.getFirstRow(filename)) {
			choice.add(new Item(s));
		}
		
		tableview.setItems(choice);
		c1.setCellValueFactory(new PropertyValueFactory<Item, ComboBox<String>>("comboBox"));
		c2.setCellValueFactory(new PropertyValueFactory<Item, String>("excel"));
	}
	
	@FXML
	void add(ActionEvent event) {
		if(check_email() && check_size() && check_others()) {
			if(checkIgnore.isSelected()) {
				Excel.readingFromExcel(true, filename, convert());
			}
			else {
				Excel.readingFromExcel(false, filename, convert());
			}
			AlertDialog.notific("Successfully added.", Notifications.SUCCESS);
//			AddCampaignController.updateProspectsLabel();
    		((Node)(event.getSource())).getScene().getWindow().hide();
		} else {
			AlertDialog.notific("Invalid columns. Try again.", Notifications.ERROR);
		}
    }
	
	@FXML
	private void pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	private void dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}
    @FXML
    void close(MouseEvent event) {
    	((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
    }
	private boolean check_size() {
		if(choice.size() == 0) {
			return false;
		}
		return true;
	}
	private boolean check_email() {
		int count = 0;
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("EMAIL(required)")) {
				count++;
			}
		}
		if(count == 1) 
			return true;
		return false;
	}
	private boolean check_others() {
		int count = 0;
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("Choose column")) {
				count++;
			}
		}
		if(count > 0) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("FIRSTNAME")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("LASTNAME")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("FULLNAME")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("COMPANY")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("PHONE")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("ADDRESS")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("CITY")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("SNIPPET1")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("SNIPPET2")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("SNIPPET3")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("SNIPPET4")) {
				count++;
			}
		}
		if(count > 1) return false;
		count = 0;
		
		for(int i = 0; i < choice.size(); i++) {
			if(choice.get(i).getComboBox().getValue().equals("SNIPPET5")) {
				count++;
			}
		}
		if(count > 1) return false;
		return true;
	}
	
	private ArrayList<String> convert(){
		ArrayList<String> newChoices = new ArrayList<String>();
		for(int i = 0; i < choice.size(); i++) {
			newChoices.add(choice.get(i).getComboBox().getValue());
		}
		return newChoices;
	}
}
