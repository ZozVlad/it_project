package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.models.Prospect;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class AddManuallyController {
    @FXML private TextField email;
    @FXML private TextField lastname;
    @FXML private TextField fullname;
    @FXML private TextField company;
    @FXML private TextField firstname;
    @FXML private TextField phone;
    @FXML private TextField address;
    @FXML private TextField city;
    @FXML private TextField snippet1;
    @FXML private TextField snippet2;
    @FXML private TextField snippet3;
    @FXML private TextField snippet5;
    @FXML private TextField snippet4;
	private double x,y;
    @FXML
    void add(ActionEvent event) {
    	if(email.getText().equals("")) {
    		AlertDialog.notific("Enter email.", Notifications.WARNING);
    	} else {
    		Prospect p = new Prospect();
    		p.setEmail(email.getText());
    		p.setFirstName(firstname.getText());
    		p.setLastName(lastname.getText());
    		p.setFullName(fullname.getText());
    		p.setCompany(company.getText());
    		p.setPhone(phone.getText());
    		p.setAddress(address.getText());
    		p.setCity(city.getText());
    		p.setSnippet1(snippet1.getText());
    		p.setSnippet2(snippet2.getText());
    		p.setSnippet3(snippet3.getText());
    		p.setSnippet4(snippet4.getText());
    		p.setSnippet5(snippet5.getText());
    		AddCampaignController.prospects.add(p);
    		AlertDialog.notific("Successfully added.", Notifications.SUCCESS);
    		((Node)(event.getSource())).getScene().getWindow().hide();
    	}
    }

    @FXML
    void close(MouseEvent event) {
    	((Node)(event.getSource())).getScene().getWindow().hide();
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

}
