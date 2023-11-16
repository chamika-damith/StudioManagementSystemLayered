package lk.ijse.controller.Booking;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lk.ijse.dto.PackageDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.PackageModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class PackageController {
    public Label lblPkgId;
    public JFXTextField txtPrice;
    public JFXComboBox txtType;
    public JFXTextField txtName;
    private PackageModel packageModel=new PackageModel();

    public void initialize(){
        txtType.setItems(FXCollections.observableArrayList("PHOTOGRAPHY", "VIDEOGRAPHY", "AUDIO_PRODUCTION", "TV_SHOWS"));
        txtType.requestFocus();
        generateNextPkgId();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        int pkgId = Integer.parseInt(lblPkgId.getText());
        String type = (String) txtType.getValue();
        double price = Double.parseDouble(txtPrice.getText());
        String name = txtName.getText();

        try {
            if (packageModel.isExists(pkgId)) {
                Image image=new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Package is already Added");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                var dto=new PackageDto(pkgId,name,price,type);
                boolean b = packageModel.savePackageDto(dto);
                if (b) {
                    generateNextPkgId();
                    Image image=new Image("/Icon/iconsOk.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Package Saved Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextPkgId() {
        try {
            int pkgid = packageModel.generateNextPkgId();
            lblPkgId.setText(String.valueOf("00"+pkgid));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDleteOnAction(ActionEvent actionEvent) {
    }
}
