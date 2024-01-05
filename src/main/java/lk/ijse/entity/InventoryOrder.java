package lk.ijse.entity;

import lk.ijse.dto.tm.InventoryOrderTm;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class InventoryOrder {
    int supOrderId;
    String description;
    Date orderDate;
    Date returnDate;
    String category;
    int supId;
    List<InventoryOrderTm> cartTmList=new ArrayList<>();
    int txtqty;
    int qty;
}
