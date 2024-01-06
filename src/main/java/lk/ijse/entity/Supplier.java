package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Supplier {
    int id;
    String name;
    String contact;
    String address;
    String category;
}
