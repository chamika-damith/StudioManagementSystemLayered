package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import javafx.scene.control.Button;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor

public class EmployeeTm {
    int empId;
    String name;
    String address;
    String email;
    double salary;
    String type;
    Button btn;

}
