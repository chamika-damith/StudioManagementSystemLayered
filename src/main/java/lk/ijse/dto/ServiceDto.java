package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor

public class ServiceDto {
    int pkgId;
    String name;
    double price;
    String type;
}
