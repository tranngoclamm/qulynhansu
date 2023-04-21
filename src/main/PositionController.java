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
  
    @FXML
    private Button saveButton;
    @FXML
    private Button editPositon;
    @FXML
    private Button addPosition;
    @FXML
    private TextField positionNameTextField;
    @FXML
    private TextField salaryCoefficientTextField;
	private static PositionController instance;
    private ObservableList<Position> positionList;
    String title = "";
    public void initialize() {
    	positionShow();
  }
    public void positionShow() {
    	try {
//    	    chucVuColumn.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
//    		 if (chucVuColumn != null) {
//    	            chucVuColumn.setCellValueFactory(new PropertyValueFactory<>("tenChucVu"));
//    	        }
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
    public PositionController() {
    	instance=this;
    }
   
  
	/**
	 * hiển thị bảng khi thêm, sửa chức vụ
	 */
	public void showPositionForm(String title) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
		Parent root = loader.load();
		Stage positionFormStage = new Stage();
		positionFormStage.setTitle(title + "Position");
		positionFormStage.setResizable(false);
		positionFormStage.setScene(new Scene(root));
		positionFormStage.show();
		

	}
	public static void updatePosition(){
    	if(instance != null) {
    		instance.positionShow();
    	}
    }
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
	    Button button = (Button) event.getSource();
	    String action = button.getId();
	    System.out.println(action);
	    if (action.equals("addPosition")) {
	        showPositionForm("Add");
	        title="Add";
	    } else if (action.equals("editPosition")) {
	        showPositionForm("Edit");
	        title="Edit";
	    }
	}
	
	 private void addPosition() {
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
			 positionShow();
			 PositionController.updatePosition();	
	 }
	 @FXML
	 public void handleSaveButton() {
		 if(title.equals("Add")) {
				addPosition();
			} else if (title.equals("Edit")) {
				try {
					editPosition();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 

	 }
	 private void editPosition() throws IOException {
//		 	showPositionForm("Edit");
		 System.out.println("Edit");
	    }
	 
	  /**
	     * thêm chức vụ
	     */

	 
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
	            positionTableView.getItems().remove(selected);

		        // Đóng kết nối
		        conn.close();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		  }
		}
	 /**
	  * 
	  * tìm kiếm
	  * @return
	  */
	 
	 
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
