package main.java;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.models.Prospect;
public class ProspectPaneController implements Initializable {
	@FXML private TableView<Prospect> prospectTable;

	@FXML private TableColumn<Prospect, String> info;
	@FXML private TableColumn<Prospect, String> EMAIL;
	@FXML private TableColumn<Prospect, String> FIRSTNAME;
	@FXML private TableColumn<Prospect, String> LASTNAME;
	@FXML private TableColumn<Prospect, String> FULLNAME;
	@FXML private TableColumn<Prospect, String> COMPANY;
	@FXML private TableColumn<Prospect, String> PHONE;
	@FXML private TableColumn<Prospect, String> ADDRESS;
	@FXML private TableColumn<Prospect, String> CITY;
	@FXML private TableColumn<Prospect, String> SNIPPET1;
	@FXML private TableColumn<Prospect, String> SNIPPET2;
	@FXML private TableColumn<Prospect, String> SNIPPET3;
	@FXML private TableColumn<Prospect, String> SNIPPET4;
	@FXML private TableColumn<Prospect, String> SNIPPET5;
	@FXML private TextField searchField;
	@FXML private ChoiceBox<String> choicebox;
	@FXML private Hyperlink hyperlink = new Hyperlink();

	private ArrayList<String> emailDuplicates = new ArrayList<String>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Collections.prospectView.clear();
		Collections.prospectView.addAll(MainPaneController.c.getProspects());
		info.setCellValueFactory(new PropertyValueFactory<Prospect, String>("emailInfo"));
		EMAIL.setCellValueFactory(new PropertyValueFactory<Prospect, String>("email"));
		FIRSTNAME.setCellValueFactory(new PropertyValueFactory<Prospect, String>("firstName"));
		LASTNAME.setCellValueFactory(new PropertyValueFactory<Prospect, String>("lastName"));
		FULLNAME.setCellValueFactory(new PropertyValueFactory<Prospect, String>("fullName"));
		COMPANY.setCellValueFactory(new PropertyValueFactory<Prospect, String>("company"));
		PHONE.setCellValueFactory(new PropertyValueFactory<Prospect, String>("phone"));
		ADDRESS.setCellValueFactory(new PropertyValueFactory<Prospect, String>("address"));
		CITY.setCellValueFactory(new PropertyValueFactory<Prospect, String>("city"));
		SNIPPET1.setCellValueFactory(new PropertyValueFactory<Prospect, String>("snippet1"));
		SNIPPET2.setCellValueFactory(new PropertyValueFactory<Prospect, String>("snippet2"));
		SNIPPET3.setCellValueFactory(new PropertyValueFactory<Prospect, String>("snippet3"));
		SNIPPET4.setCellValueFactory(new PropertyValueFactory<Prospect, String>("snippet4"));
		SNIPPET5.setCellValueFactory(new PropertyValueFactory<Prospect, String>("snippet5"));
		prospectTable.setItems(Collections.prospectView);

