package main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button login;
	@FXML
	private Label loginErrorLabel;
    
	/**
	 * đăng nhập.
	 */
	public void initialize() {
		login.setOnAction(event -> {
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "");
				String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					try {
						Stage stage = (Stage) login.getScene().getWindow();
						stage.close();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/Main.fxml"));
						Parent root = loader.load();
						Stage mainStage = new Stage();
						mainStage.setTitle("EmployeeManagementSystem");
						mainStage.setResizable(false);
						mainStage.setScene(new Scene(root));
						mainStage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (username.isEmpty() || password.isEmpty()) {
					loginErrorLabel.setText("Please fill in all information.");
				} else {
					loginErrorLabel.setText("Invalid Login. Please try again.");
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
    
	
	/**
	 * quên mật khẩu.
	 * @throws IOException nếu lỗi I/O khi mở trình duyệt web
	 * @throws URISyntaxException nếu URI không hợp lệ
	 */
	@FXML
	private void forgetPassword(ActionEvent event) {
	    String url = "https://forms.gle/s3QGV1CXBNgSDXaZ6";
	    try {
	        Desktop.getDesktop().browse(new URI(url));
	    } catch (IOException | URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}