package lk.ijse.dto;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class ViewBookingDto {
    int bookingId;
    String cusName;
    String address;
    String email;
    String mobile;
}
