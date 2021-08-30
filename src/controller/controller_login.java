package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import model.subjects;

public class controller_login implements Initializable {
	private Scene scene;
	private Stage stage;
	@FXML
	private TextField txtusername;
	@FXML
	private PasswordField txtpassword;
	@FXML
	private Button btnlogin;
	public ObservableList<Acount> acountList = FXCollections.observableArrayList();
	String query = null;
	Connection connection = DbConnect.getConnect();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@FXML
	void switchtoscenehome(ActionEvent event) {
		loadDate();
		if (!(txtpassword.getText().equals("")) && !(txtusername.getText().equals(""))) {
			if (acountList.isEmpty() == true) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("không tìm thấy tài khoản");
				alert.show();
				txtpassword.setText("");
				txtusername.setText("");
			} else if (txtusername.getText().equals(acountList.get(0).getUsername())
					&& txtpassword.getText().equals(acountList.get(0).getPassword()) && acountList.isEmpty() != true) {
				control_courseRegis.user = txtusername.getText();
				control_courseRegis.classcode = acountList.get(0).getClasscode();
				switchtosceneCourseRegis(event);
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("user, password không được để trống");
			alert.show();
		}
	}

	void loadDate() {
		String query = "select * from login where username=? and password=?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, txtusername.getText());
			preparedStatement.setString(2, txtpassword.getText());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				acountList.add(new Acount(resultSet.getString("username"), resultSet.getString("password"),
						resultSet.getString("classcode")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void switchtosceneCourseRegis(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../views/course_regis.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../views/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtusername.setFocusTraversable(false);
		txtusername.setStyle("-fx-text-inner-color: #ffffff;");
		txtpassword.setStyle("-fx-text-inner-color: #ffffff;");
	}
}
