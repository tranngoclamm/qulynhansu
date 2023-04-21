package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EmployeeCon {
	@FXML
	private TextField idTextField;
	@FXML
	private TextField fisrtNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField birthdayTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField workdayTextField;
	@FXML
	private ComboBox<String> positionComboBox;
	@FXML
	private RadioButton maleRadioButton;
	@FXML
	private RadioButton femaleRadioButton;
	@FXML
	private ToggleGroup gender;
	@FXML
    public TextField searchTextField;
	@FXML
	private Button saveButton;
//	private ObservableList<Employee> employeeList;
    List<Position> positionList = new ArrayList<>();
	List<String> positionNameList = new ArrayList<>();
	Double salaryCoefficient=0.0;
	public void initialize() {
	try {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");

        String sql = "SELECT * FROM database.positiondata";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
//        positionList = FXCollections.observableArrayList();
        while (rs.next()) {
            String chucVu = rs.getString("chuc_vu");
            double heSoLuong = rs.getDouble("he_so_luong");
            Position position = new Position(chucVu, heSoLuong);
            positionNameList.add(chucVu);
            positionList.add(position);
        	}
        ObservableList<String> chucVuObservableList = FXCollections.observableArrayList(positionNameList);
        positionComboBox.setItems(chucVuObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
//	public void showEmployeeForm(String title) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/EmployeeForm.fxml"));
//		Parent root = loader.load();
//		Stage employeeFormStage = new Stage();
//		employeeFormStage.setTitle(title);
//		employeeFormStage.setScene(new Scene(root));
////		employeeFormStage.show();
//		employeeFormStage.showAndWait();
//
//	}
//	List<Position> positionList = new ArrayList<>();
//	List<String> positionNameList = new ArrayList<>();
//	Double salaryCoefficient=0.0;
//	public void getPosition() {
//	try {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
//
//        String sql = "SELECT * FROM database.positiondata";
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//        
////        positionList = FXCollections.observableArrayList();
//        while (rs.next()) {
//            String chucVu = rs.getString("chuc_vu");
//            double heSoLuong = rs.getDouble("he_so_luong");
//            Position position = new Position(chucVu, heSoLuong);
//            positionNameList.add(chucVu);
//            positionList.add(position);
//        	}
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//	}
	@FXML
	private void handleSaveButton(ActionEvent event) throws IOException {
	  
		Alert alert;
		String id = idTextField.getText();
		String fullname = fisrtNameTextField.getText() + " " + lastNameTextField.getText();
		String email = emailTextField.getText();
		String birthday = birthdayTextField.getText();
		String workdayStr = workdayTextField.getText();
		
		int workday = -1;
		if (workdayStr.matches("[0-9]{1,2}")) {
		     int workdayTemp = Integer.parseInt(workdayStr);
		     if (workdayTemp >= 0 && workdayTemp <= 31) {
		    	 workday = workdayTemp;
		     } else {
		         alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
		         alert.showAndWait();
		         return;
		        }
		 } else {
		        alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
		        alert.showAndWait();
		        return;
		    }
		workday = Integer.parseInt(workdayStr);
		Toggle selectedToggle = gender.getSelectedToggle();
		String gender = ((RadioButton) selectedToggle).getText();
		String position = "";
		if (positionComboBox != null && positionComboBox.getValue() != null) {
		    position = positionComboBox.getValue().toString();
		}
	

		if (id.trim().isEmpty() || fullname.trim().isEmpty() || email.trim().isEmpty() || birthday.trim().isEmpty() || gender.isEmpty()) {
		    alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
		    alert.showAndWait();
		    return;
		}

		try {
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
		    // Kiểm tra trùng mã nhân viên
		    String sql = "SELECT * FROM database.employeedata WHERE id = ?";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setString(1, id);
		    ResultSet rs = stmt.executeQuery();
		    if (rs.next()) {
		        // Hiển thị thông báo lỗi nếu mã nhân viên đã tồn tại trong cơ sở dữ liệu
		        alert = new Alert(AlertType.ERROR, "Nhân viên đã tồn tại!");
		        alert.showAndWait();
		        return; // Thoát phương thức nếu thông tin không hợp lệ
		    }
		    rs.close();
		    stmt.close();

		    // Thêm mới nhân viên
		    sql = "INSERT INTO employeedata (id, ho_ten, ngay_sinh, gioi_tinh, email, chuc_vu, he_so_luong, ngay_lam_viec, tong_luong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    PreparedStatement insertStmt = conn.prepareStatement(sql);
		    insertStmt.setString(1, id);
		    insertStmt.setString(2, fullname);
		    insertStmt.setString(3, birthday);
		    insertStmt.setString(4, gender);
		    insertStmt.setString(5, email);
		    
		    insertStmt.setString(6, position);
//	        alert = new Alert(AlertType.ERROR, "OK");
			for (Position p : positionList) {
		        if (p.getPositionName().equals(position)) {
		            salaryCoefficient = p.getSalaryCoefficient();
		        }
		}
		    insertStmt.setDouble(7, salaryCoefficient);
		    insertStmt.setInt(8, workday);
		    // Lấy lương cơ bản = 1,490,000
		    double tongLuong = (salaryCoefficient * 1490000 * workday) / 26;
		    insertStmt.setDouble(9, tongLuong);

		    // Thực thi câu lệnh INSERT
		    insertStmt.executeUpdate();
		    insertStmt.close();
		    conn.close();
		} catch (SQLException s) {
		    s.printStackTrace();
		}
		 Stage stage = (Stage) saveButton.getScene().getWindow();
		 stage.close();
//		 EmployeeController update = new EmployeeController();
//		 update.employeeShow();
		 EmployeeController.updateEmployee();
	}
	
//	@FXML
//	private void saveButton() {
//		  System.out.println("Đã ấn được3");
//
//	}

//	@FXML
//	private void addEmployee(ActionEvent event) throws IOException {
//	    try {
//		showEmployeeForm("Add Employee");
//	    } catch (IOException e) {
//	    	e.getStackTrace();
//	    }
////	    saveButton = new Button();
//	    Parent root =null;
//	    Button saveButton = (Button) root.lookup("#saveButton");
//	    saveButton = (Button) ((Parent) root).lookup("#saveButton");
//	    @FXML
//	    private void handleSaveButton(ActionEvent event) {
//	        System.out.println("Đã ấn được");
//	    }
//	    saveButton.setOnAction(e -> {
//	        System.out.println("đã vào");
////	        Alert alert;
//	        String id = idTextField.getText();
//	        String fullname = fisrtNameTextField.getText() + " " + lastNameTextField.getText();
//	        String email = emailTextField.getText();
//	        String birthday = birthdayTextField.getText();
//	        int workday = Integer.parseInt(workdayTextField.getText());		       
//	        Toggle selectedToggle = gender.getSelectedToggle();
//	        String gender = ((RadioButton) selectedToggle).getText();
//	        String position = "";
//	        position = positionComboBox.getValue().toString();
//	        
////	        PositionController positionController = new PositionController();
////	        double heSoLuong = positionController.getSalaryCoefficientByChucVu(position);
//            if (id.isEmpty() || fullname.isEmpty() || email.isEmpty()  ||
//            	birthday.isEmpty() || gender.isEmpty()) {
//            		alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
//            		alert.showAndWait();
//	    }
//            try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
//	        // Tạo câu lệnh SQL DELETE và thực thi
//	        String sql = "SELECT * FROM database.employeedata WHERE id = ?";
//	        PreparedStatement stmt = conn.prepareStatement(sql);
//		    // Kiểm tra trùng mã nhân viên
//	        ResultSet rs = stmt.executeQuery(sql);
//	        if (rs.next()) {
//		        // Hiển thị thông báo lỗi nếu mã nhân viên đã tồn tại trong cơ sở dữ liệu
//		        alert = new Alert(AlertType.ERROR, "Employee ID already exists.");
//		        alert.showAndWait();
//		        return; // Thoát phương thức nếu thông tin không hợp lệ
//            }
//            rs.close();
//            stmt.close();
//	        String insertQuery = "INSERT INTO employeedata (id, ho_ten, ngay_sinh, gioi_tinh, email, chuc_vu, he_so_luong, ngay_lam_viec, tong_luong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
//		    insertStmt.setString(1, id);
//		    insertStmt.setString(2, fullname);
//		    insertStmt.setString(3, birthday);
//		    insertStmt.setString(4, gender);
//		    insertStmt.setString(5, email);
//		    insertStmt.setString(6, position);
//		    conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
//		    // lấy hệ số lương gắn với chức vụ ở sql
//	        sql = "SELECT he_so_luong FROM database.positiondata WHERE position = ?";
//	        stmt = conn.prepareStatement(sql);
//	        stmt.setString(1, position);
//	        rs = stmt.executeQuery();
//	        double salaryCoefficient = 0.0;
//	        if (rs.next()) {
//	        	salaryCoefficient = rs.getDouble("he_so_luong");
//	        }
//		    insertStmt.setDouble(7, salaryCoefficient);
//		    insertStmt.setInt(8, workday);
//		    // lấy lương cơ bản = 1,490,000
//		    double tongLuong = (salaryCoefficient * 1490000 * workday) / 26;
//		    insertStmt.setDouble(9, tongLuong);
//            } catch (SQLException s) {
//            	s.getStackTrace();
//            }
//		  });

//	 }
	
//	@FXML
//	 private void editEmployee(ActionEvent event) throws IOException {
//	        showEmployeeForm("Edit Employee");
//	    } 
}
