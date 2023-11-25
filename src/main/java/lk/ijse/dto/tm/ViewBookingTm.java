package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class ViewBookingTm {
    int bookingId;
    String cusName;
    String address;
    String email;
    String mobile;
    Button complete;
    Button cancel;
    Button more;
    Button status;
}
