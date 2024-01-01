package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.custom.InventoryOrderDetailDAO;
import lk.ijse.dao.custom.impl.InventoryOrderDetailDAOImpl;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.InventoryOrderItemTm;
import lk.ijse.dto.tm.ViewInventoryOrderTm;
import lk.ijse.dto.tm.ViewOrderTm;
import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ViewInventoryOrderDetailController {

    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCategory;
    public TableColumn colMobile;
    public TableColumn colMore;
    public JFXTextField txtSearchOrder;
    public AnchorPane viewInventoryRoot;
    public JFXTextField txtSearchBar;

    private ObservableList<ViewInventoryOrderTm> obList;

    private InventoryOrderItemDetailsController controller=new InventoryOrderItemDetailsController();

    private InventoryOrderDetailDAO inventoryOrderDetailDAO=new InventoryOrderDetailDAOImpl();

    public void initialize() throws ClassNotFoundException {
        setCellValues();
        getAllOrders();
        searchTable();
    }


    private void setCellValues() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colMore.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void getAllOrders() throws ClassNotFoundException {

        obList= FXCollections.observableArrayList();

        try {
            List<InventoryOrderViewDto> allItems = inventoryOrderDetailDAO.getAllItemsOrder();

            for (InventoryOrderViewDto dto : allItems){
                Button button = createButton();
                obList.add(new ViewInventoryOrderTm(
                        dto.getId(),
                        dto.getSupName(),
                        dto.getAddress(),
                        dto.getCategory(),
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


    private void setMoreBtnAction(Button btn) {

        btn.setOnAction((e) -> {

            int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();
            ViewInventoryOrderTm viewOrderTm= (ViewInventoryOrderTm) tblOrder.getSelectionModel().getSelectedItem();
            int selectId=viewOrderTm.getId();
            controller.getIndex(selectId);

            try {
                Parent parent=FXMLLoader.load(getClass().getResource("/view/Inventory/InventoryOrderItemDetails.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setTitle("Item Order Detail");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                viewInventoryRoot.setEffect(new GaussianBlur());

                stage.setOnCloseRequest(event -> {
                    viewInventoryRoot.setEffect(null);
                });


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void btnInventoryDetails(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Inventory/InventoryForm.fxml"));
        viewInventoryRoot.getChildren().clear();
        viewInventoryRoot.getChildren().add(parent);
    }

    public void btnInventoryOrder(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Inventory/InventoryOrderDetail.fxml"));
        viewInventoryRoot.getChildren().clear();
        viewInventoryRoot.getChildren().add(parent);
    }

    public void btnViewOrderDetail(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Inventory/ViewInventoryOrder.fxml"));
        viewInventoryRoot.getChildren().clear();
        viewInventoryRoot.getChildren().add(parent);
    }

    public void searchTable() {
        FilteredList<ViewInventoryOrderTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(viewInventoryOrderTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String cusId = String.valueOf(viewInventoryOrderTm.getId());
                String name = viewInventoryOrderTm.getSupName().toLowerCase();

                return cusId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter);
            });
        });

        SortedList<ViewInventoryOrderTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblOrder.comparatorProperty());
        tblOrder.setItems(sortedData);
    }
}
