package lk.ijse.controller.Booking;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.model.InventoryOrderItemDetailModel;
import org.controlsfx.control.Notifications;

public class BookingPaymentFormController {
    public JFXTextField txtTotal;
    public JFXTextField txtAmountOnAction;
    public AnchorPane BookingPayRoot;

    private int textTotal;

    public void initialize(){
        setTxtTotal();
        txtTotal.setText(String.valueOf(textTotal));
    }

    public void btnPay(ActionEvent actionEvent) {
        String total = txtTotal.getText();
        String amount = txtAmountOnAction.getText();

        if (total.equals(amount)) {
            txtTotal.clear();
            txtAmountOnAction.clear();
            Image image=new Image("/Icon/iconsOk.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Payment Successful");
                notifications.title("Succeeded");
                notifications.hideAfter(Duration.seconds(4));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            txtAmountOnAction.requestFocus();
            txtAmountOnAction.setFocusColor(Color.RED);
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Payment not Successful..Enter amount is lower");
                notifications.title("Error");
                notifications.hideAfter(Duration.seconds(4));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void txtTotalOnAction(ActionEvent actionEvent) {
        txtAmountOnAction.requestFocus();
    }

    public void setTxtTotal() {
        textTotal = BookingFormController.getTextTotal();
    }
}
