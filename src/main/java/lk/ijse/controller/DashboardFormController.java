package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane DashboardRoot;

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage= (Stage) DashboardRoot.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
