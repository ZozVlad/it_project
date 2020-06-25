package main.models;

import java.util.ArrayList;

public class Campaign {
	private String name;
	private String status;
	private String email;
	private String password;
	private ArrayList<Prospect> prospects;
	private ArrayList<Letter> letters;
    public int dayLimit;
    public int toSend;
    public int lastRecipient;
    private String nextDate;
    private int campaign_id;
    private int account_id;
    
	public Campaign(String name, String status, String email, String password, ArrayList<Prospect> prospects,
			ArrayList<Letter> letters, int campaign_id) {
		super();
		this.name = name;
		this.status = status;
		this.email = email;
		this.password = password;
		this.prospects = prospects;
		this.letters = letters;
		this.campaign_id = campaign_id;
	}
	
	public Campaign() {
		
	}
	
	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(int campaign_id) {
		this.campaign_id = campaign_id;
	}

	public String getNextDate() {
		return nextDate;
	}

	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Prospect> getProspects() {
		return prospects;
	}

	public void setProspects(ArrayList<Prospect> prospects) {
		this.prospects = prospects;
	}

	public ArrayList<Letter> getLetters() {
		return letters;
	}

	public void setLetters(ArrayList<Letter> letters) {
		this.letters = letters;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(!super.equals(o)) return false;
		if (this == o) return true;
		if (o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		Campaign otherObj = (Campaign)o;
		return this.campaign_id == otherObj.campaign_id;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 76+133*campaign_id;
	}
}
