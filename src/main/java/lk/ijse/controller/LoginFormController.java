package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane LoginRoot;
    public CubicCurve curve;
    public MFXTextField txtUserName;
    public MFXPasswordField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage= (Stage) LoginRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.centerOnScreen();


        Image image=new Image("/Icon/iconsOk.png");
        try {
            Notifications notifications=Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Login Successfully");
            notifications.title("Successfully");
            notifications.hideAfter(Duration.seconds(3));
            notifications.position(Pos.TOP_RIGHT);
            notifications.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void rootOnMouseExited(MouseEvent mouseEvent) {
        curve.setControlX2(451.8468017578125);
        curve.setControlY2(-36);
    }

    public void rootOnMouseMove(MouseEvent mouseEvent) {
        curve.setControlX2(mouseEvent.getX());
    }
}
