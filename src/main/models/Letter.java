package main.models;

public class Letter {
	private String subject;
	private String htmltext;
	private int letter_id;


	public Letter (int letter_id, String subject, String htmltext) {
		this.letter_id = letter_id;
		this.subject = subject;
		this.htmltext = htmltext;
	}

	public Letter() {
		
	}

	public int getLetter_id() {
		return letter_id;
	}
	public void setLetter_id(int letter_id) {
		this.letter_id = letter_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHtmltext() {
		return htmltext;
	}
	public void setHtmltext(String htmltext) {
		this.htmltext = htmltext;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(!super.equals(o)) return false;
		if (this == o) return true;
		if (o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		Letter otherObj = (Letter)o;
		return this.letter_id == otherObj.letter_id;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 76+133*letter_id;
	}
}
