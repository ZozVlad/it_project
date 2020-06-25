package main.java;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.models.Campaign;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class MainController implements Initializable {
	@FXML private Button about = new Button();
	@FXML private BorderPane borderPane = new BorderPane();
	private double x,y;
	public static BorderPane borderpanesaver;
    @FXML private Label label;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label.setText("Hello " + Constants.login + "!");
		borderpanesaver = borderPane;
		ChangeScene c = new ChangeScene("/main/resources/view/MainPane.fxml");
		c.start();
	}
    @FXML
    void UpdateAction(ActionEvent event) {
    	UpdateStatus.updateStatus();
    }
	@FXML
	void SendAction(ActionEvent event) {
		for(Campaign c : Collections.main_campaings) {
			Date check = null;
			try {
				check = new SimpleDateFormat("ddMMyyyyHHmmss").parse(c.getNextDate().replaceAll("/", ""));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			if (c.toSend < c.getLetters().size() && check.before(new Date()) && c.getStatus().equals("Ready to send")) {                     // если есть сообшения к отправке
				c.setStatus("In progress");
				c.dayLimit = 0;
				if (c.getProspects().size() > 80) {                             					// с этой строки выполняется отправка сообщений,когда количество получателей больше 80
					for (int j = c.lastRecipient; j < c.getProspects().size(); j++) {               // отправляем с 0-го/следующего после последнего предыдущего сообщения получателя
						if (c.dayLimit != 80) {                                     			//если дневной лимит не достигнут 80 отправить письмо
							try {
								EmailSending.sendMessage(c.getEmail(), c.getPassword(), c.getProspects().get(j), c.getLetters().get(c.toSend).getSubject(), c.getLetters().get(c.toSend).getHtmltext());
								c.getProspects().get(j).lastLetterSent++;
//								System.out.println(c.getProspects().get(j).getFirstName() + " " + c.getProspects().get(j).lastLetterSent);
//								System.out.println("daylimit: " + c.dayLimit + "  toSend: " + c.toSend);
								c.getProspects().get(j).setEmailInfo(c.getLetters().size());
								c.dayLimit++;
								c.lastRecipient++;
							} catch (MessagingException e) {
								e.printStackTrace();
							}

							if ((c.lastRecipient) == c.getProspects().size()) { // если дошли до конца списка отправляем следующим пользователям сообщения
								c.toSend++;
								c.lastRecipient = 0;
								j = -1;
								if (c.toSend == c.getLetters().size()) { //если же все сообщения отправили ставим статус complited и выходим
									c.setStatus("Completed"); //сделать статус отправлено
									MySQL_core.editCampaignStatus(c.getCampaign_id(), c.getStatus());
									break;
								}
							}
						} else {
							System.out.println("Cообщения отправлены. Дневной лимит достигнут!");
							break;
						}
					}
				} else if (c.getProspects().size() < 80) {                     // с этой строки выполняется отправка сообщений, когда к-во юзеров меньше 80(задержка между отправкой 1 день)
					
					for (int j = 0; j < c.getProspects().size(); j++) {                      //просто отправка до конца сайза и переход на следующее сообщение
						try {
							EmailSending.sendMessage(c.getEmail(), c.getPassword(), c.getProspects().get(j), c.getLetters().get(c.toSend).getSubject(), c.getLetters().get(c.toSend).getHtmltext());
							c.getProspects().get(j).lastLetterSent++;
							MySQL_core.editProspectLastLetterSent(c.getProspects().get(j).getProspect_id(), c.getProspects().get(j).lastLetterSent);
//							System.out.println(c.getProspects().get(j).getFirstName() + " " + c.getProspects().get(j).lastLetterSent);
//							System.out.println("daylimit: " + c.dayLimit + "  toSend: " + c.toSend);
							c.getProspects().get(j).setEmailInfo(c.getLetters().size());
							MySQL_core.editProspectEmailInfo(c.getProspects().get(j).getProspect_id(), c.getProspects().get(j).getEmailInfo());
							c.dayLimit++;
							c.lastRecipient++;
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
					c.toSend++;
					c.dayLimit = 80;
					c.lastRecipient = 0;
					if (c.toSend == c.getLetters().size()) { //если же все сообщения отправили ставим статус complited и выходим
						c.setStatus("Completed"); //сделать статус отправлено
						MySQL_core.editCampaignStatus(c.getCampaign_id(), c.getStatus());
						break;
					}
				}
//				check.setTime((new Date().getTime()) + 85000000);
				check.setTime((new Date().getTime()) + 30000);
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
				c.setNextDate(sdf.format(check));
			}
			MySQL_core.editCampaignLastFourFields(c.getCampaign_id(), c.dayLimit, c.toSend, c.lastRecipient, c.getNextDate());
			MySQL_core.editCampaignStatus(c.getCampaign_id(), c.getStatus());
		}
		//AlertDialog.notific("All letters have been sent.", NotificationType.SUCCESS);
		Collections.campaignTableView.clear();
		Collections.campaignTableView.addAll(Collections.main_campaings);
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
	void aboutButton(ActionEvent event) {
		AlertDialog.showAbout();
	}


	@FXML
	void logoutButton(ActionEvent event) {
		if(AlertDialog.showLogOutConfirmation()) {
			Parent root;
			try {
				Stage stage = new Stage();
				root = FXMLLoader.load(getClass().getResource("/main/resources/view/Login.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle(Constants.logo);
				stage.getIcons().add(new Image(Constants.path));
				((Node)(event.getSource())).getScene().getWindow().hide();
				Thread.sleep(500);
				new animatefx.animation.FadeIn(root).play();
				stage.show();
			}
			catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
