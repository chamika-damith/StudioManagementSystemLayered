package lk.ijse.controller.Booking;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class BookingPaymentFormController {
    public JFXTextField txtTotal;
    public JFXTextField txtAmountOnAction;
    public AnchorPane BookingPayRoot;

    private int textTotal;

    public static boolean isTrue=false;

    public void initialize(){
        setTxtTotal();
        txtTotal.setText(String.valueOf(textTotal));
    }

    public void btnPay(ActionEvent actionEvent) {
            txtTotal.clear();
            isTrue=true;
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
    }

    public void setTxtTotal() {
        textTotal = BookingFormController.getTextTotal();
    }

    public static boolean getValidPayment() {
        return isTrue;
    }
}
