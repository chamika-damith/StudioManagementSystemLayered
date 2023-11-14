package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@Setter
@AllArgsConstructor

public class OrderDetailsDto {
    int orderId;
    int itemId;
    int qty;
}
