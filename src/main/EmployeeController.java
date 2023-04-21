package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;
import javafx.scene.input.KeyCode;
import javafx.collections.transformation.FilteredList;
public class EmployeeController {
		@FXML
		private AnchorPane baseView;
		@FXML
		private AnchorPane employeeView;
		private String currentTableName = "EmployeeView";
		
		@FXML
	    private TableView<Employee> employeeTableView;
		@FXML
		private TableColumn<Employee, String> idColumn;
		@FXML
		private TableColumn<Employee, String> nameColumn;
		@FXML
		private TableColumn<Employee, String> birthdayColumn;
		@FXML
		private TableColumn<Employee, String> genderColumn;
		@FXML
		private TableColumn<Employee, String> emailColumn;
		@FXML
		private TableColumn<Employee, String> positionColumn;
		@FXML
		private TableColumn<Employee, Double> coeffColumn;
		@FXML
		private TableColumn<Employee, Integer> workdayColumn;
		@FXML
		private TableColumn<Employee, String> salaryColumn;
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
		private ComboBox<String> filteredPositionComboBox;
		@FXML
		private RadioButton maleRadioButton;
		@FXML
		private RadioButton femaleRadioButton;
		@FXML
		private ToggleGroup gender;
		@FXML
	    public TextField searchTextField;
		@FXML
	    public TextField filteredSalaryTextField;
		@FXML
		private Button saveButton;
		@FXML
		private Button addEmployee;
		
		private static EmployeeController instance;
	    private ObservableList<Employee> employeeList;
	    List<Position> positionList = new ArrayList<>();
		List<String> positionNameList = new ArrayList<>();
		Double salaryCoefficient=0.0;
		public void getPosition() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");

	        String sql = "SELECT * FROM database.positiondata";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
//	        positionList = FXCollections.observableArrayList();
	        while (rs.next()) {
	            String chucVu = rs.getString("chuc_vu");
	            double heSoLuong = rs.getDouble("he_so_luong");
	            Position position = new Position(chucVu, heSoLuong);
	            positionNameList.add(chucVu);
	            positionList.add(position);
	        	}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
	    public void initialize() {
	    	employeeShow();
	    	getPosition();
	    	ObservableList<String> chucVuObservableList = FXCollections.observableArrayList(positionNameList);
//	    	filteredPositionComboBox.setItems(chucVuObservableList);
	        searchEmployee();
	        filteredSalaryTextField.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.ENTER) { // Kiểm tra xem phím đã được ấn có phải là phím Enter không
	                filteredSalary(); // Gọi hàm filteredSalary để lọc danh sách nhân viên
	            }
	        });
	        filteredPositionComboBox.getItems().add(0, "All"); // Thêm "All" vào vị trí đầu tiên
	        filteredPositionComboBox.setValue("All"); // Mặc định chọn chức vụ là "All"
	        filteredPositionComboBox.setOnAction(event -> {
	            // Khi chọn chức vụ khác, hiển thị lại danh sách toàn bộ nhân viên
	            filteredSalaryTextField.setText("");
	            employeeTableView.setItems(employeeList);
	        });
	    }
	    public EmployeeController() {
	    	instance=this;
	    }
	    
	    public void employeeShow() {
	    	try {
//	    		employeeTableView.refresh();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");

		        String sql = "SELECT * FROM database.employeedata";
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        
		        employeeList = FXCollections.observableArrayList();
		        while (rs.next()) {
		            String id  = rs.getString("id");
		            String id1  = rs.getString("ho_ten");
		            String hoTen = rs.getString("ho_ten");
		            String ngaySinh = rs.getString("ngay_sinh");
		            String gioiTinh  = rs.getString("gioi_tinh");
		            String email = rs.getString("email");
		            String chucVu = rs.getString("chuc_vu");
		            double heSoLuong = rs.getDouble("he_so_luong");
		            int ngayLamViec = rs.getInt("ngay_lam_viec");
		            double tongLuong = rs.getDouble("tong_luong");
//		            double num = 1234567.89123;
		            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		            String formatted = decimalFormat.format(tongLuong);
//		            textField.setText(formatted);
		            

		            Employee employee = new Employee(id, hoTen, ngaySinh, gioiTinh, email, chucVu, heSoLuong, ngayLamViec, formatted);
		            employeeList.add(employee);
		        }
		        if (idColumn != null) {
			        idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
		        } else idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("id1"));

		        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthday"));
		        genderColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("gender"));
		        emailColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
		        positionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
		        coeffColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salaryCoefficient"));
		        workdayColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("workday"));
		        salaryColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
