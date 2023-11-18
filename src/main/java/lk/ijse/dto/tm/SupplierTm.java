package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor

public class SupplierTm {
    int id;
    String name;
    String address;
    String mobile;
    String category;
    Button btn;
}
