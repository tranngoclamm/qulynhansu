package main;

import java.io.IOException;
import java.net.URL;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.sql.SQLException;
import java.sql.Connection;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;


public class PositionController {
	
	@FXML
    private TableView<Position> positionTableView;

	@FXML
	private TableColumn<Position, String> chucVuColumn;

	@FXML
	private TableColumn<Position, Double> heSoLuongColumn;
    private ObservableList<Position> positionList;
    @FXML
    private Button saveButton;
    @FXML
    private TextField positionNameTextField;
    @FXML
    private TextField salaryCoefficientTextField;
    public void initialize() {
    	try {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");

        String sql = "SELECT chuc_vu, he_so_luong FROM database.positiondata";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        positionList = FXCollections.observableArrayList();
        while (rs.next()) {
            String chucVu = rs.getString("chuc_vu");
            double heSoLuong = rs.getDouble("he_so_luong");
            Position position = new Position(chucVu, heSoLuong);
            positionList.add(position);
        }
        chucVuColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("positionName"));
        heSoLuongColumn.setCellValueFactory(new PropertyValueFactory<Position, Double>("salaryCoefficient"));

        positionTableView.setItems(positionList);
        positionTableView.setStyle("-fx-font-size: 15px;");
        chucVuColumn.setStyle("-fx-alignment: center;");
        heSoLuongColumn.setStyle("-fx-alignment: center;");
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
  
	/**
	 * hiển thị bảng khi thêm, sửa chức vụ
	 */
	public void showPositionForm(String title) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
		Parent root = loader.load();
		Stage positionFormStage = new Stage();
		positionFormStage.setTitle(title);
		positionFormStage.setResizable(false);
		positionFormStage.setScene(new Scene(root));
		positionFormStage.show();

	}
//	 @FXML
//	 private void addPosition(ActionEvent event) throws IOException {
//		 	showPositionForm("Add Position");
//	    }
	 @FXML
	 private void editPosition(ActionEvent event) throws IOException {
		 	showPositionForm("Edit Position");
	    }
	 
	  /**
	     * thêm chức vụ
	     */
//	 @FXML
//	 private void addPosition(ActionEvent event) throws IOException, SQLException {
//		 showPositionForm("Add Position");
//		 saveButton = new Button();
//		 saveButton.setOnAction(e -> {	
//		 try {
//			 System.out.println("đã vào");
//	        Alert alert;
//	        String positionName = positionNameTextField.getText();
//	        String salaryCoefficient = salaryCoefficient.getText();
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
//		 } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//		  });
//
//	 }
	 
	 /**
	  * xóa chức vụ
	  */
	 public void deletePosition(ActionEvent e) {
		  Position selected = positionTableView.getSelectionModel().getSelectedItem();
		    Alert alert = new Alert(AlertType.CONFIRMATION);
		    alert.setTitle("Xác nhận");
		    alert.setHeaderText(null);
		    alert.setContentText("Bạn có chắc chắn muốn xóa chức vụ này?");
		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == ButtonType.OK) {
		    try {
		        // Kết nối đến cơ sở dữ liệu
		    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
		        // Tạo câu lệnh SQL DELETE và thực thi
		        String sql = "DELETE FROM database.positiondata WHERE chuc_vu = ?";
		        PreparedStatement stmt = conn.prepareStatement(sql);
		        stmt.setString(1, selected.getPositionName());
		        stmt.executeUpdate();

		        // Đóng kết nối
		        conn.close();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		  }
		}
	 public Position getPositionByChucVu(String chucVu) {
		    initialize(); // khởi tạo danh sách vị trí
		    for (Position position : positionList) {
		        if (position.getPositionName().equals(chucVu)) {
		            return position;
		        }
		    }
		    return null;
		}
}
