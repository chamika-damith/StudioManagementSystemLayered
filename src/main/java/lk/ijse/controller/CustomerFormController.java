package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CustomerFormController {
    public AnchorPane CustomerRoot;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Dashboard/DashboardWindow.fxml"));
        CustomerRoot.getChildren().add(parent);
    }
}
