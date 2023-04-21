package main;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.sql.SQLException;
import java.sql.Connection;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PositionCon {
	@FXML
	private TextField salaryCoefficientTextField;
    private ObservableList<Position> positionList;
    @FXML
    private Button saveButton;
    @FXML
    private TextField positionNameTextField;
	@FXML
	 public void handleSaveButton() {
		 Alert alert;
//	        alert = new Alert(AlertType.ERROR, "Bạn chư");

			String positionName = positionNameTextField.getText();
			String salaryCoefficientStr = salaryCoefficientTextField.getText();
		    double salaryCoefficient = Double.parseDouble(salaryCoefficientStr);
		    if (positionName.trim().isEmpty()) {
		        alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
		        alert.showAndWait();
		        if (salaryCoefficient < 0.0 || salaryCoefficient > 30.0) {
		        		alert = new Alert(AlertType.ERROR, "Hệ số lương không đúng");
				        alert.showAndWait();
				        return;
		        }
		        return;
		    }

			try {
			    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
			    // Kiểm tra trùng mã nhân viên
			    String sql = "SELECT * FROM database.positiondata WHERE chuc_vu = ?";
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    stmt.setString(1, positionName);
			    ResultSet rs = stmt.executeQuery();
			    if (rs.next()) {
			        alert = new Alert(AlertType.ERROR, "Chức vụ đã tồn tại!");
			        alert.showAndWait();
			        return; 
			    }
			    rs.close();
			    stmt.close();

			    // Thêm mới nhân viên
			    sql = "INSERT INTO positiondata (chuc_vu, he_so_luong) VALUES (?, ?)";
			    PreparedStatement insertStmt = conn.prepareStatement(sql);
			    insertStmt.setString(1, positionName);
			    insertStmt.setDouble(2, salaryCoefficient);
		
			    // Thực thi câu lệnh INSERT
			    insertStmt.executeUpdate();
			    insertStmt.close();
			    conn.close();
			} catch (SQLException s) {
			    s.printStackTrace();
			}
			 Stage stage = (Stage) saveButton.getScene().getWindow();
			 stage.close();
			 PositionController.updatePosition();

	 }
}
