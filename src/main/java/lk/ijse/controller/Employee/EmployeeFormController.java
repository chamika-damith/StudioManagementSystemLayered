package lk.ijse.controller.Employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class EmployeeFormController {
    public JFXComboBox<String> comboBox;
    public JFXTextField txtEmpId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtSalary;

    private EmployeeModel model=new EmployeeModel();

    public void initialize(){
        comboBox.setItems(FXCollections.observableArrayList("Admin","cashier","Manager","worker"));
        generateNextEmpId();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String type = comboBox.getValue();
        int empId = Integer.parseInt(txtEmpId.getText());
        String name = txtName.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String email = txtEmail.getText();

        var dto=new EmployeeDto(empId,name,salary,email,type);

        try {
            boolean b = model.saveEmployee(dto);
            if (b) {
                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Employee Saved Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextEmpId() {
        try {
            int empid = model.generateNextEmpId();
            txtEmpId.setText(String.valueOf("00"+empid));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
