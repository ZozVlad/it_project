package main.java;

import javafx.scene.control.ComboBox;

public class Item {
    private String excel;
    private ComboBox<String> comboBox = new ComboBox<String>();
	private String[] choices = {"Choose column","EMAIL(required)","FIRSTNAME","LASTNAME","FULLNAME","COMPANY","PHONE",
			"ADDRESS","CITY","SNIPPET1","SNIPPET2","SNIPPET3","SNIPPET4","SNIPPET5"};
	public Item(String excel) {
		super();
		this.excel = excel;
		comboBox.getItems().addAll(choices);
		comboBox.setValue("Choose column");
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public ComboBox<String> getComboBox() {
		return comboBox;
	}
	public void setComboBox(ComboBox<String> option) {
		this.comboBox = option;
	}


}