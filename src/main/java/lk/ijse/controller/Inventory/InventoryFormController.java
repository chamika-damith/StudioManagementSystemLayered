package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InventoryFormController {

    public JFXTextField id;

    public JFXTextField name;
    public JFXTextField qty;
    public JFXTextField price;
    public JFXTextField description;
    public JFXComboBox comboBox;

    public Image image;

    public FileInputStream fileInputStream;
    public ImageView img;
    public JFXButton imgSelectBtn;

    public void btnImgSelect(ActionEvent actionEvent) {
            FileChooser chooser = new FileChooser();
            File file =chooser.showOpenDialog(imgSelectBtn.getScene().getWindow());
            try {
                fileInputStream=new FileInputStream(file);
                image=new Image(fileInputStream);
                img.setImage(image);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
}
