package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor

public class Service {
    int pkgId;
    String name;
    double price;
    String type;
}
