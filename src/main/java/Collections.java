package main.java;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.models.Campaign;
import main.models.Prospect;

public class Collections {
	public static ObservableList<Campaign> campaignTableView = FXCollections.observableArrayList();
	public static ObservableList<Prospect> prospectView = FXCollections.observableArrayList();
	public static ArrayList<Campaign> main_campaings = new ArrayList<>();
}
