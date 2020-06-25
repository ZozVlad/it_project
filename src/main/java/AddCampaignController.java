package main.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.models.Campaign;
import main.models.Letter;
import main.models.Prospect;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class AddCampaignController implements Initializable{
    static int num;
	static ArrayList<Prospect> prospects;
	static ArrayList<Letter> letters;
	static File selectedFile;
	@FXML private Label prospectsCount = new Label();
    @FXML private TabPane tabPane;
    @FXML private ChoiceBox<String> choiceConstant = new ChoiceBox<String>();
    @FXML private ChoiceBox<String> choiceProspect = new ChoiceBox<String>();
    @FXML private TextField campaignName = new TextField();
    @FXML private TextField email = new TextField();
    @FXML private PasswordField password = new PasswordField();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		letters = new ArrayList<Letter>();
		prospects = new ArrayList<Prospect>();
		num = 1;
		updateProspectsLabel();
		addOneTab();
		initChoiceBoxes();
	}
	private void addOneTab() {
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
        
        choiceProspect.getSelectionModel().selectedItemProperty().addListener( 
        		(ObservableValue<? extends String> observable, String oldValue, String newValue) 
        		-> addProspects(newValue));
        choiceProspect.getItems().add("Add prospects");
        choiceProspect.getItems().add("From .XLSX file");
        choiceProspect.getItems().add("Manually");
        choiceProspect.getSelectionModel().selectFirst();
    }
    
    
    @FXML
    void testEmail(ActionEvent event) {
    	CheckEmail.testMail(email.getText(), password.getText());
    }
    
    @FXML
    void send(ActionEvent event) {
    	if(checkNameEmailPassword() && check_tabs() && check_prospects()) {
    		get_letters();
    		for(Prospect p : prospects) {
    			p.setEmailInfo(letters.size());
    		}
    		Campaign c = new Campaign(campaignName.getText(), "Ready to send" , email.getText(), 
    				password.getText(), prospects, letters, MySQL_core.setUniqueCampaignId());
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			c.setNextDate(sdf.format(new Date()));
			
    		Collections.main_campaings.add(c);
    		MySQL_core.addCampaign(c, Constants.account_id);
    		
    		AlertDialog.notific("Campaign was successfully added/edited.", Notifications.SUCCESS);
    		goback();
    	}
    	updateProspectsLabel();
    }
    @FXML
    void back(MouseEvent event) {
    	goback();
    }
    private void goback() {
    	ChangeScene c = new ChangeScene("/main/resources/view/MainPane.fxml");
    	c.start();
    }
    @FXML
    void addEmail(MouseEvent event) {
    	addOneTab();
    }
    @FXML
    void setChoice(MouseEvent event) {
        choiceConstant.getSelectionModel().selectFirst();
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
    private void addProspects(String answer) {
    	switch (answer) {
    	case "Add prospects":
    		break;
    	case "From .XLSX file":
    		FileChooser fileChooser = new FileChooser();
    		File defaultDirectory = new File("c:/");
    		fileChooser.setInitialDirectory(defaultDirectory);
    		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xlsx", "*.xlsx*"));
    		
    		selectedFile = fileChooser.showOpenDialog(choiceProspect.getScene().getWindow());
    		if (selectedFile != null && check_file_extension(selectedFile.getName())) {
    			Parent root;
    	        try {
    	        	Stage stage = new Stage();
    	    		root = FXMLLoader.load(getClass().getResource("/main/resources/view/ExcelPage.fxml"));
    	    		Scene scene = new Scene(root);
    	    		stage.setScene(scene);
    	    		stage.initStyle(StageStyle.TRANSPARENT);
    	    		stage.initModality(Modality.APPLICATION_MODAL);
    	    		stage.getIcons().add(new Image(Constants.path));
    	    		stage.setTitle(Constants.logo);
    	    		new animatefx.animation.FadeIn(root).play();
    	    		stage.show();
    	    		stage.setOnHiding( event -> {updateProspectsLabel();} );
    	        }
    	        catch (IOException e) {
    	            e.printStackTrace();
    	        }
    		} else {
    			AlertDialog.notific("Could not open this file.", Notifications.ERROR);
    		}
    		break;
    	case "Manually":
   			Parent root;
	        try {
	        	Stage stage = new Stage();
	    		root = FXMLLoader.load(getClass().getResource("/main/resources/view/AddManually.fxml"));
	    		Scene scene = new Scene(root);
	    		stage.setScene(scene);
	    		stage.initStyle(StageStyle.TRANSPARENT);
	    		stage.initModality(Modality.APPLICATION_MODAL);
	    		stage.getIcons().add(new Image(Constants.path));
	    		stage.setTitle(Constants.logo);
	    		new animatefx.animation.FadeIn(root).play();
	    		stage.show();
	    		stage.setOnHiding( event -> {updateProspectsLabel();} );
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
    		break;
    	default:
    		break;
    	}
    	choiceProspect.getSelectionModel().selectFirst();
    }
    public static boolean check_file_extension(String file) {
    	return file.matches("^.*\\.(xlsx|XLSX)$");
    }
    public void updateProspectsLabel() {
		prospectsCount.setText("Prospects: " + prospects.size());
    }
    /*======================================================*/
    /*              check name,email,password               */
    /*======================================================*/
    private boolean checkNameEmailPassword() {
    	if(check_gmail(email.getText()) && check_name(campaignName.getText())) {
    		return true;
    	}
		AlertDialog.notific("Invalid data.", Notifications.ERROR);
    	return false;
    }
	private boolean check_gmail(String mail) {
		return mail.matches(".+@gmail\\.com");
	}
	private boolean check_name(String name) {
		return name.matches("^\\S.+");
	}
    /*======================================================*/
    /*                      check letter                    */
    /*======================================================*/
	private boolean check_tabs() {
    	if(!empty_tabs()) {
    		return true;
    	}
		AlertDialog.notific("Your letter list is empty.", Notifications.ERROR);
    	return false;
	}
	private boolean empty_tabs() {
		return (tabPane.getTabs().size() == 0);
	}
    /*======================================================*/
    /*                   check prospects                    */
    /*======================================================*/
	private boolean check_prospects() {
    	if(!empty_prospects()) {
    		return true;
    	}
		AlertDialog.notific("Your prospect list is empty.", Notifications.ERROR);
    	return false;
	}
	private boolean empty_prospects() {
		return (prospects.size() == 0);
	}
    /*======================================================*/
    /*                    get prospects                     */
    /*======================================================*/
	private void get_letters() {
		letters.clear();
    	for(Tab t : tabPane.getTabs()) {
    		Letter l = new Letter();
    		ObservableList<Node> nod = ((Parent) t.getContent()).getChildrenUnmodifiable();
    		TextField subject = (TextField) nod.get(0);
    		l.setSubject(subject.getText());
//    		System.out.println(subject.getText());
    		HTMLEditor html = (HTMLEditor) nod.get(1);
    		l.setHtmltext(html.getHtmlText());
//    		System.out.println(html.getHtmlText());
    		letters.add(l);
    	}
	}
}
