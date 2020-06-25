package main.java;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.models.Campaign;
import main.models.Letter;
import main.models.Prospect;


public class MySQL_core {
	public static int setUniqueProspectId() {
		int id = 1;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){

				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT id_prospect FROM Prospects");
				ArrayList<Integer> prospect_id = new ArrayList<Integer>();

				while (resultSet.next()) {
					Prospect prospect = new Prospect();
					prospect.setProspect_id(resultSet.getInt(1));
					prospect_id.add(prospect.getProspect_id());   
				}

				for(int i = 0; i < prospect_id.size(); i++) {
					Integer a = prospect_id.get(i);
					if(a == id) {
						id++;
						i = 0;
					}
				}
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");

			System.out.println(ex);
		}
		
		return id;
	}

	public static int setUniqueCampaignId() {
		int id = 1;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){

				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT id_campaign FROM Campaign");
				ArrayList<Integer> campaign_id = new ArrayList<Integer>();

				while (resultSet.next()) {
					Campaign campaign = new Campaign();
					campaign.setCampaign_id(resultSet.getInt(1));
					campaign_id.add(campaign.getCampaign_id());   
				}

				for(int i = 0; i < campaign_id.size(); i++) {
					Integer a = campaign_id.get(i);
					if(a == id) {
						id++;
						i = 0;
					}
				}
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return id;
	}

	public static int setUniqueLetterId() {
		int id = 1;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){

				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT id_letter FROM Letter");
				ArrayList<Integer> letter_id = new ArrayList<Integer>();
				while (resultSet.next()) {
					Letter letter = new Letter();
					letter.setLetter_id(resultSet.getInt(1));
					letter_id.add(letter.getLetter_id());   
				}
				for(int i = 0; i < letter_id.size(); i++) {
					Integer a = letter_id.get(i);
					if(a == id) {
						id++;
						i = 0;
					}
				}
			}
		}

		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return id;
	}

	public static void addProspect (Prospect prospect, int campaign_id) { 
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){         
				String sqlCommandInsert = "INSERT INTO Prospects (id_prospect, campaign_id, email, first_name, last_name, "
						+ "full_name, company, phone, address, city, snippet_1, snippet_2, snippet_3, snippet_4, snippet_5, lastLetterSent, emailInfo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sqlCommandInsert);
				preparedStatement.setInt(1, prospect.getProspect_id());
				preparedStatement.setInt(2, campaign_id);
				preparedStatement.setString(3, prospect.getEmail());
				preparedStatement.setString(4, prospect.getFirstName());
				preparedStatement.setString(5, prospect.getLastName());
				preparedStatement.setString(6, prospect.getFullName());
				preparedStatement.setString(7, prospect.getCompany());
				preparedStatement.setString(8, prospect.getPhone());
				preparedStatement.setString(9, prospect.getAddress());
				preparedStatement.setString(10, prospect.getCity());
				preparedStatement.setString(11, prospect.getSnippet1());
				preparedStatement.setString(12, prospect.getSnippet2());
				preparedStatement.setString(13, prospect.getSnippet3());
				preparedStatement.setString(14, prospect.getSnippet4());
				preparedStatement.setString(15, prospect.getSnippet5());
				preparedStatement.setInt(16, prospect.getLastLetterSent());
				preparedStatement.setString(17, prospect.getEmailInfo());
				preparedStatement.executeUpdate();
				int rows = preparedStatement.executeUpdate(sqlCommandInsert);
				System.out.println("Added %d " + rows);
			}
		}

		catch(Exception ex){
				    System.out.println("Connection failed...");
				    System.out.println(ex);
		}
	}

	public static void addCampaign (Campaign c, int account_id) { 
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){         

				String sqlCommandInsert = "INSERT INTO Campaign (id_campaign, account_id, name, status, user_email, "
						+ "user_password, day_limit, toSend, lastRecipient, nextDate) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sqlCommandInsert);
				preparedStatement.setInt(1, c.getCampaign_id());
				preparedStatement.setInt(2, account_id);
				preparedStatement.setString(3, c.getName());
				preparedStatement.setString(4, c.getStatus());
				preparedStatement.setString(5, c.getEmail());
				preparedStatement.setString(6, c.getPassword());
				preparedStatement.setInt(7, c.dayLimit);
				preparedStatement.setInt(8, c.toSend);
				preparedStatement.setInt(9, c.lastRecipient);
				preparedStatement.setString(10, c.getNextDate());
				preparedStatement.executeUpdate();

				for(Letter l : c.getLetters()) {
					l.setLetter_id(setUniqueLetterId());
					addLetter(l, c.getCampaign_id());
				}
				for(Prospect p : c.getProspects()) {
					p.setProspect_id(setUniqueProspectId());
					addProspect(p, c.getCampaign_id());
				}
				int rows = preparedStatement.executeUpdate(sqlCommandInsert);
				System.out.println("Added %d " + rows);
			}
		}

		catch(Exception ex){
				    System.out.println("Connection failed...");
				    System.out.println(ex);
		}
	}

	public static void addLetter (Letter letter, int campaign_id) { 
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){         

				String sqlCommandInsert = "INSERT INTO Letter (id_letter, campaign_id, subject, Htmltext) "
						+ "VALUES (?, ?, ?, ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sqlCommandInsert);
				preparedStatement.setInt(1, letter.getLetter_id());
				preparedStatement.setInt(2, campaign_id);
				preparedStatement.setString(3, letter.getSubject());
				preparedStatement.setString(4, letter.getHtmltext());
				preparedStatement.executeUpdate();
				int rows = preparedStatement.executeUpdate(sqlCommandInsert);
				System.out.println("Added %d " + rows);
			}
		}

		catch(Exception ex){
				    System.out.println("Connection failed...");
				    System.out.println(ex);
		}
	}

	public static ArrayList<Campaign> loadCampaigns(int account_id) {
		ArrayList<Campaign> campaigns = new ArrayList<Campaign>();
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				Statement statement = conn.createStatement();
				String s = "SELECT * FROM Campaign WHERE Campaign.account_id = "
						+ account_id;
				ResultSet rs = statement.executeQuery(s);
				while (rs.next()) {
					Campaign campaign = new Campaign();
					campaign.setCampaign_id(rs.getInt(1));
					campaign.setAccount_id(rs.getInt(2));
					campaign.setName(rs.getString(3));
					campaign.setStatus(rs.getString(4));
					campaign.setEmail(rs.getString(5));
					campaign.setPassword(rs.getString(6));
					campaign.dayLimit = rs.getInt(7);
					campaign.toSend = rs.getInt(8);
					campaign.lastRecipient = rs.getInt(9);
					campaign.setNextDate(rs.getString(10));
					campaign.setProspects(getProspectsFromMysql(campaign.getCampaign_id()));
					campaign.setLetters(getLettersFromMysql(campaign.getCampaign_id()));
					campaigns.add(campaign);
				}
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");

			System.out.println(ex);
		}
		return campaigns;
	}

	public static ArrayList<Prospect> getProspectsFromMysql(int campaign_id) {
		ArrayList<Prospect> prospects = new ArrayList<Prospect>();
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){

				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Prospects WHERE campaign_id = " + campaign_id);

				while (resultSet.next()) {
					Prospect prospect = new Prospect();
					prospect.setProspect_id(resultSet.getInt(1));
					//		    prospect.setCampaign_id(resultSet.getInt(2));
					prospect.setEmail(resultSet.getString(3));
					prospect.setFirstName(resultSet.getString(4));
					prospect.setLastName(resultSet.getString(5));
					prospect.setFullName(resultSet.getString(6));
					prospect.setCompany(resultSet.getString(7));
					prospect.setPhone(resultSet.getString(8));
					prospect.setAddress(resultSet.getString(9));
					prospect.setCity(resultSet.getString(10));
					prospect.setSnippet1(resultSet.getString(11));
					prospect.setSnippet2(resultSet.getString(12));
					prospect.setSnippet3(resultSet.getString(13));
					prospect.setSnippet4(resultSet.getString(14));
					prospect.setSnippet5(resultSet.getString(15));
					prospect.setLastLetterSent(resultSet.getInt(16));
					prospect.setEmailInfo(resultSet.getString(17));
					prospects.add(prospect);
				}
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return prospects;
	}

	public static ArrayList<Letter> getLettersFromMysql(int campaign_id) {
		ArrayList<Letter> letters = new ArrayList<Letter>();
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Letter WHERE campaign_id = " + campaign_id);
				while (resultSet.next()) {
					Letter letter = new Letter();
					letter.setLetter_id(resultSet.getInt(1));
					letter.setSubject(resultSet.getString(3));
					letter.setHtmltext(resultSet.getString(4)); 
					letters.add(letter);
				}
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return letters;
	}

	public static Connection getConnection() throws SQLException, IOException{
		return DriverManager.getConnection(Constants.sqlUrl, Constants.sqlLogin, Constants.sqlPassword);
	}

	public static boolean deleteCampaign(int campaign_id) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				String deleteSQL = "DELETE FROM Campaign WHERE id_campaign = " + campaign_id;
				Statement statement = conn.createStatement();
				statement.executeUpdate(deleteSQL);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}

	public static boolean deleteProspect(int prospect_id) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				String deleteSQL = "DELETE FROM Prospects WHERE id_prospect = " + prospect_id;
				Statement statement = conn.createStatement();
				statement.executeUpdate(deleteSQL);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}

	public static boolean editCampaignAndLetter(int campaign_id, String name, String email, String password, ArrayList<Letter> letters) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				Statement statement = conn.createStatement();
				String updateCampaignSQL = "UPDATE Campaign SET `name` = '" + name + "' WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL);
				String updateCampaignSQL1 =	"UPDATE Campaign SET `user_email` = '" + email + "' WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL1);
				String updateCampaignSQL2 =	"UPDATE Campaign SET `user_password` = '" + password + "' WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL2);
				tmp = true;
				for (Letter l : letters) {
					Statement statement1 = conn.createStatement();
					String updateLetterSQL = "UPDATE Letter SET `subject` = '" + l.getSubject() + 
							"' WHERE id_letter = " + l.getLetter_id() + " AND campaign_id = " + campaign_id;
					statement1.executeUpdate(updateLetterSQL);
					String updateLetterSQL1  = "UPDATE Letter SET `HtmlText` = '" + l.getHtmltext() +
							"' WHERE id_letter = " + l.getLetter_id() + " AND campaign_id = " + campaign_id;
					statement1.executeUpdate(updateLetterSQL1);
					tmp = true;
				}
			}
		}
		catch(Exception ex){
			System.out.println("1 Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}
	public static boolean editCampaignStatus(int campaign_id, String status) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				Statement statement = conn.createStatement();
				String updateCampaignSQL = "UPDATE Campaign SET `status` = '" + status + "' WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("1 Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}
	public static boolean editCampaignLastFourFields(int campaign_id, int dayLimit, int toSend, int lastRecipient, String nextDate) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				Statement statement = conn.createStatement();
				String updateCampaignSQL1 = "UPDATE Campaign SET `day_limit` = " + dayLimit + " WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL1);
				String updateCampaignSQL2 = "UPDATE Campaign SET `toSend` = " + toSend + " WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL2);
				String updateCampaignSQL3 = "UPDATE Campaign SET `lastRecipient` = " + lastRecipient + " WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL3);
				String updateCampaignSQL4 =	"UPDATE Campaign SET `nextDate` = '" + nextDate + "' WHERE id_campaign = " + campaign_id;
				statement.executeUpdate(updateCampaignSQL4);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}

	public static boolean editProspectField(int prospect_id, String value, String field) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				String updateProspectSQL = "UPDATE Prospects SET `" + field + "` = '" + value + "' WHERE id_prospect = " + prospect_id;
				PreparedStatement preparedStatement = conn.prepareStatement(updateProspectSQL);
				preparedStatement.executeUpdate(updateProspectSQL);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}

	public static boolean editProspectLastLetterSent(int prospect_id, int lastLetterSent) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				String updateProspectSQL = "UPDATE Prospects SET `lastLetterSent` = " + lastLetterSent + " WHERE id_prospect = " + prospect_id;
				PreparedStatement preparedStatement = conn.prepareStatement(updateProspectSQL);
				preparedStatement.executeUpdate(updateProspectSQL);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}

	public static boolean editProspectEmailInfo(int prospect_id, String emailInfo) {
		boolean tmp = false;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = getConnection()){
				String updateProspectSQL = "UPDATE Prospects SET `emailInfo` = '" + emailInfo + "' WHERE id_prospect = " + prospect_id;
				PreparedStatement preparedStatement = conn.prepareStatement(updateProspectSQL);
				preparedStatement.executeUpdate(updateProspectSQL);
				tmp = true;
			}
		}
		catch(Exception ex){
			System.out.println("Connection failed...");
			System.out.println(ex);
		}
		return tmp;
	}
}
