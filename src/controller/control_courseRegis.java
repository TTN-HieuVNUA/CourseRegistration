package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import helpers.DbConnect;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.registerCourse;
import model.subjects;
import javafx.util.Callback;

public class control_courseRegis implements Initializable {
	private Scene scene;
	private Stage stage;
	@FXML
	private TextField txtsearch;

	@FXML
	private Label username;

	@FXML
	private Button btndel;

	@FXML
	private TableView<registerCourse> table_Search;

	@FXML
	private TableColumn<registerCourse, String> subcodecol;

	@FXML
	private TableColumn<registerCourse, String> namecol;

	@FXML
	private TableColumn<registerCourse, String> classcodecol;

	@FXML
	private TableColumn<registerCourse, String> numcol;

	@FXML
	private TableColumn<registerCourse, String> totalcol;

	@FXML
	private TableColumn<registerCourse, String> restcol;

	/// table
	@FXML
	private TableView<registerCourse> table_waitRegister;

	@FXML
	private TableColumn<registerCourse, String> registMamhCol;

	@FXML
	private TableColumn<registerCourse, String> regisTenmhCol;

	@FXML
	private TableColumn<registerCourse, String> regisMalopCol;

	@FXML
	private TableColumn<registerCourse, String> regisSotcCol;

	@FXML
	private TableColumn<String, String> regisTrangthaiCol;
	
	// user va malop duoc lay khi sinh vien dang nhap
	public static String user;
	public static String classcode;
	/*
	 * tao ra 4 mang de tranh viec phai load lai database qua nhieu lan
	 * moi khi search hay loc mon hoc
	 */
	// danh sach tat ca mon hoc (subjectList)
	public ObservableList<registerCourse> subjectList = FXCollections.observableArrayList(); 
	// danh sach mon hoc khi search listregist
	public ObservableList<registerCourse> listregist = FXCollections.observableArrayList(); 
	// danh sach mon hoc theo ma lop
	public ObservableList<registerCourse> listclasscode = FXCollections.observableArrayList(); 
	// danh sach mon hoc da dang ki
	public ObservableList<registerCourse> waitlistregister = FXCollections.observableArrayList();
	
	private static String mamhDel = "", masvDel = "", malopDel = "";
	
	String query = null;
	Connection connection = DbConnect.getConnect();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	/*
	 * tim kiem mon hoc theo ma mon hoc
	 */
	@FXML
	boolean btn_search() {
		listregist.clear();
		if (!(txtsearch.getText().equals(""))) {
			String search = txtsearch.getText();
			subjects obj = new subjects(search);
			for (int i = 0; i < subjectList.size(); i++) {
				if (obj.equals(subjectList.get(i))) {
					String mmh = subjectList.get(i).getMalop();
					String tenmh = subjectList.get(i).getTenMonHoc();
					int sotc = subjectList.get(i).getSoTinChi();
					int slmax = subjectList.get(i).getSlmax();
					int slcl = subjectList.get(i).getSlcl();
					listregist.add(new registerCourse(search, tenmh, mmh, sotc, slmax, slcl));
				}
			}
			table_Search.setItems(listregist);
			return true;
		}
		return false;
	}

	// lam moi lai bang sau khi tim kiem mon hoc
	@FXML
	private void refreshTable() {
		txtsearch.setText("");
		loadDate();
	}