//		        salaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%,.0f", cellData.getValue().getSalary())));
		        
		        employeeTableView.setItems(employeeList);
		        
		        conn.close();
//		        positionTableView.getColumns().addAll(chucVuColumn, heSoLuongColumn);
		        employeeTableView.setStyle("-fx-font-size: 13px;");
		        workdayColumn.setStyle("-fx-alignment: center;");
		        idColumn.setStyle("-fx-alignment: center;");
		        coeffColumn.setStyle("-fx-alignment: center;");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    }
	    
	   public static void updateEmployee(){
	    	if(instance != null) {
	    		instance.employeeShow();
	    	}
	    }
		/**
		* hiển thị bảng khi thêm, sửa nhân viên.
		*/
		public void showEmployeeForm(String title) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/EmployeeForm.fxml"));
			Parent root = loader.load();
			Stage employeeFormStage = new Stage();
			employeeFormStage.setTitle(title);
			employeeFormStage.setScene(new Scene(root));
			employeeFormStage.show();

		}
//		 @FXML
//		 private void handleAddEmployee(ActionEvent event) throws IOException {
//			 showEmployeeForm("Add Employee");
//			 saveButton = new Button();
//			 saveButton.setOnAction(e -> {	
//				 System.out.println("đã vào");
//		        Alert alert;
//		        String id = idTextField.getText();
//		        String fullname = fisrtNameTextField.getText() + " " + lastNameTextField.getText();
//		        String email = emailTextField.getText();
//		        String birthday = birthdayTextField.getText();
//		        int workday = Integer.parseInt(workdayTextField.getText());		       
//		        Toggle selectedToggle = gender.getSelectedToggle();
//		        String gender = ((RadioButton) selectedToggle).getText();
//		        String position = "";
//		        position = positionComboBox.getValue().toString();
//		        
////		        PositionController positionController = new PositionController();
////		        double heSoLuong = positionController.getSalaryCoefficientByChucVu(position);
//	            if (id.isEmpty() || fullname.isEmpty() || email.isEmpty()  ||
//	            	birthday.isEmpty() || gender.isEmpty()) {
//	            		alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
//	            		alert.showAndWait();
//		    }
//	            try {
//	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
//		        // Tạo câu lệnh SQL DELETE và thực thi
//		        String sql = "SELECT * FROM database.employeedata WHERE id = ?";
//		        PreparedStatement stmt = conn.prepareStatement(sql);
//			    // Kiểm tra trùng mã nhân viên
//		        ResultSet rs = stmt.executeQuery(sql);
//		        if (rs.next()) {
//			        // Hiển thị thông báo lỗi nếu mã nhân viên đã tồn tại trong cơ sở dữ liệu
//			        alert = new Alert(AlertType.ERROR, "Employee ID already exists.");
//			        alert.showAndWait();
//			        return; // Thoát phương thức nếu thông tin không hợp lệ
//	            }
//	            rs.close();
//	            stmt.close();
//		        String insertQuery = "INSERT INTO employeedata (id, ho_ten, ngay_sinh, gioi_tinh, email, chuc_vu, he_so_luong, ngay_lam_viec, tong_luong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//			    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
//			    insertStmt.setString(1, id);
//			    insertStmt.setString(2, fullname);
//			    insertStmt.setString(3, birthday);
//			    insertStmt.setString(4, gender);
//			    insertStmt.setString(5, email);
//			    insertStmt.setString(6, position);
//			    conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
//			    // lấy hệ số lương gắn với chức vụ ở sql
//		        sql = "SELECT he_so_luong FROM database.positiondata WHERE position = ?";
//		        stmt = conn.prepareStatement(sql);
//		        stmt.setString(1, position);
//		        rs = stmt.executeQuery();
//		        double salaryCoefficient = 0.0;
//		        if (rs.next()) {
//		        	salaryCoefficient = rs.getDouble("he_so_luong");
//		        }
//			    insertStmt.setDouble(7, salaryCoefficient);
//			    insertStmt.setInt(8, workday);
//			    // lấy lương cơ bản = 1,490,000
//			    double tongLuong = (salaryCoefficient * 1490000 * workday) / 26;
//			    insertStmt.setDouble(9, tongLuong);
//	            } catch (SQLException s) {
//	            	s.getStackTrace();
//	            }
//			  });
//
//		 }
		
		@FXML
		private void handleAddEmployee(ActionEvent event) {
		    try {
				showEmployeeForm("Add Employee");
//				getPosition();
//				ObservableList<String> chucVuObservableList = FXCollections.observableArrayList(positionNameList);
//		        positionComboBox.setItems(chucVuObservableList);
//		    	for (Position p : positionList) {
//			        if (p.getPositionName().equals(position)) {
//			            salaryCoefficient = p.getSalaryCoefficient();
//			        }
//			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
		}

		 @FXML
		 private void editEmployee(ActionEvent event) throws IOException {
		        showEmployeeForm("Edit Employee");
		    } 
		 
		 /**
		  * chuyển đổi chức năng giữa quản lý nhân với quản lý chức vụ.
		  */
		 @FXML
		 private void switchTable(ActionEvent event) throws IOException {
		     Button clickedButton = (Button) event.getSource();
		     String clickedName = clickedButton.getText();
		     if (clickedName.contains("QUẢN LÝ CHỨC VỤ") && !currentTableName.equals("PositionView")) {
		         FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionView.fxml"));
		         AnchorPane positionView = loader.load();
		         baseView.getChildren().setAll(positionView);
		         currentTableName = "PositionView";
		     } else if (clickedName.contains("QUẢN LÝ NHÂN VIÊN") && !currentTableName.equals("EmployeeView")) {
		         baseView.getChildren().setAll(employeeView);
		         currentTableName = "EmployeeView";
		     }
		 }
		 /**
		  * tìm kiếm.
		  */
//		 @FXML
//		 public void searchEmployee(ActionEvent e) {
//		        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
////		        String oldValue = searchTextField.getText();
//		        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//		        	  if (searchTextField == null || (newValue.length() < oldValue.length()) || newValue == null) {
//				            employeeTableView.setItems(employeeList);
//				        } else {
//				            newValue = newValue.toUpperCase();
//				            for (Employee employee : employeeTableView.getItems()) {
//				                String idSearch = String.valueOf(employee.getId());
//				                String emailSearch = employee.getEmail();
//				                if (idSearch.toUpperCase().contains(newValue) || emailSearch.toUpperCase().contains(newValue)) {
//				                    filteredList.add(employee);
//				                }
//				            }
//				            employeeTableView.setItems(filteredList);
//				        }
//		        });
//		      
//		    }
//		 @FXML
		 public void searchEmployee() {
		     searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		         if (newValue == null || newValue.isEmpty()) { // nếu ô tìm kiếm trống
		             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
		             return;
		         }
		         ObservableList<Employee> filteredList = FXCollections.observableArrayList();
		         String filter = newValue.toUpperCase(); // chuyển đổi giá trị tìm kiếm thành chữ in hoa
		         for (Employee employee : employeeList) {
		             String id = String.valueOf(employee.getId()).toUpperCase();
		             String email = employee.getEmail().toUpperCase();
		             String name = employee.getName().toUpperCase();
		             if (id.contains(filter) || email.contains(filter) || name.contains(filter)) {
		                 filteredList.add(employee); // thêm bản ghi vào danh sách đã lọc
		             }
		         }
		         employeeTableView.setItems(filteredList);
		     });
		 }
		 
		 @FXML
		 public void filteredSalary() {
			 filteredSalaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		         if (newValue == null || newValue.isEmpty()) { // nếu ô tìm kiếm trống
		             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
		             return;
		         }
		         ObservableList<Employee> filteredList = FXCollections.observableArrayList();
//		         String filterStr = filteredSalaryTextField.getText(); 
		         double filter = Double.parseDouble(filteredSalaryTextField.getText()); // lấy giá trị double từ TextField
		         for (Employee employee : employeeList) {
		             if (employee.getSalaryCoefficient() >= filter ) {
		                 filteredList.add(employee); 
		             }
		         }
		         employeeTableView.setItems(filteredList); 
		         
		     });
		 } 
		 @FXML
		 public void filteredPosition() {
			 filteredSalaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		         if (newValue == null || newValue.isEmpty()) { // nếu ô tìm kiếm trống
		             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
		             return;
		         }
		         ObservableList<Employee> filteredList = FXCollections.observableArrayList();
//		         String filterStr = filteredSalaryTextField.getText(); 
		         double filter = Double.parseDouble(filteredSalaryTextField.getText()); // lấy giá trị double từ TextField
		         for (Employee employee : employeeList) {
		             if (employee.getSalaryCoefficient() >= filter ) {
		                 filteredList.add(employee); 
		             }
		         }
		         employeeTableView.setItems(filteredList); 
		     });
		 } 
		
		 /**
		  * xóa
		  */
		 public void deleteEmployee(ActionEvent e) {
			  Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
			    Alert alert = new Alert(AlertType.CONFIRMATION);
			    alert.setTitle("Xác nhận");
			    alert.setHeaderText(null);
			    alert.setContentText("Bạn có chắc chắn muốn xóa nhân viên này?");
			    Optional<ButtonType> result = alert.showAndWait();
			    if (result.isPresent() && result.get() == ButtonType.OK) {
			    try {
			        // Kết nối đến cơ sở dữ liệu
			    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
			        // Tạo câu lệnh SQL DELETE và thực thi
			        String sql = "DELETE FROM database.employeedata WHERE id = ?";
			        PreparedStatement stmt = conn.prepareStatement(sql);
			        stmt.setString(1, selected.getId());
			        stmt.executeUpdate();
			        conn.close();
			        employeeShow(); 
			    } catch (SQLException ex) {
			        ex.printStackTrace();
			    }
			  }
			}
		 
