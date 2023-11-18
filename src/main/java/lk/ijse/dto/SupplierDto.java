package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SupplierDto {
    int id;
    String name;
    String contact;
    String address;
    String category;
}
