package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor

public class PackageDto {
    int pkgId;
    String name;
    double price;
    String type;
}
