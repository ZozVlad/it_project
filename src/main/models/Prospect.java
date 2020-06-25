package main.models;

public class Prospect {
	private String email = "";
	private String firstName = "";
	private String lastName = "";
	private String fullName = "";
	private String company = "";
	private String phone = "";
	private String address = "";
	private String city = "";
	private String snippet1 = "";
	private String snippet2 = "";
	private String snippet3 = "";
	private String snippet4 = "";
	private String snippet5 = "";
	public int lastLetterSent;
	private String emailInfo;
	private int prospect_id;

	public void setEmailInfo(int lettersSize) {
		emailInfo = lastLetterSent + "/" + lettersSize;
	}
	
	public int getLastLetterSent() {
		return lastLetterSent;
	}


	public void setLastLetterSent(int lastLetterSent) {
		this.lastLetterSent = lastLetterSent;
	}


	public String getEmailInfo() {
		return emailInfo;
	}


	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}



	
	public int getProspect_id() {
		return prospect_id;
	}
	public void setProspect_id(int prospect_id) {
		this.prospect_id = prospect_id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSnippet1() {
		return snippet1;
	}
	public void setSnippet1(String snippet1) {
		this.snippet1 = snippet1;
	}
	public String getSnippet2() {
		return snippet2;
	}
	public void setSnippet2(String snippet2) {
		this.snippet2 = snippet2;
	}
	public String getSnippet3() {
		return snippet3;
	}
	public void setSnippet3(String snippet3) {
		this.snippet3 = snippet3;
	}
	public String getSnippet4() {
		return snippet4;
	}
	public void setSnippet4(String snippet4) {
		this.snippet4 = snippet4;
	}
	public String getSnippet5() {
		return snippet5;
	}
	public void setSnippet5(String snippet5) {
		this.snippet5 = snippet5;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(!super.equals(o)) return false;
		if (this == o) return true;
		if (o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		Prospect otherObj = (Prospect)o;
		return this.prospect_id == otherObj.prospect_id;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 76+133*prospect_id;
	}
}
