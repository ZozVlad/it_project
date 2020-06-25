package main.java;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Login_SingUp {
	private int id_account;
	private String login;
	private String password;

	public int getId() {
		return id_account;
	}
	public void setId(int id_account) {
		this.id_account = id_account;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public static boolean checkLogin(String newLogin, String newPassword) {
		boolean temp = false;

		ArrayList<Login_SingUp> acclist = new ArrayList<Login_SingUp>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = MySQL_core.getConnection()){
				Statement st = conn.createStatement();
				ResultSet srs = st.executeQuery("SELECT * FROM account");
				while (srs.next()) {
					Login_SingUp acc = new Login_SingUp();
					acc.setId(srs.getInt("id_account"));
					acc.setLogin(srs.getString("login"));
					acc.setPassword(srs.getString("password"));
					acclist.add(acc);
				}
				for(int i = 0; i< acclist.size(); i++) 
					if(newLogin.equals(acclist.get(i).getLogin()) & newPassword.equals(acclist.get(i).getPassword())) {
						temp = true;
						if(newLogin.equals(acclist.get(i).getLogin()) & newPassword.equals(acclist.get(i).getPassword())) {
							Constants.account_id = acclist.get(i).id_account;
							Constants.login = acclist.get(i).getLogin();
						}
					}
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return temp;
	}
	public static boolean checkSignUp(String newLogin) {
		boolean temp = false;
		ArrayList<Login_SingUp> acclist = new ArrayList<Login_SingUp>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = MySQL_core.getConnection()){
				Statement st = conn.createStatement();
				ResultSet srs = st.executeQuery("SELECT * FROM account");
				while (srs.next()) {
					Login_SingUp acc = new Login_SingUp();
					acc.setLogin(srs.getString("login"));
					acclist.add(acc);
					for(int i = 0; i < acclist.size(); i++)
						if(newLogin.equals(acclist.get(i).getLogin())) {
							temp = true;
						}
						else {
						}
				}
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return temp;
	}

	public static void insertNewAccount(String newLogin, String newPassword) throws IOException {
		String insert = "INSERT INTO account (login, `password`) VALUES ( ?, ? )";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = MySQL_core.getConnection()){

				PreparedStatement st = conn.prepareStatement(insert);
				st.setString(1, newLogin);
				st.setString(2, newPassword);
				st.executeUpdate();
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
}

