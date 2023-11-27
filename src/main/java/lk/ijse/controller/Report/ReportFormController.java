package lk.ijse.controller.Report;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.model.ItemModel;
import lk.ijse.model.ReportModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFormController {
    public CheckBox checkBox;
    public JFXComboBox cmbItemId;

    private int itemId;

    private ItemModel itemModel=new ItemModel();

    private ReportModel reportModel=new ReportModel();

    public void initialize(){
        loadItemId();
    }

    public void btnCusReport(ActionEvent actionEvent) throws JRException, SQLException {

        InputStream resourceAsStream = getClass().getResourceAsStream("/ReportForm/cusNew.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport, //compiled report
                        null,
                        DbConnection.getInstance().getConnection() //database connection
                );

        JasperViewer.viewReport(jasperPrint, false);


    }

   public void btnOrdersReport(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/ReportForm/orders.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport, //compiled report
                        null,
                        DbConnection.getInstance().getConnection() //database connection
                );

        JasperViewer.viewReport(jasperPrint, false);
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) {
        if (checkBox.isSelected()){

        }else {
            String code = (String) cmbItemId.getValue();

            try {
                ItemDto dto = itemModel.searchItems(code);
                if (dto != null) {
                    itemId=dto.getItemId();
                }else {
                    System.out.println("dto is null");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadItemId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDto = itemModel.getAllItems();

            for (ItemDto dto : itemDto) {
                obList.add(String.valueOf(dto.getItemId()));
            }
            cmbItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void allCusReport() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/ReportForm/cusNew.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        Map<String, Object> parameters = new HashMap<>();


        CustomerDto customer = reportModel.getAllCustomer();
        if (customer != null){
            String cusId= String.valueOf(customer.getCusId());
            String name=customer.getName();
            String mobile=customer.getMobile();
            String email=customer.getEmail();
            String address=customer.getAddress();

            parameters.put("Cid",cusId);
            parameters.put("Cname",name);
            parameters.put("Caddress",address);
            parameters.put("Cmobile",mobile);
            parameters.put("Cemail",email);
        }


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport, //compiled report
                        parameters,
                        DbConnection.getInstance().getConnection() //database connection
                );

        JasperViewer.viewReport(jasperPrint, false);
    }

}
