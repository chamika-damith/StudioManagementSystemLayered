package lk.ijse.controller.Order;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lk.ijse.controller.Booking.BookingFormController;
import org.controlsfx.control.Notifications;

public class orderPaymentController {

    private int textTotal;

    public static boolean isorderTrue=false;

    public JFXTextField txtTotal;


    public void initialize(){
        setTxtTotal();
        txtTotal.setText(String.valueOf(textTotal));
    }

    public void btnPay(ActionEvent actionEvent) {
        txtTotal.clear();
        isorderTrue=true;
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
        textTotal = OrderFormController.getTextTotal();
    }

    public static boolean getValidPayment() {
        return isorderTrue;
    }
}
