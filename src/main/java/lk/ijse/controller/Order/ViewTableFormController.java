package lk.ijse.controller.Order;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.ItemTm;
import lk.ijse.dto.tm.ViewOrderTm;
import lk.ijse.model.OrderDetailViewModel;
import lk.ijse.model.OrderItemDetailFormModel;
import lk.ijse.model.OrderModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ViewTableFormController {
    public AnchorPane viewOrderTableRoot;
    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colMobile;
    public TableColumn colMore;
    public TableColumn colRemove;
    public JFXTextField txtSearchOrder;
    private ObservableList<ViewOrderTm> obList;
    private OrderItemDetailFormController OIDController=new OrderItemDetailFormController();

    public void initialize(){
        setCellValues();
        getAllOrders();
        searchTable();
    }

    private void getAllOrders() {
        var OrderModel = new OrderDetailViewModel();

        obList= FXCollections.observableArrayList();

        try {
            List<OrderViewDto> allItems = OrderModel.getAllItems();

            for (OrderViewDto dto : allItems){
                Button button = createButton();
                obList.add(new ViewOrderTm(
                        dto.getOrderId(),
                        dto.getCustomerName(),
                        dto.getAddress(),
                        dto.getEmail(),
                        dto.getMobile(),
                        button
                ));

            }
            tblOrder.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Button createButton(){
        Button btn=new Button("more");
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }

    private void setCellValues() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colMore.setCellValueFactory(new PropertyValueFactory<>("btnMore"));
    }

    public void searchTable(){
        FilteredList<ViewOrderTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchOrder.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(viewOrderTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String itemId = String.valueOf(viewOrderTm.getOrderId());
                String name = viewOrderTm.getCustomerName().toLowerCase();

                return itemId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter);
            });
        });

        SortedList<ViewOrderTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblOrder.comparatorProperty());
        tblOrder.setItems(sortedData);
    }

    public void btnorderFormOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Order/OrderForm.fxml"));
        viewOrderTableRoot.getChildren().clear();
        viewOrderTableRoot.getChildren().add(parent);
    }

    public void btnOrderTableOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Order/ViewTableForm.fxml"));
        viewOrderTableRoot.getChildren().clear();
        viewOrderTableRoot.getChildren().add(parent);
    }

    private void setMoreBtnAction(Button btn) {

        btn.setOnAction((e) -> {

                int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();
                ViewOrderTm viewOrderTm= (ViewOrderTm) tblOrder.getSelectionModel().getSelectedItem();
                int selectId=viewOrderTm.getOrderId();
                OIDController.getIndex(selectId);

            try {
                Parent parent=FXMLLoader.load(getClass().getResource("/view/Order/OrderItemDetailForm.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setTitle("Order Item Detail");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                viewOrderTableRoot.setEffect(new GaussianBlur());

                stage.setOnCloseRequest(event -> {
                    viewOrderTableRoot.setEffect(null);
                });


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
