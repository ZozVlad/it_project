package main.java;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import animatefx.animation.*;

public class LoginController implements Initializable {
	@FXML private Pane paneRegister = new Pane();
	@FXML private Pane paneLogin = new Pane();
	@FXML private ImageView closeRegisterImage = new ImageView();
	@FXML private ImageView closeLoginImage = new ImageView();

	@FXML private PasswordField passwordRegister = new PasswordField();
	@FXML private PasswordField passwordLogin = new PasswordField();

	@FXML private TextField visiblePwdRegister = new TextField();
	@FXML private TextField visiblePwdLogin = new TextField();

	@FXML private ImageView invisibleRegisterImage = new ImageView();
	@FXML private ImageView visibleRegisterImage = new ImageView();
	@FXML private ImageView invisibleLoginImage = new ImageView();
	@FXML private ImageView visibleLoginImage = new ImageView();
	
	
	@FXML private TextField loginRegister = new TextField();
	@FXML private TextField loginLogin = new TextField();
	
	private double x,y;

	@FXML 
	void goback(ActionEvent event) {
		loginLogin.setText("");
		passwordLogin.setText("");
		visiblePwdLogin.setText("");
		visiblePwdLogin.setStyle("-fx-background-color: -fx-text-box-border, -fx-background ;");
		passwordLogin.setStyle("-fx-background-color: -fx-text-box-border, -fx-background ;");
		loginLogin.setStyle("-fx-background-color: -fx-text-box-border, -fx-background ;");
		new FadeInRight(paneLogin).play();
		paneRegister.toBack();
	}

	@FXML
	void loginButton(ActionEvent event) {
		if(checkLoginAccount(loginLogin.getText(),passwordLogin.getText())) {
			AlertDialog.notific("Logged in successfully!", Notifications.SUCCESS);
			
			//next window code
			Collections.main_campaings = MySQL_core.loadCampaigns(Constants.account_id);
			
			Parent root;
	        try {
	        	Stage stage = new Stage();
	    		root = FXMLLoader.load(getClass().getResource("/main/resources/view/Main.fxml"));
	    		Scene scene = new Scene(root);
	    		stage.setScene(scene);
	    		stage.initStyle(StageStyle.TRANSPARENT);
	    		stage.getIcons().add(new Image(Constants.path));
	    		stage.setTitle(Constants.logo);
	    		((Node)(event.getSource())).getScene().getWindow().hide();
	    		Thread.sleep(500);
	    		new animatefx.animation.FadeIn(root).play();
	    		stage.show();
	            
	        }
	        catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
		} else {
			visiblePwdLogin.setStyle("-fx-background-color: red, -fx-background ;");
			passwordLogin.setStyle("-fx-background-color: red, -fx-background ;");
			loginLogin.setStyle("-fx-background-color: red, -fx-background ;");
			new Shake(visiblePwdLogin).play();
			new Shake(passwordLogin).play();
			new Shake(loginLogin).play();
			AlertDialog.notific("Incorrect login or password.", Notifications.ERROR);
		}
	}

	@FXML
	void signupButton(ActionEvent event) {
		if(checkRegisterAccount(loginRegister.getText(),passwordRegister.getText())) {
			/*====================================*/
			/*          some MySQL code           */
			//System.out.println("new account[" + loginRegister.getText() + ":" + passwordRegister.getText() + "]");
			try {
				Login_SingUp.insertNewAccount(loginRegister.getText(), passwordRegister.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*          some MySQL code           */
			/*====================================*/
			
			AlertDialog.notific("Registration completed!", Notifications.SUCCESS);
			goback(event);
		} else {
			visiblePwdRegister.setStyle("-fx-background-color: red, -fx-background ;");
			passwordRegister.setStyle("-fx-background-color: red, -fx-background ;");
			loginRegister.setStyle("-fx-background-color: red, -fx-background ;");
			new Shake(visiblePwdRegister).play();
			new Shake(passwordRegister).play();
			new Shake(loginRegister).play();
			AlertDialog.notific("Login already exists.", Notifications.ERROR);
		}
	}

	@FXML
	void toRegister(ActionEvent event) {
		loginRegister.setText("");
		passwordRegister.setText("");
		visiblePwdRegister.setText("");
		visiblePwdRegister.setStyle("-fx-background-color: -fx-text-box-border, -fx-background ;");
		passwordRegister.setStyle("-fx-background-color: -fx-text-box-border, -fx-background ;");
		loginRegister.setStyle("-fx-background-color: -fx-text-box-border, -fx-background ;");
		new FadeInDown(paneRegister).play();
		paneLogin.toBack();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}
	private void init() {
		closeLoginImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
			}
		});
		closeRegisterImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
			}
		});
		visibleLoginImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				passwordLogin.setText(visiblePwdLogin.getText());
				passwordLogin.setVisible(true);
				visiblePwdLogin.setVisible(false);
				invisibleLoginImage.setVisible(true);
				visibleLoginImage.setVisible(false);
			}
		});
		invisibleLoginImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				visiblePwdLogin.setText(passwordLogin.getText());
				visiblePwdLogin.setVisible(true);
				passwordLogin.setVisible(false);
				invisibleLoginImage.setVisible(false);
				visibleLoginImage.setVisible(true);
			}
		});

		visibleRegisterImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				passwordRegister.setText(visiblePwdRegister.getText());
				passwordRegister.setVisible(true);
				visiblePwdRegister.setVisible(false);
				invisibleRegisterImage.setVisible(true);
				visibleRegisterImage.setVisible(false);
			}
		});
		invisibleRegisterImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				visiblePwdRegister.setText(passwordRegister.getText());
				visiblePwdRegister.setVisible(true);
				passwordRegister.setVisible(false);
				invisibleRegisterImage.setVisible(false);
				visibleRegisterImage.setVisible(true);
			}
		});

		visiblePwdLogin.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				passwordLogin.setText(visiblePwdLogin.getText());
			};
		});
		visiblePwdRegister.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				passwordRegister.setText(visiblePwdRegister.getText());
			};
		});
	}
	private boolean checkLoginAccount(String login, String password) {
		if(login.equals("") || password.equals("")) return false;
		//mysql function required
		if(Login_SingUp.checkLogin(login, password)) {
			return true;
		} else {
			return false;
		}
	}
	private boolean checkRegisterAccount(String login, String password) {
		if(login.equals("") || password.equals("")) return false;
		//mysql function required
		if(!Login_SingUp.checkSignUp(login)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
}