package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.models.Campaign;

public class UpdateStatus {
	public static void updateStatus() {
		for(Campaign c : Collections.main_campaings) {
			if(c.getStatus().equals("In progress")) {
				Date check = null;
				try {
					check = new SimpleDateFormat("ddMMyyyyHHmmss").parse(c.getNextDate().replaceAll("/", ""));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				if (check.before(new Date())) {            // если есть сообшения к отправке
					c.setStatus("Ready to send");
				}
				MySQL_core.editCampaignStatus(c.getCampaign_id(), c.getStatus());
			}
		}
	}
}
