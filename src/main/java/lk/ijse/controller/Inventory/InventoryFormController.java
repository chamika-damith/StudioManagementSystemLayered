package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import lk.ijse.dto.ItemDto;
import lk.ijse.model.ItemModel;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class InventoryFormController {

    public JFXTextField txtid;
    public JFXTextField txtname;
    public JFXTextField txtqty;
    public JFXTextField txtprice;
    public JFXTextField txtdescription;
    public JFXComboBox txtcomboBox;

    public Image image;
    public FileInputStream fileInputStream;
    public ImageView img;
    public JFXButton imgSelectBtn;
    public Label txtAllInventory;

    private ItemModel model=new ItemModel();


    public void initialize(){
        txtcomboBox.setItems(FXCollections.observableArrayList("CAMERA", "LENS", "DRONE", "LIGHTS", "ACCESORIES"));
        setValueLable();
    }


    public void setValueLable(){
        try {

            int count =0;
            List<ItemDto> allItems = model.getAllItems();

            for (ItemDto item : allItems){
                count+=1;
            }

            txtAllInventory.setText(String.valueOf(count));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        int id = Integer.parseInt(txtid.getText());
        String name = txtname.getText();
        int qty = Integer.parseInt(txtqty.getText());
        double price = Double.parseDouble(txtprice.getText());
        String description = txtdescription.getText();
        String category = (String) txtcomboBox.getValue();
        Image imgId = img.getImage();
        byte[] blob = model.imagenToByte(imgId);

        ItemDto itemDto = new ItemDto(id, description, qty, name, price, blob, category);


        try {
            boolean b = model.saveItem(itemDto);

            if(b) {
                setValueLable();
                System.out.println("Item saved successfully");
            }else {
                System.out.println("Item not saved successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        int id = Integer.parseInt(txtid.getText());
        String name = txtname.getText();
        int qty = Integer.parseInt(txtqty.getText());
        double price = Double.parseDouble(txtprice.getText());
        String description = txtdescription.getText();
        String category = (String) txtcomboBox.getValue();
        Image imgId = img.getImage();
        byte[] blob = model.imagenToByte(imgId);

        ItemDto itemDto = new ItemDto(id, description, qty, name, price, blob, category);

        try {
            boolean b = model.updateItem(itemDto);
            if (b) {
                System.out.println("Item updated successfully");
            }else {
                System.out.println("Item not updated successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();

        try {
            ItemDto itemDto = model.searchItems(id);
            if (itemDto != null) {
                System.out.println("itemDto");
                txtdescription.setText(itemDto.getDescription());
                txtprice.setText(String.valueOf(itemDto.getPrice()));
                txtqty.setText(String.valueOf(itemDto.getQty()));
                txtname.setText(itemDto.getName());

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(itemDto.getImg());

                // Use the input stream to create an Image
                Image image = new Image(byteArrayInputStream);
                img.setImage(image);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
