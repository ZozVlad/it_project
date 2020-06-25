package main.java;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
//import tray.animations.AnimationType;
//import tray.notification.NotificationType;
//import tray.notification.TrayNotification;
import com.github.plushaze.traynotification.notification.*;
import com.github.plushaze.traynotification.animations.*;
public class AlertDialog {
    static void showAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Над программой работали:");
        alert.setContentText("1) Зозуля Владислав\n2) Кабак Александр\n3) Клищов Богдан\n4) Кулик Даниил\n5) Осетров Андрей");
 
        alert.showAndWait();
    }
    static boolean showLogOutConfirmation() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Log out");
    	alert.setHeaderText("Are you sure you want to log out?");
    	Optional<ButtonType> option = alert.showAndWait();

    	if (option.get() == null) {
    		return false;
    	} else if (option.get() == ButtonType.OK) {
    		return true;
    	} else if (option.get() == ButtonType.CANCEL) {
    		return false;
    	} else {
    		return false;
    	}
    }
    static void notific(String text, Notifications n) {
		TrayNotification tray = new TrayNotification();
		Notifications notification;
		tray.setAnimation(Animations.POPUP);
		notification = n;
		tray.setTitle(Constants.logo);
		tray.setMessage(text);
		tray.setNotification(notification);
		tray.showAndDismiss(Duration.seconds(1));
    }
}
