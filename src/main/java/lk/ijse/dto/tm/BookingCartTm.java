package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@ToString
@Setter
@Getter
@AllArgsConstructor

public class BookingCartTm {
    int id;
    String event;
    String address;
    int empId;
    int pkgId;
    Date date;
    Button btn;
}
