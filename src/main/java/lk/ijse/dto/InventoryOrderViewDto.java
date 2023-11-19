package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor

public class InventoryOrderViewDto {
    int id;
    String supName;
    String address;
    String category;
    String mobile;
}
