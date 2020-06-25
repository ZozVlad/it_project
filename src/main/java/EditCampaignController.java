package main.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class EditCampaignController implements Initializable{
    @FXML private TextField campaignName;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private TabPane tabPane;
    @FXML private ChoiceBox<String> choiceConstant;
    static int num;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		num = 1;
		initChoiceBoxes();
		initData();
		initLetters();
	}
	
    @FXML
    void back(MouseEvent event) {
    	ChangeScene c = new ChangeScene("/main/resources/view/MainPane.fxml");
    	c.start();
    }

    @FXML
    void close(MouseEvent event) {
		((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void hide(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setIconified(true);
    }

    @FXML
    void save(ActionEvent event) {
    	MainPaneController.c.setName(campaignName.getText());
    	MainPaneController.c.setEmail(email.getText());
    	MainPaneController.c.setPassword(password.getText());
    	for(int i = 0; i < MainPaneController.c.getLetters().size(); i++) {
    		Tab t = tabPane.getTabs().get(i);
    		ObservableList<Node> nod = ((Parent) t.getContent()).getChildrenUnmodifiable();
    		TextField subject = (TextField) nod.get(0);
    		MainPaneController.c.getLetters().get(i).setSubject(subject.getText());
    		HTMLEditor html = (HTMLEditor) nod.get(1);
    		MainPaneController.c.getLetters().get(i).setHtmltext(html.getHtmlText());
    	}
    	MySQL_core.editCampaignAndLetter(MainPaneController.c.getCampaign_id(), 
    			MainPaneController.c.getName(), MainPaneController.c.getEmail(), 
    			MainPaneController.c.getPassword(), MainPaneController.c.getLetters());
    	ChangeScene c = new ChangeScene("/main/resources/view/MainPane.fxml");
    	c.start();
    }

    @FXML
    void setChoice(KeyEvent event) {
        choiceConstant.getSelectionModel().selectFirst();
    }
    private void initData() {
    	campaignName.setText(MainPaneController.c.getName());
    	email.setText(MainPaneController.c.getEmail());
    	password.setText(MainPaneController.c.getPassword());
    }
    private void initLetters() {
    	for(int i = 0; i < MainPaneController.c.getLetters().size(); i++) {
    		Tab t = addOneTab();
    		ObservableList<Node> nod = ((Parent) t.getContent()).getChildrenUnmodifiable();
    		TextField subject = (TextField) nod.get(0);
    		subject.setText(MainPaneController.c.getLetters().get(i).getSubject());
    		HTMLEditor html = (HTMLEditor) nod.get(1);
    		html.setHtmlText(MainPaneController.c.getLetters().get(i).getHtmltext());
    	}
    }
	private Tab addOneTab() {
        Pane pane = null;
        try {
            pane = FXMLLoader.<Pane>load(getClass().getResource("/main/resources/view/HtmlEditor.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HtmlEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Tab t = new Tab("Email #" + num++);
        t.setContent(pane);
        t.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
            	num--;
            }
        });
        tabPane.getTabs().add(t);
        return t;
	}
    private void initChoiceBoxes() {
        choiceConstant.getSelectionModel().selectedItemProperty().addListener( 
        		(ObservableValue<? extends String> observable, String oldValue, String newValue) 
        		-> copy(newValue));
        choiceConstant.getItems().add("Copy constants");
        choiceConstant.getItems().add("FULLNAME");
        choiceConstant.getItems().add("FIRSTNAME");
        choiceConstant.getItems().add("LASTNAME");
        choiceConstant.getItems().add("EMAIL");
        choiceConstant.getItems().add("COMPANY");
        choiceConstant.getItems().add("CITY");
        choiceConstant.getItems().add("PHONE");
        choiceConstant.getItems().add("ADDRESS");
        choiceConstant.getItems().add("SNIPPET1");
        choiceConstant.getItems().add("SNIPPET2");
        choiceConstant.getItems().add("SNIPPET3");
        choiceConstant.getItems().add("SNIPPET4");
        choiceConstant.getItems().add("SNIPPET5");
        choiceConstant.getSelectionModel().selectFirst();
    }
    private void copy(String answer) {
    	final Clipboard clipboard = Clipboard.getSystemClipboard();
    	final ClipboardContent content = new ClipboardContent();
    	switch (answer) {
    	case "Copy constants":
    		break;
    	default:
    		content.putString("%" + answer +"%");
    		AlertDialog.notific("Copied.", Notifications.INFORMATION);
        	clipboard.setContent(content);
        	choiceConstant.getSelectionModel().selectFirst();
    		break;
    	}
    }
    @FXML
    void testEmail(ActionEvent event) {
    	CheckEmail.testMail(email.getText(), password.getText());
    }
}