	/*
	 * ở bảng này nếu dữ liệu được load từ CSDL thì sẽ giới hạn môn học phải là môn
	 * học theo mã lớp - mã lớp sẽ được lấy từ tài khoản đăng nhập của người dùng
	 * 
	 */
	public void loadDate() {
		subjectList.clear();
		query = "SELECT * FROM subject";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				subjectList.add(new registerCourse(resultSet.getString("mamh"), resultSet.getString("tenmh"),
						resultSet.getString("malop"), resultSet.getInt("sotc"), resultSet.getInt("sisomax"),
						resultSet.getInt("sisocl")));
				table_Search.setItems(listclasscode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* SET danh sách môn học theo mã lớp */
	public void loadListClassCode() {
		for (int i = 0; i < subjectList.size(); i++) {
			if (subjectList.get(i).getMalop().equals(classcode)) {
				listclasscode.add(new registerCourse(subjectList.get(i).getMaMonHoc(),
						subjectList.get(i).getTenMonHoc(), subjectList.get(i).getMalop(),
						subjectList.get(i).getSoTinChi(), subjectList.get(i).getSlmax(), subjectList.get(i).getSlcl()));
			}
		}
	}

	/*
	 * goi du lieu mon hoc da duoc dang ki 
	 * cho sinh vien dang dang nhap
	 */

	private void setWaitRegister() {
		query = "select * from courseregistration where masv=?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				waitlistregister.add(new registerCourse(resultSet.getString("mamh"), resultSet.getString("tenmh"),
						resultSet.getString("malop"), resultSet.getInt("sotc"), resultSet.getString("masv")));
			}
			loadregister();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * danh sach mon hoc da dang ki cho bang table_waitRegister
	 * hoac mon hoc dang cho dang ki
	 */
	void loadregister() {
		table_waitRegister.setItems(waitlistregister);
		registMamhCol.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
		regisTenmhCol.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
		regisMalopCol.setCellValueFactory(new PropertyValueFactory<>("malop"));
		regisSotcCol.setCellValueFactory(new PropertyValueFactory<>("soTinChi"));
	}
	