		choicebox.getItems().add("Field");
		choicebox.getItems().add("EMAIL");
		choicebox.getItems().add("FIRSTNAME");
		choicebox.getItems().add("LASTNAME");
		choicebox.getItems().add("FULLNAME");
		choicebox.getItems().add("COMPANY");
		choicebox.getItems().add("PHONE");
		choicebox.getItems().add("ADDRESS");
		choicebox.getItems().add("CITY");
		choicebox.getItems().add("SNIPPET1");
		choicebox.getItems().add("SNIPPET2");
		choicebox.getItems().add("SNIPPET3");
		choicebox.getItems().add("SNIPPET4");
		choicebox.getItems().add("SNIPPET5");
		choicebox.getSelectionModel().selectFirst();
		choicebox.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue) 
				-> searchProspect(newValue));
		searchField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				searchProspect(choicebox.getValue());
			};
		});

		EMAIL.setCellFactory(TextFieldTableCell.forTableColumn());
		EMAIL.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
			checkDuplicates();
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "email");
		});
		FIRSTNAME.setCellFactory(TextFieldTableCell.forTableColumn());
		FIRSTNAME.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setFirstName(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "first_name");
		});
		LASTNAME.setCellFactory(TextFieldTableCell.forTableColumn());
		LASTNAME.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setLastName(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "last_name");
		});
		FULLNAME.setCellFactory(TextFieldTableCell.forTableColumn());
		FULLNAME.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setFullName(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "full_name");
		});
		COMPANY.setCellFactory(TextFieldTableCell.forTableColumn());
		COMPANY.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCompany(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "company");
		});
		PHONE.setCellFactory(TextFieldTableCell.forTableColumn());
		PHONE.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhone(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "phone");
		});
		ADDRESS.setCellFactory(TextFieldTableCell.forTableColumn());
		ADDRESS.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAddress(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(),"address");
		});
		CITY.setCellFactory(TextFieldTableCell.forTableColumn());
		CITY.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCity(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "city");
		});
		SNIPPET1.setCellFactory(TextFieldTableCell.forTableColumn());
		SNIPPET1.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSnippet1(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "snippet_1");
		});
		SNIPPET2.setCellFactory(TextFieldTableCell.forTableColumn());
		SNIPPET2.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSnippet2(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "snippet_2");
		});
		SNIPPET3.setCellFactory(TextFieldTableCell.forTableColumn());
		SNIPPET3.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSnippet3(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "snippet_3");
		});
		SNIPPET4.setCellFactory(TextFieldTableCell.forTableColumn());
		SNIPPET4.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSnippet4(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "snippet_4");
		});
		SNIPPET5.setCellFactory(TextFieldTableCell.forTableColumn());
		SNIPPET5.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSnippet5(e.getNewValue());
			MySQL_core.editProspectField(e.getTableView().getItems().get(e.getTablePosition().getRow()).getProspect_id(), e.getNewValue(), "snippet_5");
		});

		prospectTable.setEditable(true);
		prospectTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		checkDuplicates();
	}

	private void searchProspect(String newValue) {
		Collections.prospectView.clear();
		for(Prospect p : MainPaneController.c.getProspects()) {
			switch (choicebox.getValue()) {
			case "EMAIL":
				if(p.getEmail().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "FIRSTNAME":
				if(p.getFirstName().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "LASTNAME":
				if(p.getLastName().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "FULLNAME":
				if(p.getFullName().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "COMPANY":
				if(p.getCompany().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "PHONE":
				if(p.getPhone().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "ADDRESS":
				if(p.getAddress().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "CITY":
				if(p.getCity().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "SNIPPET1":
				if(p.getSnippet1().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "SNIPPET2":
				if(p.getSnippet2().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "SNIPPET3":
				if(p.getSnippet3().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "SNIPPET4":
				if(p.getSnippet4().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			case "SNIPPET5":
				if(p.getSnippet5().indexOf(searchField.getText()) >= 0) {
					Collections.prospectView.add(p);
				}
				break;
			default:
				break;
			}

		}
	}
	@FXML
	void back(MouseEvent event) {
		ChangeScene c = new ChangeScene("/main/resources/view/MainPane.fxml");
		c.start();
	}

	@FXML
	void close(MouseEvent event) {
		((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
	}

	@FXML
	void delete(MouseEvent event) {
		ObservableList<Prospect> prospectsSelected = prospectTable.getSelectionModel().getSelectedItems();
		ArrayList<Prospect> items =  new ArrayList<Prospect> (prospectTable.getSelectionModel().getSelectedItems());
		Collections.prospectView.removeAll(prospectsSelected);	
		prospectTable.getSelectionModel().clearSelection();
		MainPaneController.c.getProspects().removeAll(items);
		for(Prospect p : items) {
			MySQL_core.deleteProspect(p.getProspect_id());
		}
	}

	@FXML
	void hide(MouseEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
	private void checkDuplicates() {
		emailDuplicates.clear();
		for(Prospect p : MainPaneController.c.getProspects()) {
			for(Prospect pp : MainPaneController.c.getProspects()) {
				if(!p.equals(pp) && p.getEmail().equals(pp.getEmail()) && !checkIfInArray(p.getEmail())) {
					emailDuplicates.add(p.getEmail());
				}
			}
		}
		if(emailDuplicates.size() != 0) {
			hyperlink.setText("Duplicates: " + emailDuplicates.size());
		} else {
			hyperlink.setText("");
		}
	}
	private boolean checkIfInArray(String email) {
		for(String s : emailDuplicates) {
			if(s.equals(email)) {
				return true;
			}
		}
		return false;
	}
	@FXML
	void showDuplicates(ActionEvent event) {
		choicebox.getSelectionModel().selectFirst();
		searchField.setText("");
		Collections.prospectView.clear();
		for(String email : emailDuplicates) {
			for(Prospect p : MainPaneController.c.getProspects()) {
				if(p.getEmail().equals(email)) {
					Collections.prospectView.add(p);
				}
			}
		}
	}
}
