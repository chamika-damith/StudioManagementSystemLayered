package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Customer {
    int cusId;
    String name;
    String mobile;
    String email;
    String address;
}