	/*
	 * dang ki mon hoc
	 * dua mon hoc vao danh sach cho waitlistregister
	 * neu chua nhan luu vao CSDL ma thoat chuong trinh thi mon hoc se bi huy
	 */
	void doubleClick() {
		table_Search.setRowFactory(tv -> {
			TableRow<registerCourse> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					int kt = 0;
					int ktmalop = 0;
					for (int i = 0; i < subjectList.size(); i++) {
						if (subjectList.get(i).getMaMonHoc().equals(row.getItem().getMaMonHoc())
								&& subjectList.get(i).getMalop().equals(classcode)) {
							ktmalop = 1;
						}
					}
					if (AlertNotice(row.getItem().getTenMonHoc()) == true) {
						for (int i = 0; i < waitlistregister.size(); i++) {
							if ((!(row.getItem().getMaMonHoc().equals(waitlistregister.get(i).getMaMonHoc()))
									&& !(user.equals(waitlistregister.get(i).getMasv())))
									|| (!(row.getItem().getMaMonHoc().equals(waitlistregister.get(i).getMaMonHoc()))
											&& (user.equals(waitlistregister.get(i).getMasv())))
									|| ((row.getItem().getMaMonHoc().equals(waitlistregister.get(i).getMaMonHoc()))
											&& !(user.equals(waitlistregister.get(i).getMasv())))) {
								kt = 1;
							} else {
								kt = 0;
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setContentText("môn học đã được đăng kí");
								alert.show();
								break;
							}
						}
					}
					if (((kt == 1 && ktmalop == 1) || (waitlistregister.size() == 0 && ktmalop == 1))
						&& count(row.getItem().getMaMonHoc(), row.getItem().getMalop()) == true) {
						waitlistregister.add(new registerCourse(row.getItem().getMaMonHoc(), 
																row.getItem().getTenMonHoc(),
																row.getItem().getMalop(),
																row.getItem().getSoTinChi(),
																user));
						loadregister(); // SAU KHI LƯU THÌ SẼ LOAD LẠI BẢNG REGISTER
					}
					if (ktmalop != 1) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("MÔN HỌC KHÔNG CÓ TRONG TIẾN TRÌNH ĐÀO TẠO");
						alert.show();
					}

				}
			});
			return row;
		});
	}
	
	// thong bao xac nhan dang ki mon hoc
	boolean AlertNotice(String tenmh) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("thêm môn " + tenmh + " vào danh sách đăng kí?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * dem so luong slot con lai
	 * neu > 0 thi tra ve true
	 * neu = thi tra ve false
	 * false thi se khong duoc phep dang ki mon hoc
	 */
	boolean count(String mamh, String malop) {
		int slcl = 0;
		query = "select * from subject where mamh=? and malop=?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mamh);
			preparedStatement.setString(2, malop);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				slcl = resultSet.getInt("sisocl");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		if (slcl > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * hanh dong luu cac mon hoc vao CSDL 
	 * cac mon hoc da luu thi se khong duoc luu lai nua
	 */
		@FXML
		void save(ActionEvent event) {
			for (int i = 0; i < waitlistregister.size(); i++) {
				try {
					query = "INSERT INTO courseregistration (masv, mamh,tenmh, malop, sotc) VALUES (?,?,?,?,?)";
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, waitlistregister.get(i).getMasv());
					preparedStatement.setString(2, waitlistregister.get(i).getMaMonHoc());
					preparedStatement.setString(3, waitlistregister.get(i).getTenMonHoc());
					preparedStatement.setString(4, waitlistregister.get(i).getMalop());
					preparedStatement.setInt(5, waitlistregister.get(i).getSoTinChi());
					int check = preparedStatement.executeUpdate();
					if(check > 0) {
						downSubQuantity(waitlistregister.get(i).getMaMonHoc(), waitlistregister.get(i).getMalop(), -1);
					}
				} catch (SQLException ex) {
					ex.getStackTrace();
				}
			}
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("đã thêm các môn học vào cCSDL");
			alert.show();
			listclasscode.clear();
			refreshTable();
			loadListClassCode();
		}
	/*
	 * giam di 1 slot khi dang ki thanh cong thi truyen vao -1
	 * tang them 1 slot khi huy mon hoc thanh cong, truyen vao 1 de tang them
	 */
	void downSubQuantity(String mamh, String malop, int num) {
			query = "UPDATE subject SET sisocl = sisocl +? WHERE mamh =? AND malop=?";
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, num);
				preparedStatement.setString(2, mamh);
				preparedStatement.setString(3, malop);
				preparedStatement.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	
	/*
	 * delete mon hoc trong mang cho va database
	 * 
	 * 
	 */
	// delete mon hoc
	@FXML
	void deleteCourse(ActionEvent event) {
		if (event.getSource() == btndel && mamhDel.equals("")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("chưa có môn học nào được chọn");
			alert.show();
		}
		if (event.getSource() == btndel && !(mamhDel.equals("")) && !(masvDel.equals(""))) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("hủy môn học này khỏi danh sách đăng kí?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				deleteCourseOnDb(mamhDel,malopDel);// xoa mon hoc tren database dong thoi tang them 1 slot 
				for (int i = 0; i < waitlistregister.size(); i++) {
					if (waitlistregister.get(i).getMaMonHoc().equals(mamhDel)
							&& waitlistregister.get(i).getMasv().equals(masvDel)) {
						waitlistregister.remove(i); // xoa mon hoc trong mang khong can goi lai database	
						mamhDel = ""; 				// sau khi xoa se tra lai gia tri = null
						masvDel = "";
						malopDel = "";
					}
				}
			}
			loadregister();// load lai bang table_waitRegister sau khi huy
		}
	}
	
	/// delete mon hoc tren database
	void deleteCourseOnDb(String mamh, String malop) {
		query = "delete from courseregistration where masv=? and mamh=?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, mamh);
			int check = preparedStatement.executeUpdate();
			if(check>0) {
				downSubQuantity(mamh, malop, 1);
			}
			listclasscode.clear();
			refreshTable();
			loadListClassCode();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	void clickRowCourseTable() {
		table_waitRegister.setRowFactory(tv -> {
			TableRow<registerCourse> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					mamhDel = row.getItem().getMaMonHoc();
					masvDel = row.getItem().getMasv();
					malopDel = row.getItem().getMalop();
				}
			});
			return row;
		});
	}


	// LOGOUT KHOI TAI KHOAN HIEN TAI
    @FXML
    void logout(MouseEvent event) {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../views/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username.setText(user);
		setWaitRegister(); 		// hien thi mon hoc da duoc dang ki hoac dang cho dang ki
		loadDate();				// goi ham loadDate() truoc de set tong cac mon hoc
		loadListClassCode(); 	// goi ham loadListClassCode de lay cac mon hoc duoc mo cho ma lop
		if (btn_search() == true) {
			btn_search();
		}
		if (btn_search() == false) {
			loadDate();
		}
		refreshTable();
		subcodecol.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
		namecol.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
		classcodecol.setCellValueFactory(new PropertyValueFactory<>("malop"));
		numcol.setCellValueFactory(new PropertyValueFactory<>("soTinChi"));
		totalcol.setCellValueFactory(new PropertyValueFactory<>("slmax"));
		restcol.setCellValueFactory(new PropertyValueFactory<>("slcl"));
		doubleClick();
		clickRowCourseTable();
	}

}
