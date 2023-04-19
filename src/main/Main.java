package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage loginStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../giaodien/Login.fxml"));
        loginStage.setTitle("Login");
        loginStage.setResizable(false);
        loginStage.setScene(new Scene(root));
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 