package main.java;

import javafx.scene.control.TextField;
import main.models.Campaign;

public class Search {
	public static void search(boolean running_needed, boolean completed_needed, boolean stopped_needed, boolean ready_needed, TextField search) {
		Collections.campaignTableView.clear();
		search.setText("");
		if(!running_needed && !completed_needed && !stopped_needed && !ready_needed) {
			Collections.campaignTableView.addAll(Collections.main_campaings);
			return;
		}
		for(Campaign c : Collections.main_campaings) {
			if(check(c, running_needed, completed_needed, stopped_needed, ready_needed, search)) {
				Collections.campaignTableView.add(c);
			}
		}
	}
	public static void searchName(String search) {
		Collections.campaignTableView.clear();
		for(Campaign c : Collections.main_campaings) {
			if(c.getName().indexOf(search) >= 0) {
				Collections.campaignTableView.add(c);
			}
		}
	}
	private static boolean check(Campaign c, boolean running_needed, boolean completed_needed, boolean stopped_needed, boolean ready_needed, TextField search) {
		boolean result = false;
		if(running_needed && c.getStatus().equals("In progress"))		result = true;
		else if(completed_needed && c.getStatus().equals("Completed"))  result = true;
		else if(stopped_needed && c.getStatus().equals("Stopped"))  	result = true;
		else if(ready_needed && c.getStatus().equals("Ready to send"))  result = true;
		return result;
	}
}
