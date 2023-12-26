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
import lk.ijse.model.LoginClassModel;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

import javax.security.auth.spi.LoginModule;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane LoginRoot;
    public CubicCurve curve;
    public MFXTextField txtUserName;
    public MFXPasswordField txtPassword;

    public LoginClassModel model=new LoginClassModel();

    private String loginSuccess;

    private static int userId;
    private static String username;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        if (checkLogin()){
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
        }else {
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Username or Password is Invalid");
                notifications.title("Login Successfully");
                notifications.hideAfter(Duration.seconds(3));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public void rootOnMouseExited(MouseEvent mouseEvent) {
        curve.setControlX2(451.8468017578125);
        curve.setControlY2(-36);
    }

    public void rootOnMouseMove(MouseEvent mouseEvent) {
        curve.setControlX2(mouseEvent.getX());
    }

    public boolean checkLogin() throws SQLException {
        boolean b = model.checkLogin(txtUserName.getText(), txtPassword.getText());
        if (b) {
            getUserId(txtUserName.getText());
            username=txtUserName.getText();
            return true;
        }else {
            return false;
        }
    }

    private void getUserId(String text) throws SQLException {
        userId = model.getUserId(text);
    }

    public static int returnUserId(){
        return userId;
    }
    public static String returnUserName(){
        return username;
    }

    public void txtUsernameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        btnLoginOnAction(actionEvent);
    }

}