//		 @FXML
//		 public void initializeSearch() {
//			 	searchTextField = new TextField();
//		        searchTextField.setOnKeyPressed(event -> {
//		            if (event.getCode() == KeyCode.ENTER) {
//		                searchEmployee();
//		            }
//		        });
//		    }
//		 private void searchEmployee() {
//			    // Lấy thông tin tìm kiếm từ trường tìm kiếm
//			    String search = searchTextField.getText().trim().toLowerCase();
//			    // Tạo một đối tượng FilteredList từ danh sách nhân viên
//			    FilteredList<Employee> filteredList = new FilteredList<>(employeeList);
//			    // Thiết lập bộ lọc cho FilteredList
//			    filteredList.setPredicate(employee ->
//			        employee.getId().toLowerCase().contains(search) ||
//			        employee.getName().toLowerCase().contains(search) ||
//			        employee.getEmail().toLowerCase().contains(search) ||
//			        employee.getBirthday().toLowerCase().contains(search)
//			    );
//			    // Hiển thị danh sách nhân viên lọc được trên bảng
//			    employeeTableView.setItems(filteredList);
//			}
		
	
		 
		 /**
		  * thêm nhân viên
		  */

		 /**
		  * đăng xuất.
		  */
		 @FXML
		 private void logout(ActionEvent event) {
		     Alert alert = new Alert(AlertType.CONFIRMATION);
		     alert.setTitle("Logout");
		     alert.setHeaderText("Bạn có muốn thoát không?");
		     Optional<ButtonType> result = alert.showAndWait();
		     if (result.get() == ButtonType.OK){
		         try {
		        	   Stage currentStage = (Stage) baseView.getScene().getWindow();
			             currentStage.close();
		             FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/Login.fxml"));
		             Parent root = (Parent) loader.load();
		             Stage stage = new Stage();
		             stage.setScene(new Scene(root));
		             stage.show();
		          
		         } catch (IOException e) {
		             e.printStackTrace();
		         }
		     }
		 }

		
		}
