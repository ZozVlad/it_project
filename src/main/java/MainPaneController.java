package main.java;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.models.Campaign;
//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class MainPaneController implements Initializable{
	@FXML private CheckBox inprogress = new CheckBox();
	@FXML private CheckBox completed = new CheckBox();
	@FXML private CheckBox stopped = new CheckBox();
	@FXML private CheckBox ready = new CheckBox();
	@FXML private TableView<Campaign> campaignTable;
	@FXML private TableColumn<Campaign, String> c1;
	@FXML private TableColumn<Campaign, String> c2;
	@FXML private TextField searchField = new TextField();
	@FXML private ImageView closeImage = new ImageView();
	@FXML private ImageView hideImage = new ImageView();
	static Campaign c;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		UpdateStatus.updateStatus();
		Collections.campaignTableView.clear();
		Collections.campaignTableView.addAll(Collections.main_campaings);
		c1.setCellValueFactory(new PropertyValueFactory<Campaign, String>("name"));
		c2.setCellValueFactory(new PropertyValueFactory<Campaign, String>("status"));
		addButtonToTable();
		campaignTable.setItems(Collections.campaignTableView);

		closeImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
			}
		});
		hideImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				stage.setIconified(true);
			}
		});
		searchField.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				inprogress.setSelected(false);
				completed.setSelected(false);
				stopped.setSelected(false);
				ready.setSelected(false);
				Collections.campaignTableView.clear();
				Collections.campaignTableView.addAll(Collections.main_campaings);
				Search.searchName(searchField.getText());
			};
		});
	}

	@FXML
	void searchButton(ActionEvent event) {
		Search.search(inprogress.isSelected(), completed.isSelected(), stopped.isSelected(), ready.isSelected(), searchField);
	}

	@FXML
	void addCampaignButton(ActionEvent event) {
		ChangeScene c = new ChangeScene("/main/resources/view/AddCampaign.fxml");
		c.start();
	}

	private void addButtonToTable() {
		TableColumn<Campaign, Void> PROS = new TableColumn<Campaign, Void>();
		Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>> cellFactory1 = new Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>>() {
			@Override
			public TableCell<Campaign, Void> call(final TableColumn<Campaign, Void> param) {
				final TableCell<Campaign, Void> cell = new TableCell<Campaign, Void>() {
					private final Button btn = new Button("Prospects"); 

					{
						btn.setOnAction((ActionEvent event) -> {
							c = getTableView().getItems().get(getIndex());
							ChangeScene c = new ChangeScene("/main/resources/view/ProspectPane.fxml");
							c.start();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						btn.setMaxWidth(115);
						btn.setStyle("-fx-text-fill: white;");
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				cell.setPrefWidth(115);

				return cell;
			}
		};

		PROS.setCellFactory(cellFactory1);
		campaignTable.getColumns().add(PROS);

		TableColumn<Campaign, Void> EDIT = new TableColumn<Campaign, Void>();
		Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>> cellFactory2 = new Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>>() {
			@Override
			public TableCell<Campaign, Void> call(final TableColumn<Campaign, Void> param) {
				final TableCell<Campaign, Void> cell = new TableCell<Campaign, Void>() {
					private final Button btn = new Button("Edit"); 

					{
						btn.setOnAction((ActionEvent event) -> {
							c = getTableView().getItems().get(getIndex());
							ChangeScene c = new ChangeScene("/main/resources/view/EditCampaign.fxml");
							c.start();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						btn.setMaxWidth(80);
						btn.setStyle("-fx-text-fill: white;");
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				cell.setPrefWidth(80);

				return cell;
			}
		};

		EDIT.setCellFactory(cellFactory2);
		campaignTable.getColumns().add(EDIT);

		TableColumn<Campaign, Void> STOP = new TableColumn<Campaign, Void>();
		Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>> cellFactory3 = new Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>>() {
			@Override
			public TableCell<Campaign, Void> call(final TableColumn<Campaign, Void> param) {
				final TableCell<Campaign, Void> cell = new TableCell<Campaign, Void>() {
					private final Button btn = new Button("Stop/Resume"); 

					{
						btn.setOnAction((ActionEvent event) -> {
							c = getTableView().getItems().get(getIndex());
							if(c.getStatus().equals("In progress") || c.getStatus().equals("Ready to send")) {
								c.setStatus("Stopped");
								MySQL_core.editCampaignStatus(c.getCampaign_id(), c.getStatus());
							} else if(c.getStatus().equals("Stopped")) {
								c.setStatus("In progress");
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
							} else {
								AlertDialog.notific("Cannot stop/resume completed campaign.", Notifications.WARNING);
							}
							Collections.campaignTableView.clear();
							Collections.campaignTableView.addAll(Collections.main_campaings);
							inprogress.setSelected(false);
							completed.setSelected(false);
							stopped.setSelected(false);
							ready.setSelected(false);
							searchField.setText("");
						});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						btn.setMaxWidth(135);
						btn.setStyle("-fx-text-fill: #6B5E00;");
						btn.getStyleClass().add("stop_resume");
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				cell.setPrefWidth(135);
				return cell;
			}
		};

		STOP.setCellFactory(cellFactory3);
		campaignTable.getColumns().add(STOP);

		TableColumn<Campaign, Void> DELETE = new TableColumn<Campaign, Void>();
		Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>> cellFactory4 = new Callback<TableColumn<Campaign, Void>, TableCell<Campaign, Void>>() {
			@Override
			public TableCell<Campaign, Void> call(final TableColumn<Campaign, Void> param) {
				final TableCell<Campaign, Void> cell = new TableCell<Campaign, Void>() {
					private final Button btn = new Button("Delete"); 

					{
						btn.setOnAction((ActionEvent event) -> {
							//							Campaign data = getTableView().getItems().get(getIndex());
							c = getTableView().getItems().get(getIndex());
							Collections.main_campaings.remove(c);
							MySQL_core.deleteCampaign(c.getCampaign_id());
							Collections.campaignTableView.clear();
							Collections.campaignTableView.addAll(Collections.main_campaings);
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						btn.setMaxWidth(105);

						btn.setStyle("-fx-text-fill: white;");
						btn.getStyleClass().add("delete");
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				cell.setPrefWidth(105);

				return cell;
			}
		};

		DELETE.setCellFactory(cellFactory4);
		campaignTable.getColumns().add(DELETE);
	}
}
