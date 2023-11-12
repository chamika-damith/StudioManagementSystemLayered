package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.ItemTm;
import lk.ijse.model.ItemModel;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public TableView tblItem;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colDescription;
    public TableColumn colCategory;
    public TableColumn colAction;

    private ItemModel model=new ItemModel();
    private ObservableList<ItemTm> obList = FXCollections.observableArrayList();


    public void initialize(){
        txtcomboBox.setItems(FXCollections.observableArrayList("CAMERA", "LENS", "DRONE", "LIGHTS", "ACCESORIES"));
        setValueLable();
        getAllItem();
        setCellValue();
    }

    public Button createButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("ActionBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setRemoveBtnAction(btn);
        return btn;
    }

    private void getAllItem() {
        var model=new ItemModel();


        ObservableList<ItemTm> obList=FXCollections.observableArrayList();

        try {
            List<ItemDto> allItems = model.getAllItems();

            for (ItemDto dto : allItems){
                Button button = createButton();
                obList.add(new ItemTm(
                        dto.getItemId(),
                        dto.getName(),
                        dto.getQty(),
                        dto.getPrice(),
                        dto.getDescription(),
                        dto.getCategory(),
                        button
                ));

            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValue() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

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
                getAllItem();
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
                getAllItem();
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

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblItem.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                getAllItem();
            }
        });
    }
}
