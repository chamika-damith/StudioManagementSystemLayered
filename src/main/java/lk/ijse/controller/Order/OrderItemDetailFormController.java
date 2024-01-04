package lk.ijse.controller.Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.dto.OrderItemDetailFormDto;
import lk.ijse.dto.tm.OrderItemTm;

import java.sql.SQLException;
import java.util.List;

public class OrderItemDetailFormController {
    public AnchorPane orderItemDetailRoot;

    public TableView tblOrderItems;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colQty;
    public TableColumn colImg;
    public Label lblOrderId;
    public Label lblOrderDate;
    public Label lblTotal;

    ObservableList<OrderItemTm> obList;

    private static int focusedIndex;

    private OrderDetailBO orderDetailBO= (OrderDetailBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.ORDERDETAIL);


    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        getAllValues();
        lblOrderId.setText(String.valueOf(focusedIndex));
    }

    private void getAllValues() throws ClassNotFoundException {

        obList= FXCollections.observableArrayList();


        try {
            List<OrderItemDetailFormDto> dto= orderDetailBO.getAllValues(focusedIndex);

            for (OrderItemDetailFormDto oDto : dto) {
                lblOrderDate.setText(String.valueOf(oDto.getOrderDate()));
                lblTotal.setText(String.valueOf(oDto.getTotprice()));
                obList.add(new OrderItemTm(
                        oDto.getItemId(),
                        oDto.getDescription(),
                        oDto.getName(),
                        oDto.getPrice(),
                        oDto.getCategory(),
                        oDto.getQty(),
                        oDto.getImg()
                ));
            }
            tblOrderItems.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getIndex(int index) {
        System.out.println(index);
        focusedIndex=index;
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colImg.setCellValueFactory(new PropertyValueFactory<>("img"));
    }
}
